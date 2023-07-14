package com.amm.fever.application.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.event.GigEventFixtures
import com.amm.fever.event.PerformanceEventFixtures
import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_EVENTS
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ExternalProviderServiceTest {

    @MockK
    private lateinit var providerEventRepository: ProviderEventRepository

    @MockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var externalProviderService: ExternalProviderService

    @Test
    fun `should save a list of events`() = runBlocking {
        `mock saving performance event on repository`()
        `mock the external provider repository call and returns a list of performance events`()

        val expected = ANY_EVENTS
        val response = externalProviderService.execute(ExternalProviderServiceRequest(""))

        assertThat(response.i).isEqualTo("")
        verify(exactly = 1) {
            runBlocking {
                providerEventRepository.findBy()
            }
        }
        verify(exactly = 1) {
            runBlocking {
                eventRepository.save(
                    event = ANY_EVENTS[0]
                )
            }
        }
    }
    private fun `mock saving performance event on repository`() {
        val event = PerformanceEventFixtures.ANY_EVENTS[0]
        every {
            eventRepository.save(event = event)
        } returns event
    }

    private fun `mock the db repository call and returns a list of events`() {
        every {
            runBlocking {
                eventRepository.findBy(startsAt = GigEventFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
            }
        } returns ANY_EVENTS
    }

    private fun `mock the external provider repository call and returns a list of performance events`() {
        every {
            runBlocking {
                providerEventRepository.findBy()
            }
        } returns ANY_EVENTS
    }
}