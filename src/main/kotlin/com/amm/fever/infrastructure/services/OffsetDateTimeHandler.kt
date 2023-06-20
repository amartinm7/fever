package com.amm.fever.infrastructure.services

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Date

class OffsetDateTimeHandler {
  fun now(): OffsetDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)

  fun from(date: Date): OffsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC)
}

fun String.toOffsetDateTime() =
  LocalDateTime.parse(this).toInstant(ZoneOffset.UTC).atOffset(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS)
