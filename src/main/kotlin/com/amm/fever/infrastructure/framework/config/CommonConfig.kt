package com.amm.fever.infrastructure.framework.config

import com.amm.fever.infrastructure.services.OffsetDateTimeHandler
import com.amm.fever.infrastructure.services.UuidService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class CommonConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder()
            .serializers(LOCAL_DATETIME_SERIALIZER)
            //.serializationInclusion(JsonInclude.Include.NON_NULL)
            .build()
    }

    @Bean
    fun uuidService(): UuidService = UuidService()

    @Bean
    fun offsetDateTimeHandler() = OffsetDateTimeHandler()

    companion object {
        private const val DATE_TIME_PATTERN_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ"
        private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_ISO_8601)
        private val LOCAL_DATETIME_SERIALIZER = LocalDateTimeSerializer(DATE_TIME_FORMATTER)
    }
}
