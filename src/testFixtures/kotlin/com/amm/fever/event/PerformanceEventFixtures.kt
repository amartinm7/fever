package com.amm.fever.event

import com.amm.fever.application.event.SearchEventServiceRequest
import com.amm.fever.application.event.SearchEventServiceResponse
import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.event.Event
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.MaxPrice
import com.amm.fever.domain.vo.MinPrice
import com.amm.fever.domain.vo.ModifiedAt
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.OrganizerCompanyId
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.SellFrom
import com.amm.fever.domain.vo.SellTo
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.Zone
import com.amm.fever.domain.vo.ZoneId
import com.amm.fever.domain.vo.Zones
import com.amm.fever.infrastructure.framework.event.controller.search.EventList
import com.amm.fever.infrastructure.framework.event.controller.search.EventSummary
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaEvent
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaZone
import com.amm.fever.infrastructure.framework.event.repository.jpa.JpaZones
import com.amm.fever.vo.PerformanceVOFixtures.ANY_CREATED_AT
import com.amm.fever.vo.PerformanceVOFixtures.ANY_END_DATE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_MAX_PRICE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_MIN_PRICE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_MODIFIED_AT
import com.amm.fever.vo.PerformanceVOFixtures.ANY_ORGANIZER_COMPANY_ID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_EVENT_BASE_ID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_EVENT_ID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_SELL_FROM
import com.amm.fever.vo.PerformanceVOFixtures.ANY_SELL_TO
import com.amm.fever.vo.PerformanceVOFixtures.ANY_SOLD_OUT
import com.amm.fever.vo.PerformanceVOFixtures.ANY_START_DATE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_TITLE
import com.amm.fever.vo.PerformanceVOFixtures.ANY_UUID
import com.amm.fever.vo.PerformanceVOFixtures.ANY_ZONES
import com.amm.fever.vo.PerformanceVOFixtures.NO_MATCH_END_DATE
import com.amm.fever.vo.PerformanceVOFixtures.NO_MATCH_START_DATE

object PerformanceEventFixtures {
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
            eventId = EventId(ANY_EVENT_ID),
            eventBaseId = EventBaseId(ANY_EVENT_BASE_ID),
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
                        capacity = Capacity(2L),
                        price = Price(75.00),
                        zoneName = ZoneName("Amfiteatre"),
                        numbered = Numbered(true)
                    ),
                    Zone(
                        id = ZoneId("186"),
                        capacity = Capacity(16L),
                        price = Price(65.00),
                        zoneName = ZoneName("Amfiteatre"),
                        numbered = Numbered(false)
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
            eventId = ANY_EVENT_ID,
            eventBaseId = ANY_EVENT_BASE_ID,
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
                capacity = 2L,
                price = 75.00,
                name = "Amfiteatre",
                numbered = true
            ),
            JpaZone(
                id = "186",
                capacity = 16L,
                price = 65.00,
                name = "Amfiteatre",
                numbered = false
            ),
        )
    )
}