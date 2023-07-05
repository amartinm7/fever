package com.amm.fever.infrastructure.event.controller.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.application.event.SearchEventService
import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_EVENT_LIST
import com.amm.fever.event.PerformanceEventFixtures.ANY_SEARCH_EVENT_SERVICE_REQUEST
import com.amm.fever.event.PerformanceEventFixtures.ANY_SEARCH_EVENT_SERVICE_RESPONSE
import com.amm.fever.event.PerformanceEventFixtures.ANY_START_DATE
import com.amm.fever.event.PerformanceEventFixtures.EMPTY_SEARCH_EVENT_SERVICE_RESPONSE
import com.amm.fever.infrastructure.framework.domain.HttpError
import com.amm.fever.infrastructure.framework.event.controller.search.SearchApiResponse
import com.amm.fever.infrastructure.framework.event.controller.search.SearchEventsApi
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockKExtension::class)
class SearchEventsApiTest {

    @MockK
    private lateinit var searchEventService: SearchEventService

    @InjectMockKs
    private lateinit var searchEventsApi: SearchEventsApi

    @Test
    fun `should return a valid list of events`() = runBlocking {
        `mock the service call and returns a list of events`()

        val expectedResponse = ResponseEntity.ok(SearchApiResponse(error = null, data = ANY_EVENT_LIST))

        val response: ResponseEntity<SearchApiResponse> =
            searchEventsApi.execute(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE)
        assertThat(response).isEqualTo(expectedResponse)
        verify(exactly = 1) { runBlocking { searchEventService.execute(ANY_SEARCH_EVENT_SERVICE_REQUEST) } }
    }

    @Test
    fun `should return a empty list of events`() = runBlocking {
        `mock the service call and return an empty list of events`()

        val expectedResponse =
            ResponseEntity(SearchApiResponse(error = HttpError.NotFoundError, data = null), HttpStatus.NOT_FOUND)

        val response: ResponseEntity<SearchApiResponse> =
            searchEventsApi.execute(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE)
        assertThat(response).isEqualTo(expectedResponse)
        verify(exactly = 1) { runBlocking { searchEventService.execute(ANY_SEARCH_EVENT_SERVICE_REQUEST) } }
    }

    private fun `mock the service call and returns a list of events`() {
        every {
            runBlocking {
                searchEventService.execute(ANY_SEARCH_EVENT_SERVICE_REQUEST)
            }
        } returns ANY_SEARCH_EVENT_SERVICE_RESPONSE
    }

    private fun `mock the service call and return an empty list of events`() {
        every {
            runBlocking {
                searchEventService.execute(ANY_SEARCH_EVENT_SERVICE_REQUEST)
            }
        } returns EMPTY_SEARCH_EVENT_SERVICE_RESPONSE
    }
}