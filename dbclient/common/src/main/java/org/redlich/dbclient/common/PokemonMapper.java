package org.redlich.dbclient.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.helidon.dbclient.DbColumn;
import io.helidon.dbclient.DbMapper;
import io.helidon.dbclient.DbRow;

/**
 * Maps database statements to {@link io.helidon.examples.dbclient.common.Pokemon} class.
 */
public class PokemonMapper implements DbMapper<Pokemon> {

    @Override
    public Pokemon read(DbRow row) {
        DbColumn name = row.column("name");
        // we know that in mongo this is not true
        if (null == name) {
            name = row.column("_id");
        }

        DbColumn type = row.column("type");
        return new Pokemon(name.as(String.class), type.as(String.class));
    }

    @Override
    public Map<String, Object> toNamedParameters(Pokemon value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", value.getName());
        map.put("type", value.getType());
        return map;
    }

    @Override
    public List<Object> toIndexedParameters(Pokemon value) {
        List<Object> list = new ArrayList<>(2);
        list.add(value.getName());
        list.add(value.getType());
        return list;
    }
}
