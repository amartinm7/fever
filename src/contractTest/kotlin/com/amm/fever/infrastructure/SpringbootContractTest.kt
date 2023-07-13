package com.amm.fever.infrastructure

import com.amm.fever.infrastructure.framework.FeverApplication
import com.amm.fever.infrastructure.framework.config.ControllerAdvice
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ActiveProfiles("test")
@SpringBootTest(
    classes = [DockerComposeConfig::class, FeverApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class SpringbootContractTest {

    abstract fun setUpController(): Any

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        val mvc = MockMvcBuilders
            .standaloneSetup(setUpController())
            .setMessageConverters(jacksonConverter())
            .setControllerAdvice(ControllerAdvice())
            .build()

        RestAssuredMockMvc.mockMvc(mvc)
    }

    private fun jacksonConverter() = MappingJackson2HttpMessageConverter(objectMapper)

}
