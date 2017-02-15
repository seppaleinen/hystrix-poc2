#!/usr/bin/env bash

docker service rm composite-service service-1 service-2 config-server rabbitmq monitor-dashboard auth-server edge-server zipkin-server
