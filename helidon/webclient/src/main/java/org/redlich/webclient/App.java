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
public class App {
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
