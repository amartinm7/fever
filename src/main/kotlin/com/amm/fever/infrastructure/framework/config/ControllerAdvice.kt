package com.amm.fever.infrastructure.framework.config

import com.amm.fever.infrastructure.framework.domain.HttpError
import com.amm.fever.infrastructure.framework.event.controller.search.SearchApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDomainException(ex: MissingServletRequestParameterException) =
        ResponseEntity(
            SearchApiResponse(
                error = HttpError.BadRequestError(ex.parameterName), data = null
            ), HttpStatus.BAD_REQUEST
        )
}

