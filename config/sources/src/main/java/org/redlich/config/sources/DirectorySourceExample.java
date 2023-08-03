package org.redlich.config.sources;

import io.helidon.config.Config;

import static io.helidon.config.ConfigSources.directory;

/**
 * This example shows how to read configuration from several files placed in selected directory.
 */
public class DirectorySourceExample {

    private DirectorySourceExample() {
    }

    /**
     * Executes the example.
     *
     * @param args arguments
     */
    public static void main(String... args) {
        /*
           Creates a config from files from specified directory.
           E.g. Kubernetes Secrets:
         */

        System.out.println("--- DirectorySourceExample ---");
        
        Config secrets = Config.builder(directory("conf/secrets"))
                .disableEnvironmentVariablesSource()
                .disableSystemPropertiesSource()
                .build();

        String username = secrets.get("username").asString().get();
        System.out.println("Username: " + username);
        assert username.equals("libor");

        String password = secrets.get("password").asString().get();
        System.out.println("Password: " + password);
        assert password.equals("^ery$ecretP&ssword");
    }

}
