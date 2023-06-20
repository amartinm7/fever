package com.amm.fever.domain.event

sealed class DomainException(message: String, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}

class EventCommunicationException(msg: String, cause: Throwable) :
    DomainException("Error to call to another external service: $msg", cause)

class EventNotFoundException(msg: String) : DomainException("Event has not been found: $msg")

class EventUnprocessableEntityException(msg: String) : DomainException("Event can't be retrieved: $msg")
