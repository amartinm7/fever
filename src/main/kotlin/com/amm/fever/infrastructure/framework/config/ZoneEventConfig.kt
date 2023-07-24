package com.amm.fever.infrastructure.framework.config

import com.amm.fever.application.zone.CreateZoneEventService
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.infrastructure.framework.zone.repository.jpa.JpaZoneEventRepository
import com.amm.fever.infrastructure.framework.zone.repository.jpa.JpaZoneRepositoryClient
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
        providerEventRepository: ProviderEventRepository
    ): CreateZoneEventService = CreateZoneEventService(
        jpaZoneEventRepository, providerEventRepository
    )
}