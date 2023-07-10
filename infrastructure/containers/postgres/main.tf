terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

resource "docker_container" "database" {
  image = "postgres:14.5"
  name  = "database"
  ports {
    internal = 5432
    external = 5432
  }
  env = [
    "POSTGRES_USER=userdb",
    "POSTGRES_PASSWORD=passdb",
    "POSTGRES_DB=feverdb"
  ]
  networks_advanced {
    name = "fever-network"
  }
}