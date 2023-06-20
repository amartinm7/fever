package com.amm.fever.infrastructure

import com.amm.fever.infrastructure.framework.FeverApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@ActiveProfiles("test")
@SpringBootTest(
    classes = [DockerComposeConfig::class, FeverApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
abstract class SpringBootIntegrationTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc
}


