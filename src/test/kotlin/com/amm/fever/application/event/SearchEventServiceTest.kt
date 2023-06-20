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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SearchEventServiceTest {

    @MockK
    private lateinit var providerEventRepository: ProviderEventRepository

    @MockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var searchEventService: SearchEventService

    @Test
    fun `should return a list of merged events, skipping repeated events`() {
        `mock saving performance event on repository`()
        `mock the db repository call and returns a list of events`()
        `mock the external provider repository call and returns a list of performance events`()

        val expected = ANY_EVENTS
        val response = searchEventService.execute(GIG_AND_PERF_SEARCH_EVENT_SERVICE_REQUEST)

        assertThat(response.data).isEqualTo(expected)
        verify(exactly = 1) {
            providerEventRepository.findBy(
                startsAt = GigEventFixtures.ANY_START_DATE,
                endsAt = ANY_END_DATE
            )
        }
        verify(exactly = 1) {
            eventRepository.findBy(
                startsAt = GigEventFixtures.ANY_START_DATE,
                endsAt = ANY_END_DATE
            )
        }
        verify(exactly = 1) {
            eventRepository.save(
                event = ANY_EVENTS[0]
            )
        }
    }

    @Test
    fun `should return a list of merged events without any repeated`() {
        `mock saving performance event on repository`()
        `mock saving gig event on repository`()
        `mock the db repository call and returns a list of events`()
        `mock the external provider repository call and returns a list of gig events`()

        val expected = ANY_EVENTS.plus(GigEventFixtures.ANY_EVENTS)
        val response = searchEventService.execute(GIG_AND_PERF_SEARCH_EVENT_SERVICE_REQUEST)

        assertThat(response.data).isEqualTo(expected)
        verify(exactly = 1) {
            providerEventRepository.findBy(
                startsAt = GigEventFixtures.ANY_START_DATE,
                endsAt = ANY_END_DATE
            )
        }
        verify(exactly = 1) {
            eventRepository.findBy(
                startsAt = GigEventFixtures.ANY_START_DATE,
                endsAt = ANY_END_DATE
            )
        }
    }

    private fun `mock saving performance event on repository`() {
        val event = PerformanceEventFixtures.ANY_EVENTS[0]
        every {
            eventRepository.save(event = event)
        } returns event
    }

    private fun `mock saving gig event on repository`() {
        val event = GigEventFixtures.ANY_EVENTS[0]
        every {
            eventRepository.save(event = event)
        } returns event
    }


    private fun `mock the db repository call and returns a list of events`() {
        every {
            eventRepository.findBy(startsAt = GigEventFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
        } returns ANY_EVENTS
    }

    private fun `mock the external provider repository call and returns a list of performance events`() {
        every {
            providerEventRepository.findBy(startsAt = GigEventFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
        } returns ANY_EVENTS
    }

    private fun `mock the external provider repository call and returns a list of gig events`() {
        every {
            providerEventRepository.findBy(startsAt = GigEventFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
        } returns GigEventFixtures.ANY_EVENTS
    }

    companion object {
        val GIG_AND_PERF_SEARCH_EVENT_SERVICE_REQUEST =
            SearchEventServiceRequest(startsAt = GigEventFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
    }
}