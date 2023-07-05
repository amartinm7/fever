#!/bin/bash

## run three instances of the fever app
docker-compose up -d --scale fever=3