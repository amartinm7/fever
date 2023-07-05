#!/bin/bash
for index in {1..1000}
do
  curl -X GET --location 'http://localhost:8080/search?starts_at=2021-06-30T21:00:00Z&ends_at=2022-07-17T14:18:29Z' \
  -H "Content-Type: application/json"
  printf "\nmessage sent ${index}"
done