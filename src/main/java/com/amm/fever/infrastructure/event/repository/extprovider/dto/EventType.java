package com.amm.fever.infrastructure.event.repository.extprovider.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para eventType complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="eventType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="zone" type="{}zoneType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="event_start_date" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="event_end_date" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="event_id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sell_from" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sell_to" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sold_out" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventType", propOrder = {
        "zone"
})
public class EventType {

    protected List<ZoneType> zone;
    @XmlAttribute(name = "event_start_date")
    protected String eventStartDate;
    @XmlAttribute(name = "event_end_date")
    protected String eventEndDate;
    @XmlAttribute(name = "event_id")
    protected String eventId;
    @XmlAttribute(name = "sell_from")
    protected String sellFrom;
    @XmlAttribute(name = "sell_to")
    protected String sellTo;
    @XmlAttribute(name = "sold_out")
    protected String soldOut;

    /**
     * Gets the value of the zone property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the zone property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZone().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZoneType }
     */
    public List<ZoneType> getZone() {
        if (zone == null) {
            zone = new ArrayList<ZoneType>();
        }
        return this.zone;
    }

    /**
     * Obtiene el valor de la propiedad eventStartDate.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEventStartDate() {
        return eventStartDate;
    }

    /**
     * Define el valor de la propiedad eventStartDate.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEventStartDate(String value) {
        this.eventStartDate = value;
    }

    /**
     * Obtiene el valor de la propiedad eventEndDate.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEventEndDate() {
        return eventEndDate;
    }

    /**
     * Define el valor de la propiedad eventEndDate.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEventEndDate(String value) {
        this.eventEndDate = value;
    }

    /**
     * Obtiene el valor de la propiedad eventId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Define el valor de la propiedad eventId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEventId(String value) {
        this.eventId = value;
    }

    /**
     * Obtiene el valor de la propiedad sellFrom.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSellFrom() {
        return sellFrom;
    }

    /**
     * Define el valor de la propiedad sellFrom.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellFrom(String value) {
        this.sellFrom = value;
    }

    /**
     * Obtiene el valor de la propiedad sellTo.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSellTo() {
        return sellTo;
    }

    /**
     * Define el valor de la propiedad sellTo.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSellTo(String value) {
        this.sellTo = value;
    }

    /**
     * Obtiene el valor de la propiedad soldOut.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSoldOut() {
        return soldOut;
    }

    /**
     * Define el valor de la propiedad soldOut.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSoldOut(String value) {
        this.soldOut = value;
    }

}
