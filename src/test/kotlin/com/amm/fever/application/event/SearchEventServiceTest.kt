package com.amm.fever.application.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.event.PerformanceEventFixtures.ANY_EVENTS
import com.amm.fever.vo.GigVOFixtures
import com.amm.fever.vo.PerformanceVOFixtures.ANY_END_DATE
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SearchEventServiceTest {

    @MockK
    private lateinit var eventRepository: EventRepository

    @InjectMockKs
    private lateinit var searchEventService: SearchEventService

    @Test
    fun `should return a list of events`() = runBlocking {
        `mock the db repository call and returns a list of events`()

        val expected = ANY_EVENTS
        val response = searchEventService.execute(GIG_AND_PERF_SEARCH_EVENT_SERVICE_REQUEST)

        assertThat(response.data).isEqualTo(expected)
        verify(exactly = 1) {
            runBlocking {
                eventRepository.findBy(
                    startsAt = GigVOFixtures.ANY_START_DATE,
                    endsAt = ANY_END_DATE
                )
            }
        }
    }

    private fun `mock the db repository call and returns a list of events`() {
        every {
            runBlocking {
                eventRepository.findBy(startsAt = GigVOFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
            }
        } returns ANY_EVENTS
    }

    companion object {
        val GIG_AND_PERF_SEARCH_EVENT_SERVICE_REQUEST =
            SearchEventServiceRequest(startsAt = GigVOFixtures.ANY_START_DATE, endsAt = ANY_END_DATE)
    }
}