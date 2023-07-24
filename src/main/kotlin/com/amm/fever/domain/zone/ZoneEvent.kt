package com.amm.fever.domain.zone

import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.Version
import com.amm.fever.domain.vo.ZoneId

data class Zone(
    val id: Id,
    val version: Version,
    val zoneId: ZoneId,
    val zoneName: ZoneName,
    val eventId: EventId,
    val eventBaseId: EventBaseId,
    val title: Title,
    val startsAt: StartAt,
    val endsAt: EndsAt,
    val soldOut: SoldOut,
    val numbered: Numbered,
    val capacity: Capacity,
    val price: Price,
    val audit: Audit
)
