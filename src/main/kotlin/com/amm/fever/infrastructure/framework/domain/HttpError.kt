package com.amm.fever.infrastructure.framework.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

sealed class HttpError(
    @JsonProperty("code") val code: String,
    @JsonProperty("message") open val message: String
) {
    object NotFoundError : HttpError(
        code = HttpStatus.NOT_FOUND.value().toString(),
        message = "Events not found"
    )

    object InternalServerError : HttpError(
        code = HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
        message = "System not available"
    )

    class BadRequestError(paramName: String) : HttpError(
        code = HttpStatus.BAD_REQUEST.value().toString(),
        message = "Required parameter '$paramName' is not present"
    )
}


