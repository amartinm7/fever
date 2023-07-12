package com.amm.fever.infrastructure.framework.event.controller.search

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
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
            content = [Content(schema = Schema(example = EXAMPLE_200_OK))]
        ), ApiResponse(
            responseCode = "400",
            description = "The request was not correctly formed (missing required parameters, wrong types...)",
            content = [Content(schema = Schema(example = EXAMPLE_400_BAD_REQUEST))]
        ), ApiResponse(
            responseCode = "404",
            description = "Generic error",
            content = [Content(schema = Schema(example = EXAMPLE_404_NOT_FOUND))]
        ), ApiResponse(
            responseCode = "500",
            description = "Generic error",
            content = [Content(schema = Schema(example = EXAMPLE_500_INTERNAL_SERVER_ERROR))]
        )]
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

private const val EXAMPLE_200_OK =
    "{\"data\":{\"events\":[{\"id\":\"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\"title\":\"string\",\"start_date\":\"2023-07-12\",\"start_time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0},\"end_date\":\"2023-07-12\",\"end_time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0},\"min_price\":0,\"max_price\":0}]},\"error\": null}"
private const val EXAMPLE_400_BAD_REQUEST = "{\"data\":null,\"error\":{\"code\":\"400\",\"message\":\"Bad request\"}}"
private const val EXAMPLE_404_NOT_FOUND = "{\"data\":null,\"error\":{\"code\":\"404\",\"message\":\"Not Found\"}}"
private const val EXAMPLE_500_INTERNAL_SERVER_ERROR =
    "{\"data\":null,\"error\":{\"code\":\"500\",\"message\":\"Internal server error\"}}"