# Testing fever with load balancers

## Scenario

In this scenario, the Nginx container is acting as a reverse proxy server. It listens on port 8080 for incoming HTTP requests and forwards them to the Fever containers running on port 8000.

When a client sends an HTTP request to the Nginx server on port 8080, the server block inside the `http` section of the Nginx configuration is triggered. The `location /` directive specifies that any request coming to the root path should be proxied to the backend server.

The `proxy_pass` directive is responsible for forwarding the request to the specified backend server. In this case, it is set to `http://fever:8000`, which means the request will be forwarded to the Fever containers on port 8000.

By using this configuration, the Nginx container acts as a single entry point for client requests and distributes them to the Fever containers, allowing load balancing and high availability. It also provides a layer of abstraction, as clients are not directly accessing the Fever containers but interacting with them through the Nginx server.

When you define services in the same Docker network, Docker automatically provides a built-in DNS resolver that allows service name resolution. In this case, the Nginx container and the Fever app containers are part of the same Docker network (fever-network). This allows the Nginx container to resolve the service name fever to the IP addresses of the Fever app containers.

In the Nginx configuration, the line proxy_pass http://fever:8000; specifies the target backend server as http://fever:8000. This service name, fever, is resolved by Docker's internal DNS resolver to the IP addresses of the Fever app containers. Docker handles the internal routing and load balancing of the requests from Nginx to the available Fever app containers.

Therefore, you don't need to explicitly define the IP addresses or hostnames of the Fever app containers in the Nginx configuration. Docker's DNS resolution takes care of mapping the service name to the actual IP addresses of the containers within the Docker network.

## Testing

The main goal of the test is testing in an environment with several fever containers running at the same time. 

![docker_ps.jpg](_img%2Fdocker_ps.jpg)

![docker_stats.jpg](_img%2Fdocker_stats.jpg)

But not only that, but the number of containers is scaling up and down until stop all the containers.

![rescalling.jpg](_img%2Frescalling.jpg)

Meanwhile, the containers are running, there is a nginx server which is listening on the port 8080, and for every request that is receiving, is redirecting it to one available container running on the port 8000. 

![sending_messages.jpg](_img%2Fsending_messages.jpg)

![testing_with_balancers.jpg](_img%2Ftesting_with_balancers.jpg)

Every container is running on

