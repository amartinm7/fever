package com.amm.fever.infrastructure.framework.config

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObservedAspectConfiguration {
    @Bean
    fun observedAspect(observationRegistry: ObservationRegistry): ObservedAspect? {
        observationRegistry.observationConfig().observationHandler(SimpleLoggingHandler())
        return ObservedAspect(observationRegistry)
    }
}

class SimpleLoggingHandler : ObservationHandler<Observation.Context> {

    private companion object {
        val log: Logger = LoggerFactory.getLogger(SimpleLoggingHandler::class.java)
    }

    override fun supportsContext(context: Observation.Context): Boolean = true

    override fun onStart(context: Observation.Context) {
        log.info("Starting context {} ", context)
    }

    override fun onError(context: Observation.Context) {
        log.info("Error for context {} ", context)
    }

    override fun onEvent(event: Observation.Event, context: Observation.Context) {
        log.info("Event for context {} and event [ {} ]", context, event)
    }

    override fun onScopeOpened(context: Observation.Context) {
        log.info("Scope opened for context {} ", context)
    }

    override fun onScopeClosed(context: Observation.Context) {
        log.info("Scope closed for context {}", context)
    }

    override fun onStop(context: Observation.Context) {
        log.info("Stopping context {} ", context)
    }
}