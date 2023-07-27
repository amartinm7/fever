package com.amm.fever.application.zone.pubsubs

import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import com.amm.fever.domain.zone.ZoneWasSavedEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class ZoneEventSubscriber(
    private val zoneRepository: ZoneEventEventRepository,
    private val zoneEventChannel: Channel<ZoneWasSavedEvent>
) {
    init {
        GlobalScope.launch {
            receive()
        }
    }
    private suspend fun receive() {
        for (zoneWasSavedEvent in zoneEventChannel) {
            println("${Thread.currentThread().name}: Received message: $zoneWasSavedEvent")
        }
    }
}
