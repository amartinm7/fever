package com.amm.fever.application.event

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class SearchEventService(
    private val eventRepository: EventRepository
) {

    suspend fun execute(request: SearchEventServiceRequest): SearchEventServiceResponse {
        return coroutineScope {
            val events: Deferred<List<Event>> = async {
                eventRepository.findBy(request.startsAt, request.endsAt)
            }
            return@coroutineScope SearchEventServiceResponse(events.await())
        }
    }
}

data class SearchEventServiceResponse(val data: List<Event>)

data class SearchEventServiceRequest(val startsAt: OffsetDateTime, val endsAt: OffsetDateTime)
