terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

variable "wiremock_stubs_path" {
  description = "Path to the wiremock stubs directory"
  type        = string
  default     = "/Users/antonio.martin/ideaProjectsGitHub/fever-coroutines/infrastructure/containers/wiremock/mappings/stubs"
}

variable "wiremock_responses_path" {
  description = "Path to the wiremock responses directory"
  type        = string
  default     = "/Users/antonio.martin/ideaProjectsGitHub/fever-coroutines/infrastructure/containers/wiremock/mappings/response"
}

resource "docker_container" "wiremock" {
  image = "wiremock/wiremock:latest"
  name  = "wiremock"
  command = ["--port", "8090"]
  ports {
    internal = 8090
    external = 8090
  }
  volumes {
    host_path      = var.wiremock_stubs_path
    container_path = "/home/wiremock/mappings"
  }
  volumes {
    host_path      = var.wiremock_responses_path
    container_path = "/home/wiremock/__files"
  }
  networks_advanced {
    name = "fever-network"
  }
}

resource "docker_volume" "wiremock_stubs" {
  name = "wiremock_stubs"
}

resource "docker_volume" "wiremock_responses" {
  name = "wiremock_responses"
}

resource "null_resource" "copy_wiremock_resources" {
  provisioner "local-exec" {
    command = "docker cp ${var.wiremock_stubs_path} ${docker_container.wiremock.id}:/home/wiremock/mappings/"
  }

  provisioner "local-exec" {
    command = "docker cp ${var.wiremock_responses_path} ${docker_container.wiremock.id}:/home/wiremock/__files/"
  }

  depends_on = [docker_container.wiremock]
}

