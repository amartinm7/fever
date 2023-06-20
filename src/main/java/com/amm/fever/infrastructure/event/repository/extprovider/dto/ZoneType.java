
package com.amm.fever.infrastructure.event.repository.extprovider.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Clase Java para zoneType complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="zoneType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="zone_id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="capacity" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="price" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="numbered" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "zoneType", propOrder = {
        "value"
})
public class ZoneType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "zone_id")
    protected String zoneId;
    @XmlAttribute(name = "capacity")
    protected String capacity;
    @XmlAttribute(name = "price")
    protected String price;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "numbered")
    protected String numbered;

    /**
     * Obtiene el valor de la propiedad value.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Define el valor de la propiedad value.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Obtiene el valor de la propiedad zoneId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * Define el valor de la propiedad zoneId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setZoneId(String value) {
        this.zoneId = value;
    }

    /**
     * Obtiene el valor de la propiedad capacity.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Define el valor de la propiedad capacity.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCapacity(String value) {
        this.capacity = value;
    }

    /**
     * Obtiene el valor de la propiedad price.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPrice() {
        return price;
    }

    /**
     * Define el valor de la propiedad price.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad numbered.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNumbered() {
        return numbered;
    }

    /**
     * Define el valor de la propiedad numbered.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumbered(String value) {
        this.numbered = value;
    }

}
