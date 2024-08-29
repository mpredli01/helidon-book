## Helidon Revealed
#### A Practical Guide to Getting Started with Oracle's Microservices Framework

### Helidon Config Example (Sources)

This example demonstrates how to load configuration from multiple configuration sources.

1. [`DirectorySourceExample.java`](./src/main/java/org/redlich/config/sources/DirectorySourceExample.java)
   reads configuration from multiple files in a directory by specifying only the directory.
2. [`LoadSourcesExample.java`](./src/main/java/org/redlich/config/sources/LoadSourcesExample.java)
   uses _meta-configuration_ files [`conf/meta-config.yaml`](./conf/meta-config.yaml)
   and [`src/main/resources/meta-config.yaml`](./src/main/resources/meta-config.yaml)
   which contain not the configuration itself but
   _instructions for loading_ the configuration: what type, from where, etc. It also
   applies a filter to modify config values whose keys match a certain pattern.
3. [`WithSourcesExample.java`](./src/main/java/org/redlich/config/sources/WithSourcesExample.java)
   combines multiple config sources into a single configuration instance (and adds a
   filter.


### Build and Run

Please refer to the specific `README.md` files for
```bash
mainClass=org.redlich.config.sources.Application
$ mvn clean compile exec:java 
```

```bash
mvn package
java -jar target/helidon-examples-config-sources.jar
```
