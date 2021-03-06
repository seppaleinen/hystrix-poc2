# hystrix-poc2

### Excercises done:
* Excercise 1
* Excercise 2


### To build:
```
gradle build -xtest
```



### DOCKER:
```
# Init swarm
docker swarm init;
docker swarm join \
    --token SWMTKN-1-0wm4jp75canif0es5ob3yjal3fserzjgt540qrzx10qfn7bbfa-ag9t4ifzafxbmoy06uoaedilw \
    192.168.65.2:2377;

# Build images
docker build -t service-1 service-1;
docker build -t service-2 service-2;
docker build -t composite-service composite-service;

# Create network overlay
﻿docker network create --driver overlay my_network;

# Create services
docker service create --replicas 1 --name service-1 --network my_network service-1;
docker service create --replicas 1 --name service-2 --network my_network service-2;
docker service create --replicas 1 --name composite-service  --publish 8080:8080 --network my_network composite-service;


# Remove services
docker service rm service-1 && docker service rm service-2 && docker service rm composite-service; docker system prune -f;
```


### DOCKER-COMPOSE:
```
# To build all services
gradle clean build -xtest;

# To build and start all services
docker-compose up --build;

# To shut down all services
docker-compose kill;

# To shut down and remove all services
docker-compose down;
```


### DOCKER-COMPOSE/SWARM
```
# To build all jars
gradle clean build -xtest;

# To build all images
docker-compose build

# To deploy all images to swarm
docker stack deploy -c docker-compose.stack.yml stack

# To remove swarm stack
docker stack rm stack
```

```
curl -w "\n" localhost:8080/composite-resource/123
```

### Information

* Rabbitmq admin interface http://localhost:15672/ user/pass guest/guest
* Hystrix dashboard http://localhost:7979/
* 