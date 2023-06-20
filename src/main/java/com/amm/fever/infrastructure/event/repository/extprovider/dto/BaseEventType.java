
package com.amm.fever.infrastructure.event.repository.extprovider.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para base_eventType complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="base_eventType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="event" type="{}eventType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="base_event_id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sell_mode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="organizer_company_id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "base_eventType", propOrder = {
        "event"
})
public class BaseEventType {

    @XmlElement(required = true)
    protected EventType event;
    @XmlAttribute(name = "base_event_id")
    protected String baseEventId;
    @XmlAttribute(name = "sell_mode")
    protected String sellMode;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "organizer_company_id")
    protected String organizerCompanyId;

    /**
     * Obtiene el valor de la propiedad event.
     *
     * @return possible object is
     * {@link EventType }
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Define el valor de la propiedad event.
     *
     * @param value allowed object is
     *              {@link EventType }
     */
    public void setEvent(EventType value) {
        this.event = value;
    }

    /**
     * Obtiene el valor de la propiedad baseEventId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBaseEventId() {
        return baseEventId;
    }

    /**
     * Define el valor de la propiedad baseEventId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBaseEventId(String value) {
        this.baseEventId = value;
    }

    /**
     * Obtiene el valor de la propiedad sellMode.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSellMode() {
        return sellMode;
    }

    /**
     * Define el valor de la propiedad sellMode.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellMode(String value) {
        this.sellMode = value;
    }

    /**
     * Obtiene el valor de la propiedad title.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtiene el valor de la propiedad organizerCompanyId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOrganizerCompanyId() {
        return organizerCompanyId;
    }

    /**
     * Define el valor de la propiedad organizerCompanyId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrganizerCompanyId(String value) {
        this.organizerCompanyId = value;
    }

}
