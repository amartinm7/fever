package com.amm.fever.infrastructure.framework.event.repository.jpa

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.MaxPrice
import com.amm.fever.domain.vo.MinPrice
import com.amm.fever.domain.vo.ModifiedAt
import com.amm.fever.domain.vo.ZoneName
import com.amm.fever.domain.vo.Numbered
import com.amm.fever.domain.vo.OrganizerCompanyId
import com.amm.fever.domain.vo.Price
import com.amm.fever.domain.vo.EventBaseId
import com.amm.fever.domain.vo.EventId
import com.amm.fever.domain.vo.SellFrom
import com.amm.fever.domain.vo.SellTo
import com.amm.fever.domain.vo.SoldOut
import com.amm.fever.domain.vo.StartAt
import com.amm.fever.domain.vo.Title
import com.amm.fever.domain.vo.ZoneVO
import com.amm.fever.domain.vo.ZoneId
import com.amm.fever.domain.vo.Zones
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.time.OffsetDateTime
import java.util.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

class JpaEventRepository(
    private val jpaEventRepositoryClient: JpaEventRepositoryClient,
    private val objectMapper: ObjectMapper
) : EventRepository {

    override fun save(event: Event): Event =
        jpaEventRepositoryClient.findByIds(
            eventId = event.eventId.value,
            eventBaseId = event.eventBaseId.value
        ).saveOrUpdate(event)
    //.also { println(">>> ${Thread.currentThread().name}") }

    private fun JpaEvent?.saveOrUpdate(event: Event): Event {
        this?.let { jpaEvent ->
            event.toJpaEvent(jpaEvent.id, jpaEvent.createdAt).save()
        } ?: event.toJpaEvent().save()
        return event
    }

    private fun JpaEvent.save(): JpaEvent = jpaEventRepositoryClient.save(this)

    override suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event> {
        return jpaEventRepositoryClient.findBy(startsAt = startsAt, endsAt = endsAt).toEvents()
    }

    private fun Event.toJpaEvent(jpaId: UUID? = null, createdAt: OffsetDateTime? = null) =
        JpaEvent(
            id = jpaId ?: id.value,
            eventId = eventId.value,
            eventBaseId = eventBaseId.value,
            organizerCompanyId = organizerCompanyId?.value,
            title = title.value,
            startsAt = startsAt.value,
            endsAt = endsAt.value,
            sellFrom = sellFrom.value,
            sellTo = sellTo.value,
            soldOut = soldOut.value,
            zones = objectMapper.writeValueAsString(zones.toJpaZones()),
            minPrice = minPrice.value,
            maxPrice = maxPrice.value,
            createdAt = createdAt ?: audit.createdAt.value,
            modifiedAt = audit.modifiedAt.value
        )

    private fun Zones.toJpaZones() =
        JpaZones(zones = this.value.map { zone -> zone.toJpaZone() })

    private fun ZoneVO.toJpaZone() =
        JpaZone(
            id = id.value,
            capacity = capacity.value,
            price = price.value,
            name = zoneName.value,
            numbered = numbered.value
        )

    private fun List<JpaEvent>.toEvents() =
        map { jpaEvent ->
            Event(
                id = com.amm.fever.domain.vo.Id(jpaEvent.id),
                eventId = EventId(jpaEvent.eventId),
                eventBaseId = EventBaseId(jpaEvent.eventBaseId),
                organizerCompanyId = jpaEvent.organizerCompanyId?.let { OrganizerCompanyId(jpaEvent.organizerCompanyId) },
                title = Title(jpaEvent.title),
                startsAt = StartAt(jpaEvent.startsAt),
                endsAt = EndsAt(jpaEvent.endsAt),
                sellFrom = SellFrom(jpaEvent.sellFrom),
                sellTo = SellTo(jpaEvent.sellTo),
                soldOut = SoldOut(jpaEvent.soldOut),
                zones = objectMapper.readValue(jpaEvent.zones, JpaZones::class.java).toZones(),
                minPrice = MinPrice(jpaEvent.minPrice),
                maxPrice = MaxPrice(jpaEvent.maxPrice),
                audit = Audit(
                    createdAt = CreatedAt(jpaEvent.createdAt),
                    modifiedAt = ModifiedAt(jpaEvent.modifiedAt)
                )
            )
        }

    private fun JpaZone.toZone() =
        ZoneVO(
            id = ZoneId(id),
            capacity = Capacity(capacity),
            price = Price(price),
            zoneName = ZoneName(name),
            numbered = Numbered(numbered)
        )

    private fun JpaZones.toZones() =
        this.zones.map { jpaZone -> jpaZone.toZone() }.let { zones -> Zones(value = zones) }
}

interface JpaEventRepositoryClient : JpaRepository<JpaEvent, UUID> {
    @Query("SELECT EV FROM JpaEvent EV WHERE EV.startsAt >= :startsAt and EV.endsAt <= :endsAt")
    fun findBy(@Param("startsAt") startsAt: OffsetDateTime, @Param("endsAt") endsAt: OffsetDateTime): List<JpaEvent>

    @Query("SELECT EV FROM JpaEvent EV WHERE EV.eventId = :eventId and EV.eventBaseId = :eventBaseId")
    fun findByIds(
        @Param("eventId") eventId: String,
        @Param("eventBaseId") eventBaseId: String
    ): JpaEvent?
}

@Entity
@Table(name = "EVENT")
data class JpaEvent(
    @Id
    @Column(name = "ID")
    val id: UUID,
    @Column(name = "EVENT_ID")
    val eventId: String,
    @Column(name = "EVENT_BASE_ID")
    val eventBaseId: String,
    @Column(name = "ORGANIZER_COMPANY_ID")
    val organizerCompanyId: String?,
    @Column(name = "TITLE")
    val title: String,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    val startsAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    val endsAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SELL_FROM")
    val sellFrom: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SELL_TO")
    val sellTo: OffsetDateTime,
    @Column(name = "SOLD_OUT")
    val soldOut: Boolean,
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "zones")
    val zones: String,
    @Column(name = "MIN_PRICE")
    val minPrice: Double,
    @Column(name = "MAX_PRICE")
    val maxPrice: Double,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    val createdAt: OffsetDateTime,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_AT")
    val modifiedAt: OffsetDateTime,
)

data class JpaZone(
    @JsonProperty("zone_id") val id: String,
    @JsonProperty("capacity") val capacity: Long,
    @JsonProperty("price") val price: Double,
    @JsonProperty("name") val name: String,
    @JsonProperty("numbered") val numbered: Boolean
)

data class JpaZones(
    @JsonProperty("zones") val zones: List<JpaZone>
)

