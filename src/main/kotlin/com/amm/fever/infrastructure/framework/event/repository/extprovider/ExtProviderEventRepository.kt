package com.amm.fever.infrastructure.framework.event.repository.extprovider

import com.amm.fever.domain.event.Event
import com.amm.fever.domain.event.EventCommunicationException
import com.amm.fever.domain.event.EventNotFoundException
import com.amm.fever.domain.event.EventUnprocessableEntityException
import com.amm.fever.domain.event.ProviderEventRepository
import com.amm.fever.domain.vo.Audit
import com.amm.fever.domain.vo.Capacity
import com.amm.fever.domain.vo.CreatedAt
import com.amm.fever.domain.vo.EndsAt
import com.amm.fever.domain.vo.Id
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
import com.amm.fever.infrastructure.event.repository.extprovider.dto.BaseEventType
import com.amm.fever.infrastructure.event.repository.extprovider.dto.EventListType
import com.amm.fever.infrastructure.event.repository.extprovider.dto.ZoneType
import com.amm.fever.infrastructure.services.OffsetDateTimeHandler
import com.amm.fever.infrastructure.services.UuidService
import com.amm.fever.infrastructure.services.toOffsetDateTime
import java.time.OffsetDateTime
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

class ExtProviderEventRepository(
    private val eventExtProviderWebClient: WebClient,
    private val uuidService: UuidService,
    private val offsetDateTimeHandler: OffsetDateTimeHandler
) : ProviderEventRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val is5XX = { statusCode: HttpStatusCode -> statusCode.is5xxServerError }
    private val is4XX = { statusCode: HttpStatusCode -> statusCode.is4xxClientError }

    @Deprecated("Use the new findby() method", level = DeprecationLevel.WARNING)
    override suspend fun findBy(startsAt: OffsetDateTime, endsAt: OffsetDateTime): List<Event> =
        eventExtProviderWebClient.get()
            .uri("/api/events")
            .retrieve()
            .onStatus(is5XX) { clientResponse -> clientResponse.toServerError() }
            .onStatus(is4XX) { clientResponse -> clientResponse.toClientError() }
            // .bodyToMono(EventListType::class.java)
            // .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
            .awaitBody<EventListType>().toEvents()

    override suspend fun findBy(): List<Event> =
        eventExtProviderWebClient.get()
            .uri("/api/events")
            .retrieve()
            .onStatus(is5XX) { clientResponse -> clientResponse.toServerError() }
            .onStatus(is4XX) { clientResponse -> clientResponse.toClientError() }
            // .bodyToMono(EventListType::class.java)
            // .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
            .awaitBody<EventListType>().toEvents()

    private fun ClientResponse.toServerError() =
        Mono.error<EventCommunicationException>(
            EventCommunicationException(
                "Impossible communicate con events server provider",
                RuntimeException("${bodyToMono(String::class.java)}")
            )
        ).also {
            logger.error("ExtProviderEventRepositoryClient Server Error endpoint with status code {}", statusCode())
        }

    private fun ClientResponse.toClientError() =
        when (statusCode()) {
            HttpStatus.NOT_FOUND -> Mono.error<EventNotFoundException>(EventNotFoundException(""))
            else -> Mono.error<EventUnprocessableEntityException>(EventUnprocessableEntityException(""))
        }.also { exception -> logger.warn(exception.toString()) }

    private fun List<ZoneType>.toZones(): List<ZoneVO> =
        map { zoneType ->
            ZoneVO(
                id = ZoneId(zoneType.zoneId),
                capacity = Capacity(zoneType.capacity.toLong()),
                price = Price(zoneType.price.toDouble()),
                zoneName = ZoneName(zoneType.name),
                numbered = Numbered(zoneType.numbered.toBoolean())
            )
        }

    private fun List<ZoneType>.minPrice(): Double =
        minOfOrNull { item -> item.price.toDouble() } ?: 0.0

    private fun List<ZoneType>.maxPrice() =
        maxOfOrNull { item -> item.price.toDouble() } ?: 0.0

    private fun BaseEventType.toEvent(): Event =
        Event(
            id = Id(uuidService.randomUUID()),
            eventId = EventId(event.eventId),
            eventBaseId = EventBaseId(baseEventId),
            organizerCompanyId = organizerCompanyId?.let { OrganizerCompanyId(organizerCompanyId) },
            title = Title(title),
            startsAt = StartAt(event.eventStartDate.toOffsetDateTime()),
            endsAt = EndsAt(event.eventEndDate.toOffsetDateTime()),
            sellFrom = SellFrom(event.sellFrom.toOffsetDateTime()),
            sellTo = SellTo(event.sellTo.toOffsetDateTime()),
            soldOut = SoldOut(event.soldOut.toBoolean()),
            zones = Zones(event.zone.toZones()),
            minPrice = MinPrice(event.zone.minPrice()),
            maxPrice = MaxPrice(event.zone.maxPrice()),
            audit = Audit(
                createdAt = CreatedAt(offsetDateTimeHandler.now()),
                modifiedAt = ModifiedAt(offsetDateTimeHandler.now())
            )
        )

    private fun EventListType.toEvents(): List<Event> =
        this.output.baseEvent
            .filter { item -> item.sellMode == "online" }
            .map { item -> item.toEvent() }
}
