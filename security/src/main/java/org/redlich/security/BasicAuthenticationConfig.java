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
public final class BasicAuthenticationConfig {
    private BasicAuthenticationConfig() {
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

        long t = System.nanoTime();
        server.start();
        long time = System.nanoTime() - t;

        System.out.println("[APP] -------------------------------------------");
        System.out.println("[APP]  Configured Users Using Configuration File");
        System.out.println("[APP] -------------------------------------------");

        ConfigValue<String> jack = config.get("security.providers.0.http-basic-auth.users.0.login").asString();
        ConfigValue<String> jackPassword = config.get("security.providers.0.http-basic-auth.users.0.password").asString();
        ConfigValue<List<Config>> jackRoles = config.get("security.providers.0.http-basic-auth.users.0.roles").asNodeList();
        System.out.println("[APP TEST] " + jack);
        System.out.println("[APP TEST] " + jackPassword);
        System.out.println("[APP TEST] " + jackRoles);

        ConfigValue<String> jill = config.get("security.providers.0.http-basic-auth.users.1.login").asString();
        ConfigValue<String> jillPassword = config.get("security.providers.0.http-basic-auth.users.1.password").asString();
        ConfigValue<List<Config>> jillRoles = config.get("security.providers.0.http-basic-auth.users.1.roles").asNodeList();
        System.out.println("[APP TEST] " + jill);
        System.out.println("[APP TEST] " + jillPassword);
        System.out.println("[APP TEST] " + jillRoles);

        ConfigValue<String> john = config.get("security.providers.0.http-basic-auth.users.2.login").asString();
        ConfigValue<String> johnPassword = config.get("security.providers.0.http-basic-auth.users.2.password").asString();
        ConfigValue<List<Config>> johnRoles = config.get("security.providers.0.http-basic-auth.users.2.roles").asNodeList();
        System.out.println("[APP TEST] " + john);
        System.out.println("[APP TEST] " + johnPassword);
        System.out.println("[APP TEST] " + johnRoles);

        System.out.printf("""
                Server started in %d ms

                Signature example: from builder

                "Users:
                jack/password in roles: user, admin
                jill/password in roles: user
                john/password in no roles

                ***********************
                ** Endpoints:        **
                ***********************

                No authentication: http://localhost:8080/public
                No roles required, authenticated: http://localhost:8080/noRoles
                User role required: http://localhost:8080/user
                Admin role required: http://localhost:8080/admin
                Always forbidden (uses role nobody is in), audited: http://localhost:8080/deny
                Admin role required, authenticated, authentication optional, audited \
                (always forbidden - challenge is not returned as authentication is optional): http://localhost:8080/noAuthn
                Static content, requires user role: http://localhost:8080/static/index.html

                """, TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS));
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

