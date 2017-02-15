#!/usr/bin/env bash

docker service create --replicas 1 --name service-1 --network my_network service-1
docker service create --replicas 1 --name service-2 --network my_network service-2
docker service create --replicas 1 --name composite-service --network my_network composite-service
docker service create --replicas 1 --name monitor-dashboard  --publish 7979:7979 --network my_network monitor-dashboard
docker service create --replicas 1 --name rabbitmq --publish 15672:15672 --network my_network rabbitmq:3-management
docker service create --replicas 1 --name auth-server --publish 9999:9999 --network my_network auth-server
docker service create --replicas 1 --name edge-server --publish 443:8765 --network my_network edge-server
docker service create --replicas 1 --name zipkin-server --publish 9411:9411 --network my_network zipkin-server

docker service create \
    --replicas 1 --name config-server  --publish 8888:8888 --network my_network \
    --mount type=bind,source=$PWD/config-repo,destination=/config-repo \
    config-server
