package org.redlich.webclient;

import io.helidon.config.Config;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.WebServerConfig;
import io.helidon.webserver.http.HttpRouting;

/**
 * The application main class.
 */
public final class ServerApplication {

    /**
     * Cannot be instantiated.
     */
    private ServerApplication() {
    }

    /**
     * WebServer starting method.
     *
     * @param args starting arguments
     */
    public static void main(String[] args) {
        // By default, this will pick up application.yaml from the classpath
        Config config = Config.create();
        Config.global(config);

        WebServerConfig.Builder builder = WebServer.builder();
        setup(builder);
        WebServer server = builder.build().start();
        server.context().register(server);
        System.out.println("WEB server is up! http://localhost:" + server.port() + "/greet");
    }

    /**
     * Set up the server.
     *
     * @param server server builder
     */
    static void setup(WebServerConfig.Builder server) {
        Config config = Config.global();
        server.config(config.get("server"))
                .routing(ServerApplication::routing);
    }

    /**
     * Setup routing.
     *
     * @param routing routing builder
     */
    private static void routing(HttpRouting.Builder routing) {
        routing.register("/greet", new GreetService());
    }
}