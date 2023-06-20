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
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

object GigEventFixtures {

    const val ANY_UUID_STR = "fdcd386f-11a5-448f-b630-52be130c07a0"
    val ANY_UUID = UUID.fromString(ANY_UUID_STR)
    const val ANY_PROVIDER_ID = "291"
    const val ANY_PROVIDER_BASE_ID = "291"
    const val ANY_TITLE = "Camela en concierto"
    val ANY_START_DATE = OffsetDateTime.parse("2021-06-30T21:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_END_DATE = OffsetDateTime.parse("2021-06-30T22:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_SELL_FROM = OffsetDateTime.parse("2020-07-01T00:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_SELL_TO = OffsetDateTime.parse("2021-06-30T20:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    const val ANY_SOLD_OUT = false
    val ANY_ZONES_JSON =
        """
            {"zones": 
            [
                    {"zone_id":"40", "capacity":"243", "price":"20.00", "name":"Platea", "numbered":"true"},
                    {"zone_id":"38", "capacity":"100", "price":"15.00", "name":"Grada 2", "numbered":"false"},
                    {"zone_id":"30", "capacity":"90", "price":"30.00", "name":"A28", "numbered":"true"}
                ]}
                """.trimMargin()
    const val ANY_MIN_PRICE = 15.0
    const val ANY_MAX_PRICE = 30.0
    val ANY_CREATED_AT = OffsetDateTime.parse("2023-06-18T21:00:00Z").truncatedTo(ChronoUnit.SECONDS)
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
            organizerCompanyId = null,
            title = Title(ANY_TITLE),
            startsAt = StartAt(ANY_START_DATE),
            endsAt = EndsAt(ANY_END_DATE),
            sellFrom = SellFrom(ANY_SELL_FROM),
            sellTo = SellTo(ANY_SELL_TO),
            soldOut = SoldOut(ANY_SOLD_OUT),
            zones = Zones(
                value = listOf(
                    Zone(
                        id = ZoneId("40"),
                        capacity = Capacity("243"),
                        price = Price("20.00"),
                        name = Name("Platea"),
                        numbered = Numbered("true")
                    ),
                    Zone(
                        id = ZoneId("38"),
                        capacity = Capacity("100"),
                        price = Price("15.00"),
                        name = Name("Grada 2"),
                        numbered = Numbered("false")
                    ),
                    Zone(
                        id = ZoneId("30"),
                        capacity = Capacity("90"),
                        price = Price("30.00"),
                        name = Name("A28"),
                        numbered = Numbered("true")
                    )

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
    val ANY_SEARCH_EVENT_SERVICE_REQUEST = SearchEventServiceRequest(startsAt = ANY_START_DATE, endsAt = ANY_END_DATE)
    val ANY_SEARCH_EVENT_SERVICE_RESPONSE = SearchEventServiceResponse(data = ANY_EVENTS)
    val EMPTY_SEARCH_EVENT_SERVICE_RESPONSE = SearchEventServiceResponse(data = emptyList())
}