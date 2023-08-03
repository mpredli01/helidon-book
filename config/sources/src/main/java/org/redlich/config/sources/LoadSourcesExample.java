package org.redlich.config.sources;

import io.helidon.config.Config;
import io.helidon.config.ConfigValue;

import static io.helidon.config.ConfigSources.classpath;
import static io.helidon.config.ConfigSources.file;

/**
 * This example shows how to merge the configuration from different sources
 * loaded from meta configuration.
 *
 * @see WithSourcesExample
 */
public class LoadSourcesExample {

    private LoadSourcesExample() {
    }

    /**
     * Executes the example.
     *
     * @param args arguments
     */
    public static void main(String... args) {
        /*
           Creates a configuration from list of config sources loaded from meta sources:
           - conf/meta-config.yaml - - deployment dependent meta-config file, loaded from file on filesystem;
           - meta-config.yaml - application default meta-config file, loaded form classpath;
           with a filter which convert values with keys ending with "level" to upper case
         */

        System.out.println("--- LoadSourcesExample ---");

        Config metaConfig = Config.create(file("conf/meta-config.yaml").optional(),
                classpath("meta-config.yaml"));

        Config config = Config.builder()
                .config(metaConfig)
                .addFilter((key, stringValue) -> key.name().equals("level") ? stringValue.toUpperCase() : stringValue)
                .build();

        // Optional environment type, from dev.yaml:
        ConfigValue<String> env = config.get("meta.env").asString();
        env.ifPresent(e -> System.out.println("Environment: " + e));
        assert env.get().equals("DEV");

        // Default value (default.yaml): Config Sources Example
        String appName = config.get("app.name").asString().get();
        System.out.println("Name: " + appName);
        assert appName.equals("Config Sources Example");

        // Page size, from config.yaml: 10
        int pageSize = config.get("app.page-size").asInt().get();
        System.out.println("Page size: " + pageSize);
        assert pageSize == 10;

        // Applied filter (uppercase logging level), from dev.yaml: finest -> FINEST
        String level = config.get("component.audit.logging.level").asString().get();
        System.out.println("Level: " + level);
        assert level.equals("FINE");
    }

}
