package com.amm.fever.domain.event

import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.MaxPrice
import com.amm.fever.domain.vo.MinPrice
import com.amm.fever.domain.vo.OrganizerCompanyId
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.SellFrom
import com.amm.fever.domain.vo.SellTo
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.Zones

data class Event(
    val id: Id,
    val eventId: EventId,
    val eventBaseId: EventBaseId,
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