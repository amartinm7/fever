package com.amm.fever.infrastructure.framework.event.controller.search

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.time.OffsetDateTime


@Tag(name = "search", description = "List the available events on a time range")
interface SearchEventsApiInfo {

    @Operation(
        summary = "List the available events on a time range",
        description = "List the available events on a time range"
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "List of plans",
        ), ApiResponse(
            responseCode = "400",
            description = "The request was not correctly formed (missing required parameters, wrong types...)"
        ), ApiResponse(responseCode = "500", description = "Generic error")]
    )
    suspend fun execute(
        @Parameter(
            name = "starts_at",
            example = "2021-06-17T14:18:29Z",
            `in` = ParameterIn.QUERY,
            required = true,
            description = "Return only events that starts after this date"
        )
        startsAt: OffsetDateTime,
        @Parameter(
            name = "ends_at",
            example = "2021-08-01T14:18:29Z",
            `in` = ParameterIn.QUERY,
            required = true,
            description = "Return only events that finishes before this date"
        )
        endsAt: OffsetDateTime,
    ): ResponseEntity<SearchApiResponse>
}
