package com.amm.fever.infrastructure.framework.event.repository.jpa

import com.amm.fever.domain.event.Audit
import com.amm.fever.domain.event.Capacity
import com.amm.fever.domain.event.CreatedAt
import com.amm.fever.domain.event.EndsAt
import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventRepository
import com.amm.fever.domain.event.MaxPrice
import com.amm.fever.domain.event.MinPrice
import com.amm.fever.domain.event.ModifiedAt
import com.amm.fever.domain.event.Name
import com.amm.fever.domain.event.Numbered
import com.amm.fever.domain.event.OrganizerCompanyId
import com.amm.fever.domain.event.Price
import com.amm.fever.domain.event.ProviderBaseId
import com.amm.fever.domain.event.ProviderId
import com.amm.fever.domain.event.SellFrom
import com.amm.fever.domain.event.SellTo
import com.amm.fever.domain.event.SoldOut
import com.amm.fever.domain.event.StartAt
import com.amm.fever.domain.event.Title
import com.amm.fever.domain.event.Zone
import com.amm.fever.domain.event.ZoneId
import com.amm.fever.domain.event.Zones
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime
import java.util.UUID

class JpaEventRepository(
    private val jpaEventRepositoryClient: JpaEventRepositoryClient,
    private val objectMapper: ObjectMapper
) : EventRepository {

    override fun save(event: Event): Event =
        jpaEventRepositoryClient.findByIds(
            providerId = event.providerId.value,
            providerBaseId = event.providerBaseId.value
        ).saveOrUpdate(event)
            //.also { println(">>> ${Thread.currentThread().name}") }

    private fun JpaEvent?.saveOrUpdate(event: Event): Event {
        this?.let {
            jpaEvent -> event.toJpaEvent(jpaEvent.id, jpaEvent.createdAt).save()
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
            providerId = providerId.value,
            providerBaseId = providerBaseId.value,
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

    private fun Zone.toJpaZone() =
        JpaZone(
            id = id.value,
            capacity = capacity.value,
            price = price.value,
            name = name.value,
            numbered = numbered.value
        )

    private fun List<JpaEvent>.toEvents() =
        map { jpaEvent ->
            Event(
                id = com.amm.fever.domain.event.Id(jpaEvent.id),
                providerId = ProviderId(jpaEvent.providerId),
                providerBaseId = ProviderBaseId(jpaEvent.providerBaseId),
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
        Zone(
            id = ZoneId(id),
            capacity = Capacity(capacity),
            price = Price(price),
            name = Name(name),
            numbered = Numbered(numbered)
        )

    private fun JpaZones.toZones() =
        this.zones.map { jpaZone -> jpaZone.toZone() }.let { zones -> Zones(value = zones) }
}

interface JpaEventRepositoryClient : JpaRepository<JpaEvent, UUID> {
    @Query("SELECT EV FROM JpaEvent EV WHERE EV.startsAt >= :startsAt and EV.endsAt <= :endsAt")
    fun findBy(@Param("startsAt") startsAt: OffsetDateTime, @Param("endsAt") endsAt: OffsetDateTime): List<JpaEvent>

    @Query("SELECT EV FROM JpaEvent EV WHERE EV.providerId = :providerId and EV.providerBaseId = :providerBaseId")
    fun findByIds(
        @Param("providerId") providerId: String,
        @Param("providerBaseId") providerBaseId: String
    ): JpaEvent?
}

@Entity
@Table(name = "EVENT")
data class JpaEvent(
    @Id
    @Column(name = "ID")
    val id: UUID,
    @Column(name = "PROVIDER_ID")
    val providerId: String,
    @Column(name = "PROVIDER_BASE_ID")
    val providerBaseId: String,
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
    @JsonProperty("capacity") val capacity: String,
    @JsonProperty("price") val price: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("numbered") val numbered: String
)

data class JpaZones(
    @JsonProperty("zones") val zones: List<JpaZone>
)

