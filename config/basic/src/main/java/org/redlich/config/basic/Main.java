package org.redlich.config.basic;

import java.nio.file.Path;
import java.util.List;

import io.helidon.config.Config;

import static io.helidon.config.ConfigSources.classpath;

/**
 * Basics example.
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
        Config config = Config.create(classpath("application.conf"));

        int pageSize = config.get("app.page-size").asInt().get();

        boolean storageEnabled = config.get("app.storageEnabled").asBoolean().orElse(false);

        List<Integer> basicRange = config.get("app.basic-range").asList(Integer.class).get();

        Path loggingOutputPath = config.get("logging.outputs.file.name").as(Path.class).get();

        System.out.println(pageSize);
        System.out.println(storageEnabled);
        System.out.println(basicRange);
        System.out.println(loggingOutputPath);
    }

}