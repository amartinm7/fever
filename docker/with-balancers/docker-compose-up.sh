#!/bin/bash

## run three instances of the fever app
docker-compose up -d --scale fever=3

sleep 30

sh ./send_messages.sh &

## run three instances of the fever app
docker-compose up -d --scale fever=5

sleep 30

## run three instances of the fever app
docker-compose up -d --scale fever=2

sleep 30

## run three instances of the fever app
docker-compose up -d --scale fever=1

sleep 30

# Stop the execution of send_messages.sh script
pkill -f "./send_messages.sh"

## run three instances of the fever app
docker-compose down

