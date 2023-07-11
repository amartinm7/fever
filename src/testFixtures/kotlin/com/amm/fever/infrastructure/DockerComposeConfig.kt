package com.amm.fever.infrastructure

import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import java.io.File

@Configuration
class DockerComposeConfig {
    companion object {
        @Container
        private val docker = DockerComposeContainer<Nothing>(File("docker-compose.yml"))
            .apply {
                withLocalCompose(true)
                waitingFor("database", Wait.defaultWaitStrategy())
                waitingFor("wiremock", Wait.defaultWaitStrategy())
            }

        init {
            docker.start()
            Runtime.getRuntime().addShutdownHook(Thread { docker.stop() })
            Thread.sleep(5000L)
        }
    }
}