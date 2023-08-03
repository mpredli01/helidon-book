package org.redlich.dbclient.jdbc;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.helidon.dbclient.DbClient;
import org.redlich.dbclient.common.AbstractPokemonService;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

/**
 * Example service using a database.
 */
public class PokemonService extends AbstractPokemonService {
    private static final Logger LOGGER = Logger.getLogger(PokemonService.class.getName());

    PokemonService(DbClient dbClient) {
        super(dbClient);

        // dirty hack to prepare database for our POC
        // MySQL init
        dbClient.execute(handle -> handle.namedDml("create-table"))
                .thenAccept(System.out::println)
                .exceptionally(throwable -> {
                    LOGGER.log(Level.WARNING, "Failed to create table, maybe it already exists?", throwable);
                    return null;
                });
        }

    /**
     * Delete all pokemons.
     *
     * @param request  the server request
     * @param response the server response
     */
    @Override
    protected void deleteAllPokemons(ServerRequest request, ServerResponse response) {
        dbClient().execute(exec -> exec
                        // this is to show how ad-hoc statements can be executed (and their naming in Tracing and Metrics)
                        .createDelete("DELETE FROM pokemons")
                        .execute())
                .thenAccept(count -> response.send("Deleted: " + count + " values"))
                .exceptionally(throwable -> sendError(throwable, response));
        }
    }
