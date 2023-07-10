#!/bin/bash
#
# To kill the process
# pkill -f "./zipkin.sh"

# send messages
#for index in {1..10000}
#do
#curl -X POST -s -location "http://localhost:9411/api/v2/spans" -d @yelp.json \
#  -H "Content-Type: application/json"
#  printf "\nmessage sent ${index}"
#  sleep 1
#done

curl -X POST -s -location "http://localhost:9410/api/v2/spans" \
  -H "Content-Type: application/json" \
  -d '[ { "traceId": "a03ee8fff1dcd9b9", "parentId": "f5f268651b2a2b34", "id": "15fc03927f0f68df", "kind": "CLIENT", "name": "post", "timestamp": 1571896375322000, "duration": 14000, "localEndpoint": { "serviceName": "mobile_api" }, "remoteEndpoint": { "serviceName": "blt", "port": 31882 }, "tags": { "client_status_code": "200", "http.uri.client": "/visits", "request_budget": "9980", "tracer": "syslog2scribe.haproxy-synapse" } }]'