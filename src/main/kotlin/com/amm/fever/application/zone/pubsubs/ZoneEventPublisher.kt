package com.amm.fever.application.zone.pubsubs

import com.amm.fever.domain.zone.ZoneWasSavedEvent
import kotlinx.coroutines.channels.Channel

class ZoneEventPublisher(
    private val zoneEventChannel: Channel<ZoneWasSavedEvent>
) {
    suspend fun publish(zone: ZoneWasSavedEvent) = zoneEventChannel.send(zone)
}