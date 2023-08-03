package org.redlich.dbclient.pokemons;

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
        DbColumn id = row.column("id");
        DbColumn name = row.column("name");
        DbColumn type = row.column("idType");
        return new Pokemon(id.as(Integer.class), name.as(String.class), type.as(Integer.class));
    }

    @Override
    public Map<String, Object> toNamedParameters(Pokemon value) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", value.getId());
        map.put("name", value.getName());
        map.put("idType", value.getIdType());
        return map;
    }

    @Override
    public List<Object> toIndexedParameters(Pokemon value) {
        List<Object> list = new ArrayList<>(3);
        list.add(value.getId());
        list.add(value.getName());
        list.add(value.getIdType());
        return list;
    }
}