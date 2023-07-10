terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

resource "docker_container" "fever" {
  image = "ms-fever:latest"
  name  = "fever-container"
  ports {
    internal = 8000
    external = 8000
  }
  networks_advanced {
    name = "fever-network"
  }
}