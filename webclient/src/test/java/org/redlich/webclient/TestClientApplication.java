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

package org.redlich.webclient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import io.helidon.config.Config;
import io.helidon.metrics.api.Counter;
import io.helidon.metrics.api.MeterRegistry;
import io.helidon.metrics.api.Metrics;
import io.helidon.webserver.testing.junit5.ServerTest;
import io.helidon.webserver.testing.junit5.SetUpServer;
import io.helidon.webclient.api.WebClient;
import io.helidon.webclient.spi.WebClientService;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.WebServerConfig;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for verification of WebClient example.
 */
@ServerTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestClientApplication {

    private static final MeterRegistry METRIC_REGISTRY = Metrics.globalRegistry();

    private final WebServer server;
    private final Path testFile;

    public TestClientApplication(WebServer server) {
        this.server = server;
        server.context().register(server);
        this.testFile = Paths.get("test.txt");
    }

    @SetUpServer
    public static void setup(WebServerConfig.Builder server) {
        Config.global(Config.create());
        ServerApplication.setup(server);
    }

    @AfterEach
    public void afterEach() throws IOException {
        Files.deleteIfExists(testFile);
    }

    private WebClient client(WebClientService... services) {
        Config config = Config.create();
        return WebClient.builder()
                .baseUri("http://localhost:" + server.port() + "/greet")
                .config(config.get("client"))
                .update(it -> Stream.of(services).forEach(it::addService))
                .build();
    }

    @Test
    @Order(1)
    public void testPerformRedirect() {
        WebClient client = client();
        String greeting = ClientApplication.followRedirects(client);
        assertThat(greeting, is("{\"message\":\"Hello World!\"}"));
    }

    @Test
    @Order(2)
    public void testFileDownload() throws IOException {
        WebClient client = client();
        ClientApplication.saveResponseToFile(client);
        assertThat(Files.exists(testFile), is(true));
        assertThat(Files.readString(testFile), is("{\"message\":\"Hello World!\"}"));
    }

    @Test
    @Order(3)
    public void testMetricsExample() {
        String counterName = "example.metric.GET.localhost";
        Counter counter = METRIC_REGISTRY.getOrCreate(Counter.builder(counterName));
        assertThat("Counter " + counterName + " has not been 0", counter.count(), is(0L));
        ClientApplication.clientMetricsExample("http://localhost:" + server.port() + "/greet", Config.create());
        assertThat("Counter " + counterName + " " + "has not been 1", counter.count(), is(1L));
    }

    @Test
    @Order(4)
    public void testPerformPutAndGetMethod() {
        WebClient client = client();
        String greeting = ClientApplication.performGetMethod(client);
        assertThat(greeting, is("{\"message\":\"Hello World!\"}"));
        ClientApplication.performPutMethod(client);
        greeting = ClientApplication.performGetMethod(client);
        assertThat(greeting, is("{\"message\":\"Hola World!\"}"));
    }

}