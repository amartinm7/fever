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
import com.amm.fever.vo.GigVOFixtures.ANY_CAPACITY
import com.amm.fever.vo.GigVOFixtures.ANY_CREATED_AT
import com.amm.fever.vo.GigVOFixtures.ANY_END_DATE
import com.amm.fever.vo.GigVOFixtures.ANY_MAX_PRICE
import com.amm.fever.vo.GigVOFixtures.ANY_MIN_PRICE
import com.amm.fever.vo.GigVOFixtures.ANY_MODIFIED_AT
import com.amm.fever.vo.GigVOFixtures.ANY_NUMBERED
import com.amm.fever.vo.GigVOFixtures.ANY_PRICE
import com.amm.fever.vo.GigVOFixtures.ANY_EVENT_BASE_ID
import com.amm.fever.vo.GigVOFixtures.ANY_EVENT_ID
import com.amm.fever.vo.GigVOFixtures.ANY_SELL_FROM
import com.amm.fever.vo.GigVOFixtures.ANY_SELL_TO
import com.amm.fever.vo.GigVOFixtures.ANY_SOLD_OUT
import com.amm.fever.vo.GigVOFixtures.ANY_START_DATE
import com.amm.fever.vo.GigVOFixtures.ANY_TITLE
import com.amm.fever.vo.GigVOFixtures.ANY_UUID
import com.amm.fever.vo.GigVOFixtures.ANY_ZONE_ID
import com.amm.fever.vo.GigVOFixtures.ANY_ZONE_NAME

object GigEventFixtures {
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
                        id = ZoneId(ANY_ZONE_ID),
                        capacity = Capacity(ANY_CAPACITY),
                        price = Price(ANY_PRICE),
                        zoneName = ZoneName(ANY_ZONE_NAME),
                        numbered = Numbered(ANY_NUMBERED)
                    ),
                    Zone(
                        id = ZoneId("38"),
                        capacity = Capacity(100L),
                        price = Price(15.00),
                        zoneName = ZoneName("Grada 2"),
                        numbered = Numbered(false)
                    ),
                    Zone(
                        id = ZoneId("30"),
                        capacity = Capacity(90L),
                        price = Price(30.00),
                        zoneName = ZoneName("A28"),
                        numbered = Numbered(true)
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