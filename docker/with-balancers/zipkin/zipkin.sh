#!/bin/bash

# send messages
for index in {1..10000}
do
curl -X POST -s -location "http://localhost:9411/api/v2/spans" -d @yelp.json \
  -H "Content-Type: application/json"
  printf "\nmessage sent ${index}"
  sleep 1
done