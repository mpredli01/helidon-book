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

import io.helidon.logging.common.LogConfig;

/**
 * Runs every example main class in this module/package.
 */
public class Main {

    private Main() {
        }

    /**
     * Executes the example.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        LogConfig.configureRuntime();
        displaySplashScreen();
        WithSourcesExample.main(args);
        LoadSourcesExample.main(args);
        DirectorySourceExample.main(args);
        }

    public static void displaySplashScreen() {
        String title = " Helidon Config Example (Sources) ";
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
