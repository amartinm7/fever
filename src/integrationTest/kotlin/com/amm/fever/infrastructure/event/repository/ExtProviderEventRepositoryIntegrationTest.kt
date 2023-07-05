package com.amm.fever.infrastructure.event.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.domain.event.Event
import com.amm.fever.event.GigEventFixtures
import com.amm.fever.event.PerformanceEventFixtures.ANY_END_DATE
import com.amm.fever.event.PerformanceEventFixtures.ANY_START_DATE
import com.amm.fever.infrastructure.SpringBootIntegrationTest
import com.amm.fever.infrastructure.framework.event.repository.extprovider.ExtProviderEventRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ExtProviderEventRepositoryIntegrationTest : SpringBootIntegrationTest() {

    @Autowired
    private lateinit var providerEventRepository: ExtProviderEventRepository

    @Test
    fun `should return a list of events`() = runBlocking {
        val response: List<Event> = providerEventRepository.findBy(
            startsAt = ANY_START_DATE,
            endsAt = ANY_END_DATE
        )
        // let's go to check that the results are the expected 3 events
        assertThat(response.size).isEqualTo(3)
        assertThat(response[0].providerBaseId.value).isEqualTo(GigEventFixtures.ANY_PROVIDER_BASE_ID)
        assertThat(response[0].providerId.value).isEqualTo(GigEventFixtures.ANY_PROVIDER_ID)
        assertThat(response[1].providerBaseId.value).isEqualTo(ANY_PROVIDER_BASE_ID_1)
        assertThat(response[1].providerId.value).isEqualTo(ANY_PROVIDER_ID)
        assertThat(response[2].providerBaseId.value).isEqualTo(ANY_PROVIDER_BASE_ID_2)
        assertThat(response[2].providerId.value).isEqualTo(ANY_PROVIDER_ID)
        // check that the actual event is equals to the expected to check the mapping is well done, but
        // the uuid and the dates are created every time we call to the provider endpoint,
        // so we have to copy from expected to compare and match the two objects.
        // mock the uuid is a little bit hard in this point.
        assertThat(
            response[0].copy(
                id = GigEventFixtures.ANY_EVENTS[0].id,
                audit = GigEventFixtures.ANY_EVENTS[0].audit
            )
        ).isEqualTo(GigEventFixtures.ANY_EVENTS[0])
    }

    companion object {
        const val ANY_PROVIDER_ID = "1642"
        const val ANY_PROVIDER_BASE_ID_1 = "322"
        const val ANY_PROVIDER_BASE_ID_2 = "1591"
    }
}
