# Comicshop MonoRepo :rocket:

To start the project simple execute the docker compose file

## Contained Services :ship:

### Frontend

- React/TS/Vite app

### Product Service

- Spring Boot App
- Connected to MongoDB

### Notification Service

- Spring Boot App
- Spring Mail Integration
- Connected to MongoDB

### Other Services

- Keycloak via Docker Compose
- RabbitMQ via Docker Compose

## Development Instructions :construction_worker:

### Docker Compose

- `./docker-compose.yml` automatically pulls all required images, builds the containers and persistent volumes and runs all services
- In order to use it, make sure you have Docker installed and running
- Navigate to this directory `cd comicshop-mono` and run `docker compose up`
  - alternatively, you can use the IntelliJ Docker Plugin or the Docker Desktop Dashboard (Mac/Win)
- Sometimes, the `depends_on` order does not work, tho
- To get it right, just manually shutdown the dependant and start them manually after the dependencies are up
  - e.g. `product-service` sometimes can't connect to `product-db` or `rabbitmq-container`
  - therefore, just wait for the db & mq to be completely online and restart `product-service` via Docker Dashboard/IntelliJ

### Local Development in Docker

- While working on a service, you'll obviously have to edit/restart it often
- Doing this in Docker is tedious (you'd have to shutdown the container, delete it and its image, rebuild it and restart it freshly)
- Therefore, it's best to only start the services you won't actively be working on via Docker and the others locally

  - e.g. "I want to update the `cart-service`"
    - I start up the docker-compose file but immidiately kill the `cart-service`` container
    - Instead, I run the cart service locally via IntelliJ
    - Therefore I can quickly refresh my dev environment for the local service but still have the other stuff available in Docker

- This does not count for the frontend -- it's forwarding the hot-module-reloading and all code changes are directly visible in the Docker instance

### Setting up Keycloak

In order to set up keycloak locally, you have to do the following things:

- Login with username `admin` and password `admin` under `localhost:8090`
  - You're now in the `Master` realm. We need to create our own on, though
- In the **Realms** dropdown, create a `Profile-service` realm

- In **Clients**, create a new client for the **frontend** with the following details:

- In **Realm Roles**, create two new roles: `customer` and `shop-admin`
- In **Users**, create two new users (one customer, on admin), name them how you like
- After entering the info, don't forget to add **non-temporary** credentials!!!
- After creating them, you may have to manually select them again to add them to to a group
  - `Users` -> select your created user -> `Role Mapping` -> `Assign Role` -> Select the role

### Adding new services

#### Database initialization

- If you need to store data persistently, create a docker volume under `volumes:` within the `docker-compose.yml`
  - Make sure to reference your volume in the postgres container as well
- Spring Boot: In `/src/main/resources`, **always** set `spring.jpa.hibernate.ddl-auto=update` -- the default value `create` or `create-drop` will drop all db content!

#### Port-forwarding

- When adding the services to docker, make sure to forward it to a port that is not yet taken!
- e.g. spring services run on `localhost:8080` per default. We'll have ~6 spring services & they can't all run on the same port
- for such occasions, Docker has a `port-forwarding` mechanism that we use in the top-level `docker-compose` file
- This way we can map the services to different ports, e.g. `8080` for `product-service`, `8081` for `notification-service`, etc...
- The notation is always read as `machine_port:container_port` -> i.e. `8081:8080` for the notification service
- The same goes for postgres db ports, etc
