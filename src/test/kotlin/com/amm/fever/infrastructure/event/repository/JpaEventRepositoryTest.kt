package com.amm.fever.infrastructure.event.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.domain.event.Event
import com.amm.fever.event.PerformanceEventFixtures.ANY_EVENTS
import com.amm.fever.event.PerformanceEventFixtures.ANY_JPA_EVENTS
import com.amm.fever.event.PerformanceEventFixtures.ANY_JPA_ZONES
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEventRepository
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEventRepositoryClient
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaZones
import com.amm.fever.vo.PerformanceVOFixtures.ANY_END_DATE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_EVENT_BASE_ID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_EVENT_ID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_START_DATE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_ZONES
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpaEventRepositoryTest {

    @MockK
    private lateinit var objectMapper: ObjectMapper

    @MockK
    private lateinit var jpaEventRepositoryClient: JpaEventRepositoryClient

    @InjectMockKs
    private lateinit var jpaEventRepository: JpaEventRepository

    @Test
    suspend fun `should return an event list given an start and end dates`() {
        //given
        `mock the findBy and returns a list of performance events`()
        `mock the objectMapper call and returns a JpaZones`()
        //when
        val response: List<Event> = jpaEventRepository.findBy(
            startsAt = ANY_START_DATE,
            endsAt = ANY_END_DATE,
        )
        //then
        assertThat(response).isEqualTo(expected = ANY_EVENTS)
        verify(exactly = 1) { jpaEventRepositoryClient.findBy(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE) }
        verify(exactly = 1) { objectMapper.readValue(ANY_ZONES, JpaZones::class.java) }
    }

    @Test
    fun `should save an event`() {
        //given
        `mock the findByIds and returns a jpaEvent of perfomance events`()
        `mock the save method`()
        `mock the objectMapper writeValueAsString call and returns a zones json`()
        //when
        val response: Event = jpaEventRepository.save(event = ANY_EVENTS[0])
        //then
        verify(exactly = 1) {
            jpaEventRepositoryClient.findByIds(
                eventId = ANY_EVENT_ID,
                eventBaseId = ANY_EVENT_BASE_ID
            )
        }
        verify(exactly = 1) { jpaEventRepositoryClient.save(ANY_JPA_EVENTS[0]) }
        verify(exactly = 1) { objectMapper.writeValueAsString(ANY_JPA_ZONES) }
    }

    private fun `mock the findByIds and returns a jpaEvent of perfomance events`() {
        every {
            jpaEventRepositoryClient.findByIds(eventId = ANY_EVENT_ID, eventBaseId = ANY_EVENT_BASE_ID)
        } returns ANY_JPA_EVENTS[0]
    }

    private fun `mock the save method`() {
        every {
            jpaEventRepositoryClient.save(ANY_JPA_EVENTS[0])
        } returns ANY_JPA_EVENTS[0]
    }

    private fun `mock the objectMapper writeValueAsString call and returns a zones json`() {
        every {
            objectMapper.writeValueAsString(ANY_JPA_ZONES)
        } returns ANY_ZONES
    }

    private fun `mock the findBy and returns a list of performance events`() {
        every {
            jpaEventRepositoryClient.findBy(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE)
        } returns ANY_JPA_EVENTS
    }

    private fun `mock the objectMapper call and returns a JpaZones`() {
        every {
            objectMapper.readValue(ANY_ZONES, JpaZones::class.java)
        } returns ANY_JPA_ZONES
    }
}

