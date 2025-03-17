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

package org.redlich.config.sources;

import io.helidon.config.Config;

import static io.helidon.config.ConfigSources.directory;

/**
 * This example shows how to read configuration from several files placed in selected directory.
 */
public class DirectorySources {

    private DirectorySources() {
        }

    /**
     * Executes the example.
     *
     * @param args arguments
     */
    public static void main(String... args) {
        displaySplashScreen();

        // creates a config from files from specified directory, e.g., Kubernetes Secrets
        Config secrets = Config.builder(directory("conf/secrets"))
                .disableEnvironmentVariablesSource()
                .disableSystemPropertiesSource()
                .build();

        String username = secrets.get("username").asString().get();
        String password = secrets.get("password").asString().get();

        System.out.println("[APP] Username: " + username);
        System.out.println("[APP] Password: " + password);
        System.out.println();
        }

    public static void displaySplashScreen() {
        String title = " Directory Sources Example ";
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
