package com.amm.fever.infrastructure

import com.amm.fever.event.PerformanceEventFixtures.ANY_CREATED_AT
import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_MAX_PRICE
import com.amm.fever.event.PerformanceEventFixtures.ANY_MIN_PRICE
import com.amm.fever.event.PerformanceEventFixtures.ANY_MODIFIED_AT
import com.amm.fever.event.PerformanceEventFixtures.ANY_ORGANIZER_COMPANY_ID
import com.amm.fever.event.PerformanceEventFixtures.ANY_PROVIDER_BASE_ID
import com.amm.fever.event.PerformanceEventFixtures.ANY_PROVIDER_ID
import com.amm.fever.event.PerformanceEventFixtures.ANY_SELL_FROM
import com.amm.fever.event.PerformanceEventFixtures.ANY_SELL_TO
import com.amm.fever.event.PerformanceEventFixtures.ANY_SOLD_OUT
import com.amm.fever.event.PerformanceEventFixtures.ANY_START_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_TITLE
import com.amm.fever.event.PerformanceEventFixtures.ANY_UUID
import com.amm.fever.event.PerformanceEventFixtures.ANY_ZONES
import com.amm.fever.infrastructure.framework.FeverApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate


@ActiveProfiles("test")
@SpringBootTest(
    classes = [DockerComposeConfig::class, DatabaseConfig::class, RestTemplateConfig::class, FeverApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
abstract class SpringbootAcceptanceTest {

    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var restTemplate: RestTemplate

    @Autowired
    protected lateinit var eventDatabaseSupport: EventDatabaseSupport
}

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    @Bean
    fun restTemplateBuilder() = RestTemplateBuilder()
}

@Configuration
class DatabaseConfig {

    @Bean
    fun eventDatabaseSupport(
        jdbcTemplate: NamedParameterJdbcTemplate
    ) = EventDatabaseSupport(jdbcTemplate)
}

class EventDatabaseSupport(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun createEvent() {
        val mapper: Map<String, Any> = mapOf(
            "ID" to ANY_UUID,
            "PROVIDER_ID" to ANY_PROVIDER_ID,
            "PROVIDER_BASE_ID" to ANY_PROVIDER_BASE_ID,
            "ORGANIZER_COMPANY_ID" to ANY_ORGANIZER_COMPANY_ID,
            "TITLE" to ANY_TITLE,
            "START_DATE" to ANY_START_DATE,
            "END_DATE" to ANY_END_DATE,
            "SELL_FROM" to ANY_SELL_FROM,
            "SELL_TO" to ANY_SELL_TO,
            "SOLD_OUT" to ANY_SOLD_OUT,
            "ZONES" to ANY_ZONES,
            "MIN_PRICE" to ANY_MIN_PRICE,
            "MAX_PRICE" to ANY_MAX_PRICE,
            "CREATED_AT" to ANY_CREATED_AT,
            "MODIFIED_AT" to ANY_MODIFIED_AT,
        )
        jdbcTemplate.update(
            """INSERT INTO EVENT (
                        ID, 
                        PROVIDER_ID, 
                        PROVIDER_BASE_ID, 
                        ORGANIZER_COMPANY_ID, 
                        TITLE,
                        START_DATE, 
                        END_DATE, 
                        SELL_FROM, 
                        SELL_TO, 
                        SOLD_OUT, 
                        ZONES, 
                        MIN_PRICE,
                        MAX_PRICE,
                        CREATED_AT, 
                        MODIFIED_AT
                    )
                    VALUES ( 
                        :ID::uuid, 
                        :PROVIDER_ID, 
                        :PROVIDER_BASE_ID, 
                        :ORGANIZER_COMPANY_ID, 
                        :TITLE,
                        :START_DATE, 
                        :END_DATE, 
                        :SELL_FROM, 
                        :SELL_TO, 
                        :SOLD_OUT, 
                        :ZONES::JSONB,
                        :MIN_PRICE,
                        :MAX_PRICE,
                         now(), 
                         now()
                   )""",
            mapper
        )
    }

    fun deleteEvent() {
        val mapper: Map<String, Any> = mapOf(
            "ID" to ANY_UUID,
        )
        jdbcTemplate.update(
            "DELETE FROM EVENT WHERE ID = :ID::uuid",
            mapper
        )
    }
}