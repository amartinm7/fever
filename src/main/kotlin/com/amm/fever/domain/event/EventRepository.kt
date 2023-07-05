package com.amm.fever.domain.event

import java.time.OffsetDateTime

interface EventRepository : EventWriteRepository, EventReadRepository

interface EventWriteRepository {
    fun save(event: Event): Event
}

interface EventReadRepository {
    suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event>
}