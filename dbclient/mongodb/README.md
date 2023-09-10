# Helidon Revealed
## A Practical Guide to Getting Started with Oracle's Microservices Framework
## Helidon DBClient Example (MongoDB)

### Prerequisite

Please ensure to have your local instance of MongoDB running on your workstation

### Build the Application

`$ mvn clean compile exec:java`

### Exercise the Application

* `localhost:8079/db`
* `localhost:8079/metrics`
* `localhost:8079/health`

### cURL Commands:

#### List all Pokémons in the database

* `curl http://localhost:8079/db`

#### Add new Pokémons to the database

* `$ curl -i -X PUT -d '{"name":"Squirtle","type":"Water"}' http://localhost:8079/db`
* `$ curl -i -X PUT -d '{"name":"Caterpie","type":"Bug"}' http://localhost:8079/db`
* `$ curl -i -X PUT -d '{"name":"Rattata","type":"Dark"}' http://localhost:8079/db`

#### Retrieve a single Pokémon from the database

* `$ curl http://localhost:8079/db/Squirtle`

#### Delete a single Pokémon from the database

* `$ curl -i -X DELETE http://localhost:8079/db/Squirtle`

#### Delete all Pokémons from the database

* `$ curl -i -X DELETE http://localhost:8079/db`

### Pokemon Types

These are the 18 types of Pokémons according to this [website](https://pokemondb.net/type).

* NORMAL
* FIRE 
* WATER
* ELECTRIC
* GRASS
* ICE
* FIGHTING
* POISON
* GROUND
* FLYING
* PSYCHIC
* BUG
* ROCK
* GHOST
* DRAGON
* DARK
* STEEL
* FAIRY

### List of Pokémons

You can find a list of Pokémons at this [website](https://pokemondb.net/pokedex/all).
