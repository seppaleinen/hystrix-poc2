#!/usr/bin/env bash

gradle clean build -xtest

docker build -t service-1 service-1
docker build -t service-2 service-2
docker build -t composite-service composite-service
docker build -t config-server config-server
docker build -t monitor-dashboard monitor-dashboard
docker build -t auth-server auth-server
docker build -t edge-server edge-server
docker build -t zipkin-server zipkin-server
