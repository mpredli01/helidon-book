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

import io.helidon.config.ConfigValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.helidon.config.ConfigSources.*;

public class TestMetaConfigSources {

    @BeforeAll
    public Config getConfig() {
        Config metaConfig = Config.create(file("conf/meta-config.yaml").optional(),
                classpath("meta-config.yaml"));

        Config config = Config.builder()
                .config(metaConfig)
                .addFilter((key, stringValue) -> key.name().equals("level") ? stringValue.toUpperCase() : stringValue)
                .build();
        return config;
        }

    @Test
    public void testLoadSourcesExample() {
        Config config = getConfig();

        // Optional environment type, from dev.yaml:
        ConfigValue<String> env = config.get("meta.env").asString();
        env.ifPresent(e -> System.out.println("[APP] Environment: " + e));
        assert env.get().equals("DEV");

        // Default value (default.yaml): Config Sources Example
        String appName = config.get("app.name").asString().get();
        assert appName.equals("Config Sources Example");

        // Page size, from config.yaml: 10
        int pageSize = config.get("app.page-size").asInt().get();
        assert pageSize == 10;

        // Applied filter (uppercase logging level), from dev.yaml: finest -> FINEST
        String level = config.get("component.audit.logging.level").asString().get();
        assert level.equals("FINE");
    }
}
