#!/bin/bash

# remove containers
docker rm -f $(docker ps -a -q)

# refresh the compiled files
./gradlew clean build

# create docker image
docker build -t ms-fever --no-cache .
