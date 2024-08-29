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

import io.helidon.config.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static io.helidon.config.ConfigSources.classpath;

public class TestApplication {

    @BeforeAll
    public Config getConfig() {
        final String configFile = "application.conf";
        return Config.create(classpath(configFile));
        }

    @Test
    public void testMain() {
        Config config = getConfig();

        int pageSize = config.get("app.page-size").asInt().get();
        boolean storageEnabled = config.get("app.storageEnabled").asBoolean().orElse(false);
        List<Integer> basicRange = config.get("app.basic-range").asList(Integer.class).get();
        Path loggingOutputPath = config.get("logging.outputs.file.name").as(Path.class).get();

        assert pageSize == 20;
        assert !storageEnabled;
        assert basicRange.get(0) == -20;
        assert basicRange.get(1) == 20;
        assert loggingOutputPath.toString().equals("target/root.log");
        }
    }
