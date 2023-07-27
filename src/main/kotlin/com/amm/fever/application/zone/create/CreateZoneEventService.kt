package com.amm.fever.application.zone.create

import com.amm.fever.application.zone.pubsubs.ZoneEventPublisher
import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.vo.Id
import com.amm.fever.domain.vo.Version
import com.amm.fever.domain.vo.ZoneVO
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import com.amm.fever.domain.zone.ZoneWasSavedEvent
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreateZoneEventService(
    private val zoneRepository: ZoneEventEventRepository,
    private val providerEventRepository: ProviderEventRepository,
    private val zoneEventPublisher: ZoneEventPublisher
) {
    suspend fun execute(request: CreateZoneEventServiceRequest): Unit {
        findEventsFromExtProviderBy().saveZones()
    }

    private suspend fun findEventsFromExtProviderBy(): List<Event> = providerEventRepository.findBy()

    private suspend fun List<Event>.saveZones(): Unit {
        for (event in this) {
            event.saveZones()
        }
    }

    private suspend fun Event.saveZones(): Unit {
        with(zones.value) {
            for (zoneVO in this) {
                coroutineScope {
                    launch(Dispatchers.IO) {
                        // println(">>> ${Thread.currentThread().name}")
                        zoneRepository
                            .save(zone = zoneVO.toZone(this@saveZones))
                            .emitEvent()
                    }
                }
            }
        }
    }

    private suspend fun Zone.emitEvent() =
        zoneEventPublisher.publish(ZoneWasSavedEvent(zone = this))

    private fun ZoneVO.toZone(event: Event) =
        Zone(
            id = Id(UUID.randomUUID()),
            version = Version(1),
            zoneId = id,
            zoneName = zoneName,
            eventId = event.eventId,
            eventBaseId = event.eventBaseId,
            title = event.title,
            startsAt = event.startsAt,
            endsAt = event.endsAt,
            soldOut = event.soldOut,
            numbered = numbered,
            capacity = capacity,
            price = price,
            audit = event.audit
        )
}

data class CreateZoneEventServiceRequest(val id: UUID)
