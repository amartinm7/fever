package com.amm.fever.zone

import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.ModifiedAt
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.Version
import com.amm.fever.domain.vo.ZoneId
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.zone.Zone
import com.amm.fever.infrastructure.framework.zone.repository.jpa.JpaZone
import com.amm.fever.vo.GigVOFixtures.ANY_CAPACITY
import com.amm.fever.vo.GigVOFixtures.ANY_CREATED_AT
import com.amm.fever.vo.GigVOFixtures.ANY_END_DATE
import com.amm.fever.vo.GigVOFixtures.ANY_EVENT_BASE_ID
import com.amm.fever.vo.GigVOFixtures.ANY_EVENT_ID
import com.amm.fever.vo.GigVOFixtures.ANY_MODIFIED_AT
import com.amm.fever.vo.GigVOFixtures.ANY_NUMBERED
import com.amm.fever.vo.GigVOFixtures.ANY_PRICE
import com.amm.fever.vo.GigVOFixtures.ANY_SOLD_OUT
import com.amm.fever.vo.GigVOFixtures.ANY_START_DATE
import com.amm.fever.vo.GigVOFixtures.ANY_TITLE
import com.amm.fever.vo.GigVOFixtures.ANY_UUID
import com.amm.fever.vo.GigVOFixtures.ANY_VERSION
import com.amm.fever.vo.GigVOFixtures.ANY_ZONE_ID
import com.amm.fever.vo.GigVOFixtures.ANY_ZONE_NAME

object GigZoneFixtures {
    val ANY_ZONE = Zone(
        id = Id(ANY_UUID),
        version = Version(ANY_VERSION),
        zoneId = ZoneId(ANY_ZONE_ID),
        zoneName = ZoneName(ANY_ZONE_NAME),
        eventId = EventId(ANY_EVENT_ID),
        eventBaseId = EventBaseId(ANY_EVENT_BASE_ID),
        title = Title(ANY_TITLE),
        startsAt = StartAt(ANY_START_DATE),
        endsAt = EndsAt(ANY_END_DATE),
        soldOut = SoldOut(ANY_SOLD_OUT),
        numbered = Numbered(ANY_NUMBERED),
        capacity = Capacity(ANY_CAPACITY),
        price = Price(ANY_PRICE),
        audit = Audit(
            createdAt = CreatedAt(ANY_CREATED_AT),
            modifiedAt = ModifiedAt(ANY_MODIFIED_AT)
        )
    )
    val ANY_JPA_ZONE = JpaZone(
        id = ANY_UUID,
        version = ANY_VERSION,
        zoneId = ANY_ZONE_ID,
        zoneName = ANY_ZONE_NAME,
        eventId = ANY_EVENT_ID,
        eventBaseId = ANY_EVENT_BASE_ID,
        title = ANY_TITLE,
        startsAt = ANY_START_DATE,
        endsAt = ANY_END_DATE,
        soldOut = ANY_SOLD_OUT,
        numbered = ANY_NUMBERED,
        capacity = ANY_CAPACITY,
        price = ANY_PRICE,
        createdAt = ANY_CREATED_AT,
        modifiedAt = ANY_MODIFIED_AT
    )
}