package com.amm.fever.application.event

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.ProviderEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreateEventService(
    private val eventRepository: EventRepository,
    private val providerEventRepository: ProviderEventRepository,
) {
    suspend fun execute(request: CreateEventServiceRequest): CreateEventServiceResponse {
        findEventsFromExtProviderBy().emitCommandEvent()
        return CreateEventServiceResponse("")
    }

    private suspend fun findEventsFromExtProviderBy(): List<Event> = providerEventRepository.findBy()

    private suspend fun List<Event>.emitCommandEvent(): List<Event> {
        for (event in this) {
            coroutineScope {
                launch(Dispatchers.IO) {
                    // println(">>> ${Thread.currentThread().name}")
                    eventRepository.save(event)
                }
            }
        }
        return this
    }
}

data class CreateEventServiceResponse(val i: String)

data class CreateEventServiceRequest(val i: String)