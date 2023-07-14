package com.amm.fever.domain.event

import java.time.OffsetDateTime

interface ProviderEventRepository: ProviderEventReadRepository

interface ProviderEventReadRepository {
    suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event>
    suspend fun findBy(): List<Event>
}