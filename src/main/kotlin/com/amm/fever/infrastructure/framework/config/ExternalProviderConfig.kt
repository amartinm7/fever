package com.amm.fever.infrastructure.framework.config

import com.amm.fever.application.event.CreateEventService
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExternalProviderConfig {

    @Bean
    fun externalProviderService(
        eventRepository: EventRepository,
        providerEventRepository: ProviderEventRepository,
    ) = CreateEventService(
        eventRepository,
        providerEventRepository
    )
}