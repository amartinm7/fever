package com.amm.fever.infrastructure.framework.schedule.externalprovider

import com.amm.fever.application.event.ExternalProviderService
import com.amm.fever.application.event.ExternalProviderServiceRequest
import kotlinx.coroutines.runBlocking
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ExternalProviderSchedule(
    private val externalProviderService: ExternalProviderService,
    @Value("\${app.scheduler.externalprovider.enabled}")
    private var isEnabledScheduler: Boolean = false
) {

    private val LOGGER = LoggerFactory.getLogger(ExternalProviderSchedule::class.java)

    @Scheduled(cron = "\${app.scheduler.externalprovider.scheduled}")
    @SchedulerLock(
        name = "external_provider_service",
        lockAtLeastFor = "\${app.scheduler.externalprovider.shedlock.lockAtLeast}",
        lockAtMostFor = "\${app.scheduler.externalprovider.shedlock.lockAtMost}",
    )
    fun launch() {
        runBlocking {
            try {
                LOGGER.info(">>> Starting cron ExternalProviderSchedule-launch")
                if (isEnabledScheduler) {
                    externalProviderService.execute(ExternalProviderServiceRequest(""))
                }
                LOGGER.info(">>> Ended cron ExternalProviderSchedule-launch")
            } catch (exception: Exception) {
                LOGGER.error(">>> Error executing cron DeleteExpiredAds ${exception.message}", exception)
            }
        }
    }
}