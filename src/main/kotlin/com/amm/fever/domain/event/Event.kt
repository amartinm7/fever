package com.amm.fever.domain.event

import java.time.OffsetDateTime
import java.util.UUID

data class Event(
    val id: Id,
    val providerId: ProviderId,
    val providerBaseId: ProviderBaseId,
    val organizerCompanyId: OrganizerCompanyId?,
    val title: Title,
    val startsAt: StartAt,
    val endsAt: EndsAt,
    val sellFrom: SellFrom,
    val sellTo: SellTo,
    val soldOut: SoldOut,
    val zones: Zones,
    val minPrice: MinPrice,
    val maxPrice: MaxPrice,
    val audit: Audit
)

data class Id(val value: UUID)
data class ProviderId(val value: String)
data class ProviderBaseId(val value: String)
data class OrganizerCompanyId(val value: String)
data class Title(val value: String)
data class StartAt(val value: OffsetDateTime)
data class EndsAt(val value: OffsetDateTime)
data class SellFrom(val value: OffsetDateTime)
data class SellTo(val value: OffsetDateTime)
data class SoldOut(val value: Boolean)
data class ZoneId(val value: String)
data class Capacity(val value: String)
data class Price(val value: String)
data class Name(val value: String)
data class Numbered(val value: String)
data class Zone(
    val id: ZoneId,
    val capacity: Capacity,
    val price: Price,
    val name: Name,
    val numbered: Numbered
)
data class Zones(val value: List<Zone>)
data class MinPrice(val value: Double)
data class MaxPrice(val value: Double)
data class CreatedAt(val value: OffsetDateTime)
data class ModifiedAt(val value: OffsetDateTime)
data class Audit(val modifiedAt: ModifiedAt, val createdAt: CreatedAt)
