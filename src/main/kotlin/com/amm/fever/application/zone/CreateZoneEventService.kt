package com.amm.fever.application.zone

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.Version
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreateZoneEventService(
    private val zoneRepository: ZoneEventEventRepository,
    private val providerEventRepository: ProviderEventRepository,
) {
    suspend fun execute(request: CreateZoneEventServiceRequest): Unit {
        findEventsFromExtProviderBy().emitCommandEvent()
    }

    private suspend fun findEventsFromExtProviderBy(): List<Event> = providerEventRepository.findBy()

    private suspend fun List<Event>.emitCommandEvent(): Unit {
        for (event in this) {
            event.emitCommandEvent()
        }
    }

    private suspend fun Event.emitCommandEvent(): Unit {
        with(zones.value) {
            for (zoneVO in this) {
                coroutineScope {
                    launch(Dispatchers.IO) {
                        // println(">>> ${Thread.currentThread().name}")
                        zoneRepository.save(
                            zone = Zone(
                                id = Id(UUID.randomUUID()),
                                version = Version(1),
                                zoneId = zoneVO.id,
                                zoneName = zoneVO.zoneName,
                                eventId = this@emitCommandEvent.eventId,
                                eventBaseId = this@emitCommandEvent.eventBaseId,
                                title = this@emitCommandEvent.title,
                                startsAt = this@emitCommandEvent.startsAt,
                                endsAt = this@emitCommandEvent.endsAt,
                                soldOut = this@emitCommandEvent.soldOut,
                                numbered = zoneVO.numbered,
                                capacity = zoneVO.capacity,
                                price = zoneVO.price,
                                audit = this@emitCommandEvent.audit
                            )
                        )
                    }
                }
            }
        }
    }
}

data class CreateZoneEventServiceRequest(val id: UUID)
