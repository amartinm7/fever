package com.amm.fever.infrastructure.event.controller.search

import com.amm.fever.application.event.SearchEventService
import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_SEARCH_EVENT_SERVICE_REQUEST
import com.amm.fever.event.PerformanceEventFixtures.ANY_SEARCH_EVENT_SERVICE_RESPONSE
import com.amm.fever.event.PerformanceEventFixtures.ANY_START_DATE
import com.amm.fever.event.PerformanceEventFixtures.NO_MATCH_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.NO_MATCH_SEARCH_EVENT_SERVICE_REQUEST
import com.amm.fever.event.PerformanceEventFixtures.NO_MATCH_SEARCH_EVENT_SERVICE_RESPONSE
import com.amm.fever.event.PerformanceEventFixtures.NO_MATCH_START_DATE
import com.amm.fever.infrastructure.SpringbootContractTest
import com.amm.fever.infrastructure.framework.event.controller.search.SearchEventsApi
import com.amm.fever.infrastructure.matchesJson
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus

@ExtendWith(MockKExtension::class)
class SearchEventsApiContractTest : SpringbootContractTest() {

    @MockK
    protected lateinit var searchEventService: SearchEventService

    @InjectMockKs
    protected lateinit var searchEventsApi: SearchEventsApi

    override fun setUpController() = searchEventsApi

    @Test
    fun `should return all active events`() {
        every { searchEventService.execute(ANY_SEARCH_EVENT_SERVICE_REQUEST) } returns ANY_SEARCH_EVENT_SERVICE_RESPONSE

        given()
            .When { get("/search?starts_at=$ANY_START_DATE&ends_at=$ANY_END_DATE") }
            .Then {
                status(HttpStatus.OK)
                body(matchesJson(FETCH_EVENTS_JSON_PATH))
            }
    }

    @Test
    fun `should return none events`() {
        every { searchEventService.execute(NO_MATCH_SEARCH_EVENT_SERVICE_REQUEST) } returns NO_MATCH_SEARCH_EVENT_SERVICE_RESPONSE

        given()
            .When { get("/search?starts_at=$NO_MATCH_START_DATE&ends_at=$NO_MATCH_END_DATE") }
            .Then {
                status(HttpStatus.NOT_FOUND)
                body(matchesJson(EMPTY_EVENTS_JSON_PATH))
            }
    }

    @Test
    fun `should return server error`() {
        every { searchEventService.execute(any()) } throws RuntimeException("Any kind of error")

        given()
            .When { get("/search?starts_at=$NO_MATCH_START_DATE&ends_at=$NO_MATCH_END_DATE") }
            .Then {
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                body(matchesJson(ERROR_JSON_PATH))
            }
    }

    companion object {
        private const val FETCH_EVENTS_JSON_PATH = "/fixtures/events/fetch_events.json"
        private const val EMPTY_EVENTS_JSON_PATH = "/fixtures/events/empty_events.json"
        private const val ERROR_JSON_PATH = "/fixtures/events/error_events.json"
    }
}