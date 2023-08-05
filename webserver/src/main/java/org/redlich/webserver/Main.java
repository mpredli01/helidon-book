/*
 *
 */
package org.redlich.webserver;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.media.jsonb.JsonbSupport;
import io.helidon.metrics.serviceapi.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(final String[] args ) {
        startServer();
        }

    /**
     * Start the server.
     *
     * @return the created {@link io.helidon.webserver.WebServer} instance
     */
    static WebServer startServer() {

        // Load logging configuration
        LogConfig.configureRuntime();

        // By default, this will pick up application.yaml from the classpath
        Config config = Config.create();

        WebServer server = WebServer.builder(createRouting(config))
                .config(config.get("server"))
                .addMediaSupport(JsonpSupport.create())
                .addMediaSupport(JsonbSupport.create())
                .build();

        // Start the server and print some information
        server.start().thenAccept(ws -> {
            System.out.println(
                    "WEB server is up! http://localhost:" + ws.port() + "/");
            });

        // Server threads are not daemon. There is NO need to block, just react.
        server.whenShutdown().thenRun(() -> System.out.println("WEB server is DOWN. Good bye!"));

        return server;
        }

    /**
     * Creates new {@link io.helidon.webserver.Routing}.
     *
     * @param config configuration of this server
     * @return routing configured with JSON support, a health check, and a service
     */
    private static Routing createRouting(Config config) {

        GreetService greetService = new GreetService(config);

        MetricsSupport metrics = MetricsSupport.create();

        HealthSupport health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();

        return Routing.builder()
                .register("/greet", greetService)
                .register(metrics) // register "/metrics" endpoint
                .register(health) // register "/health" endpoint
                .build();
        }
    }
