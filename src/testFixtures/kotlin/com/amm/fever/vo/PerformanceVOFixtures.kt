package com.amm.fever.vo

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

object PerformanceVOFixtures {
    const val ANY_UUID_STR = "3deab62e-2fe2-4f30-aede-96484fa3f738"
    val ANY_UUID = UUID.fromString(ANY_UUID_STR)
    const val ANY_EVENT_ID = "1642"
    const val ANY_EVENT_BASE_ID = "1591"
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
}