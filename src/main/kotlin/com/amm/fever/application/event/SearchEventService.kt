package com.amm.fever.application.event

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.OffsetDateTime

class SearchEventService(
    private val eventRepository: EventRepository,
    private val providerEventRepository: ProviderEventRepository
) {

    fun execute(request: SearchEventServiceRequest): SearchEventServiceResponse {
        val providerEventFlux = Flux.defer {
            Flux.fromIterable(findEventsFromExtProviderBy(request.startsAt, request.endsAt))
        }

        val eventFlux = Flux.defer {
            Flux.fromIterable(eventRepository.findBy(request.startsAt, request.endsAt))
        }

        val asyncRequests = Mono.zip(providerEventFlux.collectList(), eventFlux.collectList())

        return asyncRequests.flatMap { tuple ->
            Mono.just(SearchEventServiceResponse(tuple.t1 merge tuple.t2))
        }.block()!!
    }

    private fun findEventsFromExtProviderBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event> =
        providerEventRepository.findBy(startsAt = startsAt, endsAt = endsAt)
            .emitCommandEvent()
            .filterByDates(startsAt = startsAt, endsAt = endsAt)


    // creates a map with key providerId and value the event for every List<event>
    // after that, merges the two maps replacing or adding new entries over the first map
    private infix fun List<Event>.merge(events: List<Event>): List<Event> =
        events.associateBy { event -> event.providerId.value }.plus(
            this.associateBy { event -> event.providerId.value }
        ).values.toList()

    private fun List<Event>.emitCommandEvent(): List<Event> {
        val source = Flux.create<Event> { emitter ->
            // println(">>> ${Thread.currentThread().name}")
            forEach { event -> emitter.next(event) }
            emitter.complete()
        }
        // execute the save in parallel threads
        source.flatMap { item: Event ->
            Flux.defer { Flux.just(eventRepository.save(item)) }
                .subscribeOn(Schedulers.parallel())
        }.subscribe()
        return this
    }

    private fun List<Event>.filterByDates(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event> =
        filter { event -> startsAt.isBeforeOrEquals(event.startsAt.value) and endsAt.isAfterOrEquals(event.endsAt.value) }

    private infix fun OffsetDateTime.isBeforeOrEquals(other: OffsetDateTime) = isBefore(other) || isEqual(other)

    private infix fun OffsetDateTime.isAfterOrEquals(other: OffsetDateTime) = isAfter(other) || isEqual(other)
}

data class SearchEventServiceResponse(val data: List<Event>)

data class SearchEventServiceRequest(val startsAt: OffsetDateTime, val endsAt: OffsetDateTime)
