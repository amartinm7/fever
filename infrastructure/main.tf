terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

resource "docker_network" "fever-network" {
  name = "fever-network"
}

module "fever" {
  source = "./containers/fever"
}

module "database" {
  source = "./containers/postgres"
}

module "wiremock" {
  source = "./containers/wiremock"
}

module "zipkin" {
  source = "./containers/zipkin"
}
