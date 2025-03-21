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

package org.redlich.dbclient.mongodb;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbStatementType;
import io.helidon.dbclient.metrics.DbClientMetrics;
import io.helidon.dbclient.tracing.DbClientTracing;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.WebServerConfig;
import io.helidon.webserver.http.HttpRouting;

/**
 * Simple Hello World rest application.
 */
public final class MongoDbApplication {

    /**
     * Cannot be instantiated.
     */
    private MongoDbApplication() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        startServer();
    }

    /**
     * Start the server.
     *
     * @return the created {@link WebServer} instance
     */
    static WebServer startServer() {

        // load logging configuration
        LogConfig.configureRuntime();

        WebServer server = setupServer(WebServer.builder());

        System.out.println("WEB server is up! http://localhost:" + server.port() + "/");
        return server;
    }

    static WebServer setupServer(WebServerConfig.Builder builder) {
        // By default, this will pick up application.yaml from the classpath
        Config config = Config.create();

        return builder.routing(routing -> routing(routing, config))
                .config(config.get("server"))
                .build()
                .start();
    }

    /**
     * Setup routing.
     *
     * @param config configuration of this server
     */
    private static void routing(HttpRouting.Builder routing, Config config) {
        Config dbConfig = config.get("db");

        DbClient dbClient = DbClient.builder(dbConfig)
                // add an interceptor to named statement(s)
                .addService(DbClientMetrics.counter().statementNames("select-all", "select-one"))
                // add an interceptor to statement type(s)
                .addService(DbClientMetrics.timer()
                        .statementTypes(DbStatementType.DELETE, DbStatementType.UPDATE, DbStatementType.INSERT))
                // add an interceptor to all statements
                .addService(DbClientTracing.create())
                .build();

        routing.register("/db", new PokemonService(dbClient));
    }
}