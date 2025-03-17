/*
 * Copyright (c) 2024 Oracle and/or its affiliates.
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

package org.redlich.security;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import io.helidon.config.Config;
import io.helidon.config.ConfigValue;
import io.helidon.http.HttpMediaTypes;
import io.helidon.logging.common.LogConfig;
import io.helidon.security.SecurityContext;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.WebServerConfig;
import io.helidon.webserver.staticcontent.StaticContentService;

/**
 * Example using configuration based approach.
 */
public final class ConfigAuthenticationApplication {
    private ConfigAuthenticationApplication() {
        }

    /**
     * Entry point, starts the server.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        LogConfig.initClass();

        Config config = Config.create();

        WebServerConfig.Builder builder = WebServer.builder()
                .port(8080);
        setup(builder);
        WebServer server = builder.build();

        long nanoTime = System.nanoTime();
        server.start();
        long time = System.nanoTime() - nanoTime;

        System.out.println("[APP] ---------------------------------------");
        System.out.println("[APP] Helidon Security Configuration Example");
        System.out.println("[APP] ---------------------------------------");
        System.out.printf("[APP] Server started in %d ms", TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS));
        System.out.println("\n");
        System.out.println("[APP] -------------------------------------------");
        System.out.println("[APP]  Configured Users Using Configuration File");
        System.out.println("[APP] -------------------------------------------");
        System.out.println("\n");
        ConfigValue<String> mike = config.get("security.providers.0.http-basic-auth.users.0.login").asString();
        ConfigValue<String> mikePassword = config.get("security.providers.0.http-basic-auth.users.0.password").asString();
        ConfigValue<List<Config>> mikeRoles = config.get("security.providers.0.http-basic-auth.users.0.roles").asNodeList();
        System.out.println("[APP] " + mike);
        System.out.println("[APP] " + mikePassword);
        System.out.println("[APP] " + mikeRoles);
        System.out.println("\n");
        ConfigValue<String> rowena = config.get("security.providers.0.http-basic-auth.users.1.login").asString();
        ConfigValue<String> rowenaPassword = config.get("security.providers.0.http-basic-auth.users.1.password").asString();
        ConfigValue<List<Config>> rowenaRoles = config.get("security.providers.0.http-basic-auth.users.1.roles").asNodeList();
        System.out.println("[APP] " + rowena);
        System.out.println("[APP] " + rowenaPassword);
        System.out.println("[APP] " + rowenaRoles);
        System.out.println("\n");
        ConfigValue<String> ian = config.get("security.providers.0.http-basic-auth.users.2.login").asString();
        ConfigValue<String> ianPassword = config.get("security.providers.0.http-basic-auth.users.2.password").asString();
        ConfigValue<List<Config>> ianRoles = config.get("security.providers.0.http-basic-auth.users.2.roles").asNodeList();
        System.out.println("[APP] " + ian);
        System.out.println("[APP] " + ianPassword);
        System.out.println("[APP] " + ianRoles);
        System.out.println("\n");
        System.out.println("[APP] ----------------------");
        System.out.println("[APP]  Configured Endpoints");
        System.out.println("[APP] ----------------------");
        System.out.println("\n");
        System.out.println("[APP] No authentication: http://localhost:8080/public");
        System.out.println("[APP] No roles required, but authenticated: http://localhost:8080/noRoles");
        System.out.println("[APP] Admin role required: http://localhost:8080/admin");
        System.out.println("[APP] User role required: http://localhost:8080/user");
        System.out.println("[APP] Always forbidden (no roles defined) and audited: http://localhost:8080/deny");
        System.out.println("[APP] Admin role required, authenticated, authentication optional, audited \\");
        System.out.println("[APP] (always forbidden - challenge is not returned as authentication is optional): http://localhost:8080/noAuthn");
        System.out.println("[APP] Static content that requires a user role: http://localhost:8080/static/index.html");
        }

    static void setup(WebServerConfig.Builder server) {
        Config config = Config.create();

        server.config(config.get("server"))
                .routing(routing -> routing
                        .register("/static", StaticContentService.create("/WEB"))
                        .get("/{*}", (req, res) -> {
                            Optional<SecurityContext> securityContext = req.context().get(SecurityContext.class);
                            res.headers().contentType(HttpMediaTypes.PLAINTEXT_UTF_8);
                            res.send("Hello, you are: \n" + securityContext
                                    .map(ctx -> ctx.user().orElse(SecurityContext.ANONYMOUS).toString())
                                    .orElse("Security context is null"));
                            }));
        }
    }

