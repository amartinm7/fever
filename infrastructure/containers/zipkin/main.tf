terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

resource "docker_container" "zipkin" {
  image = "openzipkin/zipkin:latest"
  name  = "zipkin"
  ports {
    internal = 9411
    external = 9411
  }
  networks_advanced {
    name = "fever-network"
  }
}
