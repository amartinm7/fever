package com.amm.fever.infrastructure.event

import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_EVENT_LIST
import com.amm.fever.event.PerformanceEventFixtures.ANY_START_DATE
import com.amm.fever.infrastructure.SpringbootAcceptanceTest
import com.amm.fever.infrastructure.framework.event.controller.search.SearchApiResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException

class SearchEventsAcceptanceTest : SpringbootAcceptanceTest() {

    @BeforeEach
    fun setup() {
        eventDatabaseSupport.deleteEvent()
        eventDatabaseSupport.createEvent()
    }

    @Test
    fun `should return a list of events in the same date`() {
        val expected = SearchApiResponse(error = null, data = ANY_EVENT_LIST)
        val response: ResponseEntity<SearchApiResponse> =
            restTemplate.getForEntity(
                "http://localhost:$port/search?starts_at=$ANY_START_DATE&ends_at=$ANY_END_DATE",
                SearchApiResponse::class.java
            )
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.data!!.events.size).isEqualTo(expected.data!!.events.size)
        Assertions.assertThat(response.body!!.data!!.events[0].title).isEqualTo(expected.data!!.events[0].title)
        Assertions.assertThat(response.body!!.data!!.events[0].startDate).isEqualTo(expected.data!!.events[0].startDate)
        Assertions.assertThat(response.body!!.data!!.events[0].endDate).isEqualTo(expected.data!!.events[0].endDate)
        Assertions.assertThat(response.body!!.data!!.events[0].startTime).isEqualTo(expected.data!!.events[0].startTime)
        Assertions.assertThat(response.body!!.data!!.events[0].startDate).isEqualTo(expected.data!!.events[0].startDate)
        Assertions.assertThat(response.body!!.data!!.events[0].minPrice).isEqualTo(expected.data!!.events[0].minPrice)
        Assertions.assertThat(response.body!!.data!!.events[0].maxPrice).isEqualTo(expected.data!!.events[0].maxPrice)
    }

    @Test
    fun `should return a list of events between the dates`() {
        val expected = SearchApiResponse(error = null, data = ANY_EVENT_LIST)
        val response: ResponseEntity<SearchApiResponse> =
            restTemplate.getForEntity(
                "http://localhost:$port/search?starts_at=${ANY_START_DATE_MINUS_DAY}&ends_at=${ANY_END_DATE_PLUS_DAY}",
                SearchApiResponse::class.java
            )
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.data!!.events.size).isEqualTo(expected.data!!.events.size)
        Assertions.assertThat(response.body!!.data!!.events[0].title).isEqualTo(expected.data!!.events[0].title)
        Assertions.assertThat(response.body!!.data!!.events[0].startDate).isEqualTo(expected.data!!.events[0].startDate)
        Assertions.assertThat(response.body!!.data!!.events[0].endDate).isEqualTo(expected.data!!.events[0].endDate)
        Assertions.assertThat(response.body!!.data!!.events[0].startTime).isEqualTo(expected.data!!.events[0].startTime)
        Assertions.assertThat(response.body!!.data!!.events[0].startDate).isEqualTo(expected.data!!.events[0].startDate)
        Assertions.assertThat(response.body!!.data!!.events[0].minPrice).isEqualTo(expected.data!!.events[0].minPrice)
        Assertions.assertThat(response.body!!.data!!.events[0].maxPrice).isEqualTo(expected.data!!.events[0].maxPrice)
    }

    @Test
    fun `should return none events`() {
        try {
            restTemplate.getForEntity(
                "http://localhost:$port/search?starts_at=${ANY_START_DATE_PLUS_DAY}&ends_at=${ANY_END_DATE}",
                SearchApiResponse::class.java
            )
        } catch (e: HttpClientErrorException) {
            Assertions.assertThat(e.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
            Assertions.assertThat(e.message).isEqualTo(EXPECTED_MESSAGE_404)
        }
    }

    companion object {
        val EXPECTED_MESSAGE_404 =
            "404 : \"{\"data\":null,\"error\":{\"code\":\"404\",\"message\":\"Events not found\"}}\""
        val ANY_START_DATE_PLUS_DAY = ANY_START_DATE.plusDays(1)
        val ANY_START_DATE_MINUS_DAY = ANY_START_DATE.minusDays(1)
        val ANY_END_DATE_PLUS_DAY = ANY_END_DATE.plusDays(1)
    }
}


