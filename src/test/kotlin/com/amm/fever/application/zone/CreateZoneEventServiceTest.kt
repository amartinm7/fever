package com.amm.fever.application.zone

import com.amm.fever.application.zone.create.CreateZoneEventService
import com.amm.fever.application.zone.create.CreateZoneEventServiceRequest
import com.amm.fever.application.zone.pubsubs.ZoneEventPublisher
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import com.amm.fever.event.PerformanceEventFixtures
import com.amm.fever.zone.GigZoneFixtures
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateZoneEventServiceTest {

    @MockK
    private lateinit var zoneRepository: ZoneEventEventRepository

    @MockK
    private lateinit var providerEventRepository: ProviderEventRepository

    @MockK
    private lateinit var zoneEventPublisher: ZoneEventPublisher

    @InjectMockKs
    private lateinit var createZoneEventService: CreateZoneEventService

    @Test
    fun `should create a new zone`() = runBlocking {
        `mock the external provider repository call and returns a list of performance events`()
        `mock saving performance event on repository`()
        `mock publish zone events`()
        createZoneEventService.execute(CreateZoneEventServiceRequest(UUID.randomUUID()))
        verify { zoneRepository.save(any<Zone>()) }
        verify { runBlocking { providerEventRepository.findBy() } }
        verify { runBlocking { zoneEventPublisher.publish(any()) } }
    }

    private fun `mock saving performance event on repository`() {
        every { zoneRepository.save(any()) } returns GigZoneFixtures.ANY_ZONE
    }

    private fun `mock the external provider repository call and returns a list of performance events`() {
        every {
            runBlocking {
                providerEventRepository.findBy()
            }
        } returns PerformanceEventFixtures.ANY_EVENTS
    }

    private fun `mock publish zone events`() {
        every {
            runBlocking {
                zoneEventPublisher.publish(any())
            }
        } returns Unit
    }
}