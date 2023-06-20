#!/bin/bash

# refresh the compiled files
./gradlew clean build

# create docker image
docker build -t ms-fever --no-cache .
