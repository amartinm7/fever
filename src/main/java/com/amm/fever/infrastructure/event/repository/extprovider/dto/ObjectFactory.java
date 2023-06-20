package com.amm.fever.infrastructure.event.repository.extprovider.dto;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.amm.fever.infrastructure.event.repository package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EventList_QNAME = new QName("", "eventList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amm.fever.infrastructure.event.repository
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EventListType }
     */
    public EventListType createEventListType() {
        return new EventListType();
    }

    /**
     * Create an instance of {@link ZoneType }
     */
    public ZoneType createZoneType() {
        return new ZoneType();
    }

    /**
     * Create an instance of {@link EventType }
     */
    public EventType createEventType() {
        return new EventType();
    }

    /**
     * Create an instance of {@link BaseEventType }
     */
    public BaseEventType createBaseEventType() {
        return new BaseEventType();
    }

    /**
     * Create an instance of {@link OutputType }
     */
    public OutputType createOutputType() {
        return new OutputType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventListType }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link EventListType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "eventList")
    public JAXBElement<EventListType> createEventList(EventListType value) {
        return new JAXBElement<EventListType>(_EventList_QNAME, EventListType.class, null, value);
    }

}
