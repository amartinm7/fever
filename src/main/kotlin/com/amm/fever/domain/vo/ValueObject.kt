package com.amm.fever.domain.vo

import java.time.OffsetDateTime
import java.util.UUID


data class Id(val value: UUID)
data class EventId(val value: String)
data class EventBaseId(val value: String)
data class OrganizerCompanyId(val value: String)
data class Title(val value: String)
data class StartAt(val value: OffsetDateTime)
data class EndsAt(val value: OffsetDateTime)
data class SellFrom(val value: OffsetDateTime)
data class SellTo(val value: OffsetDateTime)
data class SoldOut(val value: Boolean)
data class ZoneId(val value: String)
data class Capacity(val value: Long)
data class Price(val value: Double)
data class ZoneName(val value: String)
data class Numbered(val value: Boolean)
data class ZoneVO(
    val id: ZoneId,
    val capacity: Capacity,
    val price: Price,
    val zoneName: ZoneName,
    val numbered: Numbered
)
data class Zones(val value: List<ZoneVO>)
data class MinPrice(val value: Double)
data class MaxPrice(val value: Double)
data class CreatedAt(val value: OffsetDateTime)
data class ModifiedAt(val value: OffsetDateTime)
data class Audit(val modifiedAt: ModifiedAt, val createdAt: CreatedAt)
data class Version(val value: Int)