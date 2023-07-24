package com.amm.fever.infrastructure.framework.config

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
class ChannelConfig {
    @Bean
    fun customEventChannel() = Channel<String>()
}

@Component
class EventConsumer(private val channel: Channel<String>) {
    init {
        GlobalScope.launch {
            for (message in channel) {
                println("${Thread.currentThread().name}: Received message: $message")
            }
        }
    }
}

@Service
class EventPublisher(private val channel: Channel<String>) {

    fun startPublishing() {
        GlobalScope.launch {
            while (true) {
                println("${Thread.currentThread().name}: Sending message... ")
                channel.send("Hello, this is a custom event!")
                delay(1000) // Publish a message every 1 second
            }
        }
    }
}

@RestController
class Controller(private val publisher: EventPublisher) {
    @GetMapping("/publish")
    fun starts(): Unit {
        publisher.startPublishing()
    }
}
