package com.amm.fever.infrastructure.framework.zone.repository.jpa

import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.ModifiedAt
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.ZoneId
import com.amm.fever.domain.zone.Zone
import com.amm.fever.domain.zone.ZoneEventEventRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import jakarta.persistence.Version
import java.time.OffsetDateTime
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

class JpaZoneEventRepository(
    private val jpaZoneRepositoryClient: JpaZoneRepositoryClient
) : ZoneEventEventRepository {
    override fun save(zone: Zone): Zone =
        jpaZoneRepositoryClient.save(zone.toJpaZone()).toZone()

    override suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Zone> {
        TODO("Not yet implemented")
    }

    private fun Zone.toJpaZone() =
        JpaZone(
            id = id.value,
            version = version.value,
            zoneId = zoneId.value,
            zoneName = zoneName.value,
            eventId = eventId.value,
            eventBaseId = eventBaseId.value,
            title = title.value,
            startsAt = startsAt.value,
            endsAt = endsAt.value,
            soldOut = soldOut.value,
            numbered = numbered.value,
            capacity = capacity.value,
            price = price.value,
            createdAt = audit.createdAt.value,
            modifiedAt = audit.modifiedAt.value
        )
}

interface JpaZoneRepositoryClient : JpaRepository<JpaZone, UUID>

@Entity
@Table(name = "ZONE")
data class JpaZone(
    @Id
    @Column(name = "ID")
    val id: UUID,
    @Version
    @Column(name = "VERSION")
    val version: Int,
    @Column(name = "ZONE_ID")
    val zoneId: String,
    @Column(name = "EVENT_ID")
    val eventId: String,
    @Column(name = "EVENT_BASE_ID")
    val eventBaseId: String,
    @Column(name = "TITLE")
    val title: String,
    @Column(name = "ZONE_NAME")
    val zoneName: String,
    @Column(name = "CAPACITY")
    val capacity: Long,
    @Column(name = "NUMBERED")
    val numbered: Boolean,
    @Column(name = "SOLD_OUT")
    val soldOut: Boolean,
    @Column(name = "PRICE")
    val price: Double,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    val startsAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    val endsAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    val createdAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_AT")
    val modifiedAt: OffsetDateTime,
) {
    fun toZone() =
        Zone(
            id = com.amm.fever.domain.vo.Id(id),
            version = com.amm.fever.domain.vo.Version(version),
            zoneId = ZoneId(zoneId),
            zoneName = ZoneName(zoneName),
            eventId = EventId(eventId),
            eventBaseId = EventBaseId(eventBaseId),
            title = Title(title),
            startsAt = StartAt(startsAt),
            endsAt = EndsAt(endsAt),
            soldOut = SoldOut(soldOut),
            numbered = Numbered(numbered),
            capacity = Capacity(capacity),
            price = Price(price),
            audit = Audit(
                createdAt = CreatedAt(createdAt),
                modifiedAt = ModifiedAt(modifiedAt)
            )
        )
}