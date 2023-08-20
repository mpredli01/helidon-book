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

package org.redlich.config.sources;

import io.helidon.config.Config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.helidon.config.ConfigSources.*;

public class TestDirectorySourceExample {

    @BeforeAll
    public Config getConfig() {
        // creates a config from files from specified directory, e.g., Kubernetes Secrets
        Config config = Config.builder(directory("conf/secrets"))
                .disableEnvironmentVariablesSource()
                .disableSystemPropertiesSource()
                .build();
        return config;
        }

    @Test
    public void testDirectorySourceExample() {
        Config config = getConfig();

        String username = config.get("username").asString().get();
        String password = config.get("password").asString().get();

        assert username.equals("libor");
        assert password.equals("^ery$ecretP&ssword");
        }
    }
