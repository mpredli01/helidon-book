package org.redlich.dbclient;

import io.helidon.common.Reflected;

/**
 * POJO representing a very simplified Pokemon.
 */
@Reflected
public class Pokemon {
    private String name;
    private String type;

    /**
     * Default constructor.
     */
    public Pokemon() {
        // JSON-B
        }

    /**
     * Create pokemon with name and type.
     *
     * @param name name of the beast
     * @param type type of the beast
     */
    public Pokemon(String name, String type) {
        this.name = name;
        this.type = type;
        }

    public String getName() {
        return name;
        }

    public void setName(String name) {
        this.name = name;
        }

    public String getType() {
        return type;
        }

    public void setType(String type) {
        this.type = type;
        }
    }