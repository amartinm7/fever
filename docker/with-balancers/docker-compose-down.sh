#!/usr/bin/env bash

docker-compose down

pkill -f "./send_messages.sh"