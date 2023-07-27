package com.amm.fever.infrastructure.framework.schedule.externalprovider

import com.amm.fever.application.zone.create.CreateZoneEventService
import com.amm.fever.application.zone.create.CreateZoneEventServiceRequest
import java.util.UUID
import kotlinx.coroutines.runBlocking
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ZoneEventSchedule(
    private val createZoneEventService: CreateZoneEventService,
    @Value("\${app.scheduler.externalprovider.enabled}")
    private var isEnabledScheduler: Boolean = false
) {

    private val LOGGER = LoggerFactory.getLogger(ZoneEventSchedule::class.java)

    @Scheduled(cron = "\${app.scheduler.externalprovider.scheduled}")
    @SchedulerLock(
        name = "create_zone_service",
        lockAtLeastFor = "\${app.scheduler.externalprovider.shedlock.lockAtLeast}",
        lockAtMostFor = "\${app.scheduler.externalprovider.shedlock.lockAtMost}",
    )
    fun execute() {
        runBlocking {
            try {
                LOGGER.info(">>> Starting cron ZoneEventSchedule")
                if (isEnabledScheduler) {
                    createZoneEventService.execute(CreateZoneEventServiceRequest(UUID.randomUUID()))
                }
                LOGGER.info(">>> Ended cron ZoneEventSchedule")
            } catch (exception: Exception) {
                LOGGER.error(">>> Error executing cron ZoneEventSchedule ${exception.message}", exception)
            }
        }
    }
}