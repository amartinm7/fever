package com.amm.fever.infrastructure.framework.config

import com.amm.fever.application.zone.create.CreateZoneEventService
import com.amm.fever.application.zone.pubsubs.ZoneEventPublisher
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.zone.ZoneEventEventRepository
import com.amm.fever.infrastructure.framework.zone.repository.jpa.JpaZoneEventRepository
import com.amm.fever.infrastructure.framework.zone.repository.jpa.JpaZoneRepositoryClient
import com.amm.fever.application.zone.pubsubs.ZoneEventSubscriber
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneWasSavedEvent
import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ZoneEventConfig {
    @Bean
    fun jpaZoneRepository(jpaZoneRepositoryClient: JpaZoneRepositoryClient): JpaZoneEventRepository =
        JpaZoneEventRepository(jpaZoneRepositoryClient)

    @Bean
    fun createZoneService(
        jpaZoneEventRepository: JpaZoneEventRepository,
        providerEventRepository: ProviderEventRepository,
        zoneEventPublisher: ZoneEventPublisher
    ): CreateZoneEventService = CreateZoneEventService(
        jpaZoneEventRepository,
        providerEventRepository,
        zoneEventPublisher
    )

    @Bean
    fun zoneEventChannel() = Channel<ZoneWasSavedEvent>()

    @Bean
    fun zoneEventPublisher(
        zoneEventChannel: Channel<ZoneWasSavedEvent>
    ): ZoneEventPublisher =
        ZoneEventPublisher(zoneEventChannel)

    @Bean
    fun zoneEventSubscriber(
        zoneRepository: ZoneEventEventRepository,
        zoneEventChannel: Channel<ZoneWasSavedEvent>
    ): ZoneEventSubscriber =
        ZoneEventSubscriber(
            zoneRepository,
            zoneEventChannel
        )
}