/*
 * Copyright (c) 2017, 2023 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.redlich.webclient;

import io.helidon.common.reactive.Single;
import io.helidon.config.Config;
import io.helidon.config.ConfigValue;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;
import io.helidon.webserver.WebServer;

/**
 * Hello world!
 *
 */
public class OriginalMain {
    public static void main( String[] args ) {
        Config config = Config.create();
        String url;
        ConfigValue<Integer> port = config.get("server.port").asInt();
        if (!port.isPresent() || port.get() == -1) {
            throw new IllegalStateException("Unknown port! Please specify port as a main method parameter "
                    + "or directly to config server.port");
            }
        url = "http://localhost:" + port.get() + "/greet";
        System.out.println("The URL is: " + url);

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .addMediaSupport(JsonpSupport.create())
            .build();

        Single<WebServer> webserver = server.start();

        webserver.thenAccept(ws -> {
                    System.out.println("WEB server is up! http://localhost:" + ws.port() + "/greet");
                    ws.whenShutdown().thenRun(() -> System.out.println("WEB server is DOWN. Goodbye!"));
                })
                .exceptionallyAccept(t -> {
                    System.err.println("Startup failed: " + t.getMessage());
                    t.printStackTrace(System.err);
                });

        WebClient webClient = WebClient.builder()
                .baseUri(url)
                .config(config.get("client"))
                .addMediaSupport(JsonpSupport.create())
                .build();

        performGetMethod(webClient).await();
        }
    static Single<String> performGetMethod(WebClient webClient) {
        System.out.println("Get request execution.");
        return webClient.get()
                .request(String.class)
                .peek(string -> {
                    System.out.println("GET request successfully executed.");
                    System.out.println(string);
                });
        }
    }
