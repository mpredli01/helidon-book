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

package org.redlich.dbclient.jdbc;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.helidon.dbclient.DbClient;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

import org.redlich.dbclient.common.AbstractPokemonService;

/**
 * Example service using a database.
 */
public class PokemonService extends AbstractPokemonService {

    PokemonService(DbClient dbClient) {
        super(dbClient);

        // dirty hack to prepare database for our POC
        // MySQL init
        long count = dbClient().execute().namedDml("create-table");
        System.out.println(count);
    }

    @Override
    protected void deleteAllPokemons(ServerRequest req, ServerResponse res) {
        // this is to show how ad-hoc statements can be executed (and their naming in Tracing and Metrics)
        long count = dbClient().execute().createDelete("DELETE FROM pokemons").execute();
        res.send("Deleted: " + count + " values");
    }
}