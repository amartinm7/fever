package com.amm.fever.vo

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

object GigVOFixtures {
    const val ANY_UUID_STR = "fdcd386f-11a5-448f-b630-52be130c07a0"
    val ANY_UUID = UUID.fromString(ANY_UUID_STR)
    val ANY_VERSION = 1
    const val ANY_EVENT_ID = "291"
    const val ANY_EVENT_BASE_ID = "291"
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

    val ANY_ZONE_ID = "40"
    val ANY_ZONE_NAME = "Platea"
    val ANY_NUMBERED = true
    val ANY_PRICE = 20.0
    val ANY_CAPACITY = 243L

    const val ANY_MIN_PRICE = 15.0
    const val ANY_MAX_PRICE = 30.0
    val ANY_CREATED_AT = OffsetDateTime.parse("2023-06-18T21:00:00Z").truncatedTo(ChronoUnit.SECONDS)
    val ANY_MODIFIED_AT = ANY_CREATED_AT
}