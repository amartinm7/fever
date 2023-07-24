package com.amm.fever.application.zone

import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.ModifiedAt
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.Version
import com.amm.fever.domain.vo.ZoneId
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import com.amm.fever.event.PerformanceEventFixtures
import com.amm.fever.zone.GigZoneFixtures
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
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

    @InjectMockKs
    private lateinit var createZoneEventService: CreateZoneEventService

    @Test
    fun `should create a new zone`() = runBlocking {
        `mock the external provider repository call and returns a list of performance events`()
        `mock saving performance event on repository`()
        createZoneEventService.execute(CreateZoneEventServiceRequest(UUID.randomUUID()))
        verify { zoneRepository.save(any<Zone>()) }
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
}