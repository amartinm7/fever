#!/bin/bash

## run three instances of the fever app
docker-compose up -d --scale fever=3

sleep 30

## send messages in background
sh ./send_messages.sh

sleep 30