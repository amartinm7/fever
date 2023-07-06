#!/bin/bash

# To kill the process
# pkill -f "./send_messages_for_stress.sh"

# send messages
for index in {1..10000}
do
  curl -X GET --location 'http://localhost:8000/search?starts_at=2021-06-30T21:00:00Z&ends_at=2022-07-17T14:18:29Z' \
  -H "Content-Type: application/json"
  printf "\nmessage sent ${index}"
done