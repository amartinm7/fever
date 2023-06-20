package com.amm.fever.event

import com.amm.fever.application.event.SearchEventServiceRequest
import com.amm.fever.application.event.SearchEventServiceResponse
import com.amm.fever.domain.event.Audit
import com.amm.fever.domain.event.Capacity
import com.amm.fever.domain.event.CreatedAt
import com.amm.fever.domain.event.EndsAt
import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.Id
import com.amm.fever.domain.event.MaxPrice
import com.amm.fever.domain.event.MinPrice
import com.amm.fever.domain.event.ModifiedAt
import com.amm.fever.domain.event.Name
import com.amm.fever.domain.event.Numbered
import com.amm.fever.domain.event.OrganizerCompanyId
import com.amm.fever.domain.event.Price
import com.amm.fever.domain.event.ProviderBaseId
import com.amm.fever.domain.event.ProviderId
import com.amm.fever.domain.event.SellFrom
import com.amm.fever.domain.event.SellTo
import com.amm.fever.domain.event.SoldOut
import com.amm.fever.domain.event.StartAt
import com.amm.fever.domain.event.Title
import com.amm.fever.domain.event.Zone
import com.amm.fever.domain.event.ZoneId
import com.amm.fever.domain.event.Zones
import com.amm.fever.infrastructure.framework.event.controller.search.EventList
import com.amm.fever.infrastructure.framework.event.controller.search.EventSummary
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEvent
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaZone
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaZones
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

object PerformanceEventFixtures {

    const val ANY_UUID_STR = "3deab62e-2fe2-4f30-aede-96484fa3f738"
    val ANY_UUID = UUID.fromString(ANY_UUID_STR)
    const val ANY_PROVIDER_ID = "1642"
    const val ANY_PROVIDER_BASE_ID = "1591"
    const val ANY_ORGANIZER_COMPANY_ID = "1"
    const val ANY_TITLE = "Los Morancos"
    val NO_MATCH_START_DATE = OffsetDateTime.parse("1999-07-31T20:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val NO_MATCH_END_DATE = OffsetDateTime.parse("1999-07-31T21:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_START_DATE = OffsetDateTime.parse("2021-07-31T20:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_END_DATE = OffsetDateTime.parse("2021-07-31T21:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_SELL_FROM = OffsetDateTime.parse("2021-06-26T00:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_SELL_TO = OffsetDateTime.parse("2021-07-31T19:50:00Z").truncatedTo(ChronoUnit.SECONDS)
    const val ANY_SOLD_OUT = false
    val ANY_ZONES =
        """
            {"zones": [
                    {"zone_id":"186", "capacity":"2", "price":"75.00", "name":"Amfiteatre", "numbered":"true"},
                    {"zone_id":"186", "capacity":"16", "price":"65.00", "name":"Amfiteatre", "numbered":"false"}
                ]}
                """.trimMargin()
    const val ANY_MIN_PRICE = 65.0
    const val ANY_MAX_PRICE = 75.0
    val ANY_CREATED_AT = OffsetDateTime.parse("2023-05-05T00:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_MODIFIED_AT = ANY_CREATED_AT
    val ANY_EVENT_LIST = EventList(
        events = listOf(
            EventSummary(
                id = ANY_UUID,
                title = ANY_TITLE,
                startDate = ANY_START_DATE.toLocalDate(),
                endDate = ANY_END_DATE.toLocalDate(),
                startTime = ANY_START_DATE.toLocalTime(),
                endTime = ANY_END_DATE.toLocalTime(),
                minPrice = ANY_MIN_PRICE,
                maxPrice = ANY_MAX_PRICE,
            )
        )
    )

    val ANY_EVENTS = listOf(
        Event(
            id = Id(ANY_UUID),
            providerId = ProviderId(ANY_PROVIDER_ID),
            providerBaseId = ProviderBaseId(ANY_PROVIDER_BASE_ID),
            organizerCompanyId = OrganizerCompanyId(ANY_ORGANIZER_COMPANY_ID),
            title = Title(ANY_TITLE),
            startsAt = StartAt(ANY_START_DATE),
            endsAt = EndsAt(ANY_END_DATE),
            sellFrom = SellFrom(ANY_SELL_FROM),
            sellTo = SellTo(ANY_SELL_TO),
            soldOut = SoldOut(ANY_SOLD_OUT),
            zones = Zones(
                value = listOf(
                    Zone(
                        id = ZoneId("186"),
                        capacity = Capacity("2"),
                        price = Price("75.00"),
                        name = Name("Amfiteatre"),
                        numbered = Numbered("true")
                    ),
                    Zone(
                        id = ZoneId("186"),
                        capacity = Capacity("16"),
                        price = Price("65.00"),
                        name = Name("Amfiteatre"),
                        numbered = Numbered("false")
                    ),
                )
            ),
            minPrice = MinPrice(ANY_MIN_PRICE),
            maxPrice = MaxPrice(ANY_MAX_PRICE),
            audit = Audit(
                createdAt = CreatedAt(ANY_CREATED_AT),
                modifiedAt = ModifiedAt(ANY_MODIFIED_AT)
            )
        )
    )
    val NO_MATCH_SEARCH_EVENT_SERVICE_REQUEST = SearchEventServiceRequest(startsAt = NO_MATCH_START_DATE, endsAt = NO_MATCH_END_DATE)
    val NO_MATCH_SEARCH_EVENT_SERVICE_RESPONSE = SearchEventServiceResponse(data = emptyList())
    val ANY_SEARCH_EVENT_SERVICE_REQUEST = SearchEventServiceRequest(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE)
    val ANY_SEARCH_EVENT_SERVICE_RESPONSE = SearchEventServiceResponse(data = ANY_EVENTS)
    val EMPTY_SEARCH_EVENT_SERVICE_RESPONSE = SearchEventServiceResponse(data = emptyList())

    val ANY_JPA_EVENTS = listOf<JpaEvent>(
        JpaEvent(
            id = ANY_UUID,
            providerId = ANY_PROVIDER_ID,
            providerBaseId = ANY_PROVIDER_BASE_ID,
            organizerCompanyId = ANY_ORGANIZER_COMPANY_ID,
            title = ANY_TITLE,
            startsAt = ANY_START_DATE,
            endsAt = ANY_END_DATE,
            sellFrom = ANY_SELL_FROM,
            sellTo = ANY_SELL_TO,
            soldOut = ANY_SOLD_OUT,
            zones = ANY_ZONES,
            minPrice = ANY_MIN_PRICE,
            maxPrice = ANY_MAX_PRICE,
            createdAt = ANY_CREATED_AT,
            modifiedAt = ANY_MODIFIED_AT
        )
    )

    val ANY_JPA_ZONES = JpaZones(
        zones = listOf(
            JpaZone(
                id = "186",
                capacity = "2",
                price = "75.00",
                name = "Amfiteatre",
                numbered = "true"
            ),
            JpaZone(
                id = "186",
                capacity = "16",
                price = "65.00",
                name = "Amfiteatre",
                numbered = "false"
            ),
        )
    )
}