package com.amm.fever.infrastructure.framework.zone.repository.jpa

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amm.fever.zone.GigZoneFixtures.ANY_JPA_ZONE
import com.amm.fever.zone.GigZoneFixtures.ANY_ZONE
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpaZoneEventRepositoryTestVO {

    @MockK
    private lateinit var jpaZoneRepositoryClient: JpaZoneRepositoryClient

    @InjectMockKs
    private lateinit var jpaZoneEventRepository: JpaZoneEventRepository

    @Test
    fun `should save a zone`() {
        `mock save a zone`()
        val response = jpaZoneEventRepository.save(ANY_ZONE)
        assertThat(response).isEqualTo(ANY_ZONE)
        verify(exactly = 1) { jpaZoneRepositoryClient.save(ANY_JPA_ZONE) }
    }

    private fun `mock save a zone`() {
        every {
            jpaZoneRepositoryClient.save(ANY_JPA_ZONE)
        } returns ANY_JPA_ZONE
    }
}

