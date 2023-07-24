package com.amm.fever.infrastructure.framework.schedule.externalprovider

import com.amm.fever.application.event.CreateEventService
import com.amm.fever.application.event.CreateEventServiceRequest
import kotlinx.coroutines.runBlocking
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EventSchedule(
    private val createEventService: CreateEventService,
    @Value("\${app.scheduler.externalprovider.enabled}")
    private var isEnabledScheduler: Boolean = false
) {

    private val LOGGER = LoggerFactory.getLogger(EventSchedule::class.java)

    @Scheduled(cron = "\${app.scheduler.externalprovider.scheduled}")
    @SchedulerLock(
        name = "create_event_service",
        lockAtLeastFor = "\${app.scheduler.externalprovider.shedlock.lockAtLeast}",
        lockAtMostFor = "\${app.scheduler.externalprovider.shedlock.lockAtMost}",
    )
    fun execute() {
        runBlocking {
            try {
                LOGGER.info(">>> Starting cron EventSchedule")
                if (isEnabledScheduler) {
                    createEventService.execute(CreateEventServiceRequest(""))
                }
                LOGGER.info(">>> Ended cron EventSchedule")
            } catch (exception: Exception) {
                LOGGER.error(">>> Error executing cron EventSchedule ${exception.message}", exception)
            }
        }
    }
}