## Helidon Revealed
#### A Practical Guide to Getting Started with Oracle's Microservices Framework

### Helidon DBClient Example (MongoDB)

### Prerequisite

Please ensure to have your local instance of MongoDB running on your workstation

### Build the Application

```bash
$ mvn clean compile exec:java
```

### Exercise the Application

```bash
localhost:8079/db
localhost:8079/metrics
localhost:8079/health
```

### cURL Commands:

#### List all Pokémon in the database

```bash
$ curl http://localhost:8079/db
```

#### Add new Pokémon to the database

```bash
$ curl -i -X PUT -d '{"name":"Squirtle","type":"Water"}' http://localhost:8079/db
$ curl -i -X PUT -d '{"name":"Caterpie","type":"Bug"}' http://localhost:8079/db
$ curl -i -X PUT -d '{"name":"Rattata","type":"Dark"}' http://localhost:8079/db
```

#### Retrieve a single Pokémon from the database

```bash
$ curl http://localhost:8079/db/Squirtle
```

#### Delete a single Pokémon from the database
   
```bash
$ curl -i -X DELETE http://localhost:8079/db/Squirtle
```


#### Delete all Pokémon from the database

```bash
$ curl -i -X DELETE http://localhost:8079/db
```

### Pokémon Types

These are the 18 types of Pokémon according to this [website](https://pokemondb.net/type).

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

### List of Pokémon

You can find a list of Pokémon at this [website](https://pokemondb.net/pokedex/all).
