package com.amm.fever.infrastructure.framework.config

import com.amm.fever.application.event.SearchEventService
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.infrastructure.framework.event.repository.extprovider.ExtProviderEventRepository
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEventRepository
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEventRepositoryClient
import com.amm.fever.infrastructure.services.OffsetDateTimeHandler
import com.amm.fever.infrastructure.services.UuidService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.OffsetDateTime

@Configuration
class EventConfig {
    @Bean
    fun searchEventService(
        eventRepository: EventRepository,
        providerEventRepository: ProviderEventRepository
    ) = SearchEventService(eventRepository, providerEventRepository)

    @Bean
    fun eventRepository(jpaEventRepositoryClient: JpaEventRepositoryClient, objectMapper: ObjectMapper) =
        JpaEventRepository(jpaEventRepositoryClient, objectMapper)

    @Bean
    fun providerEventRepository(
        eventExtProviderWebClient: WebClient,
        uuidService: UuidService,
        offsetDateTimeHandler: OffsetDateTimeHandler
    ) = ExtProviderEventRepository(eventExtProviderWebClient, uuidService, offsetDateTimeHandler)
}