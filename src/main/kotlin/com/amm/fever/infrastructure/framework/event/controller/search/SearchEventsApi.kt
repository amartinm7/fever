package com.amm.fever.infrastructure.framework.event.controller.search

import com.amm.fever.application.event.SearchEventService
import com.amm.fever.application.event.SearchEventServiceRequest
import com.amm.fever.application.event.SearchEventServiceResponse
import com.amm.fever.domain.event.Event
import com.amm.fever.infrastructure.framework.domain.HttpError
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.UUID

@RestController
class SearchEventsApi(
    private val searchEventService: SearchEventService
) : SearchEventsApiInfo {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(
        path = ["/search"]
    )
    override fun execute(
        @RequestParam(name = "starts_at", required = true)
        startsAt: OffsetDateTime,
        @RequestParam(name = "ends_at", required = true)
        endsAt: OffsetDateTime,
    ): ResponseEntity<SearchApiResponse> =
        try {
            searchEventService.execute(
                SearchEventServiceRequest(startsAt = startsAt, endsAt = endsAt)
            ).toResponseEntity()
        } catch (e: Exception) {
            INTERNAL_SERVER_ERROR_RESPONSE_ENTITY.also {
                logger.error(e.toString())
            }
        }

    private fun SearchEventServiceResponse.toResponseEntity() =
        data.takeUnless { it.isEmpty() }
            ?.toResponseEntityOk()
            ?: NOT_FOUND_RESPONSE_ENTITY

    private fun List<Event>.toEventSummary(): List<EventSummary> =
        map { event ->
            EventSummary(
                id = event.id.value,
                title = event.title.value,
                startDate = event.startsAt.value.toLocalDate(),
                startTime = event.startsAt.value.toLocalTime(),
                endDate = event.endsAt.value.toLocalDate(),
                endTime = event.endsAt.value.toLocalTime(),
                minPrice = event.minPrice.value,
                maxPrice = event.maxPrice.value,
            )
        }

    private fun List<Event>.toResponseEntityOk() =
        ResponseEntity.ok(
            SearchApiResponse(
                error = null,
                data = EventList(events = toEventSummary())
            )
        )

    companion object {
        val NOT_FOUND_RESPONSE_ENTITY =
            ResponseEntity(
                SearchApiResponse(
                    error = HttpError.NotFoundError, data = null
                ), NOT_FOUND
            )
        val INTERNAL_SERVER_ERROR_RESPONSE_ENTITY =
            ResponseEntity(
                SearchApiResponse(
                    error = HttpError.InternalServerError, data = null
                ), INTERNAL_SERVER_ERROR)
    }
}


data class EventSummary(
    @JsonProperty("id") val id: UUID,
    @JsonProperty("title") val title: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("start_date") val startDate: LocalDate,
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("start_time") val startTime: LocalTime,
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("end_date") val endDate: LocalDate,
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("end_time") val endTime: LocalTime,
    @JsonProperty("min_price") val minPrice: Double,
    @JsonProperty("max_price") val maxPrice: Double
)

data class EventList(@JsonProperty("events") val events: List<EventSummary>)

data class SearchApiResponse(
    @JsonProperty("data") val data: EventList?,
    @JsonProperty("error") val error: HttpError?
)


