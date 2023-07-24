package com.amm.fever.domain.zone

import java.time.OffsetDateTime

interface ZoneEventEventRepository : ZoneEventWriteRepository, ZoneEventReadRepository

interface ZoneEventWriteRepository {
    fun save(zone: Zone): Zone
}

interface ZoneEventReadRepository {
    suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Zone>
}