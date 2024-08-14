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

package org.redlich.config.basic;

import java.nio.file.Path;
import java.util.List;

import io.helidon.logging.common.LogConfig;
import io.helidon.config.Config;

import static io.helidon.config.ConfigSources.classpath;

/**
 * Basic example.
 */
public class Main {

    private Main() {
        }

    /**
     * Executes the example.
     *
     * @param args arguments
     */
    public static void main(String... args) {

        // Load logging configuration
        LogConfig.configureRuntime();

        displaySplashScreen();

        final String configFile = "application.conf";

        Config config = Config.create(classpath(configFile));
        System.out.println("[APP] Reading configuration from '" + configFile + "'");

        
        int pageSize = config.get("app.page-size").asInt().get();
        boolean storageEnabled = config.get("app.storageEnabled").asBoolean().orElse(false);
        List<Integer> basicRange = config.get("app.basic-range").asList(Integer.class).get();
        Path loggingOutputPath = config.get("logging.outputs.file.name").as(Path.class).get();

        System.out.println("[APP] 'page-size' configured value = " + pageSize);
        System.out.println("[APP] 'storageEnabled' configured value = " + storageEnabled);
        System.out.println("[APP] 'basic-range' configured value = " + basicRange);
        System.out.println("[APP] 'logging.outputs.file.name' configured value = " + loggingOutputPath);
        System.out.println();
        }

    public static void displaySplashScreen() {
        String title = " Helidon Config Example (Basic) ";
        int length = title.length();

        System.out.println();
        System.out.print("[APP] ");
        for (int i = 0; i < length; ++i)
            System.out.print("-");
        System.out.println();
        System.out.println("[APP] " + title);
        System.out.print("[APP] ");
        for (int i = 0; i < length; ++i)
            System.out.print("-");
        System.out.println();
        }
    }
