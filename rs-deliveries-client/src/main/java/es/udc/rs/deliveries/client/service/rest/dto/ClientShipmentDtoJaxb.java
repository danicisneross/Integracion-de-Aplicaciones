//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package es.udc.rs.deliveries.client.service.rest.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import es.udc.rs.deliveries.client.service.rest.jaxb.LocalDateTimeXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para shipmentInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="shipmentInfo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="customerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="packageReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="hoursLeft" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="deliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element ref="{http://www.w3.org/2005/Atom}link" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="shipmentId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shipmentInfo", propOrder = {
    "customerId",
    "packageReference",
    "address",
    "status",
    "hoursLeft",
    "deliveryDate",
    "links"
})
public class ClientShipmentDtoJaxb {

    protected long customerId;
    protected long packageReference;
    @XmlElement(required = true)
    protected String address;
    protected String status;
    protected long hoursLeft;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected LocalDateTime deliveryDate;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    protected List<AtomLinkDtoJaxb> links;
    @XmlAttribute(name = "shipmentId", required = true)
    protected long id;

    /**
     * Obtiene el valor de la propiedad customerId.
     * 
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Define el valor de la propiedad customerId.
     * 
     */
    public void setCustomerId(long value) {
        this.customerId = value;
    }

    /**
     * Obtiene el valor de la propiedad packageReference.
     * 
     */
    public long getPackageReference() {
        return packageReference;
    }

    /**
     * Define el valor de la propiedad packageReference.
     * 
     */
    public void setPackageReference(long value) {
        this.packageReference = value;
    }

    /**
     * Obtiene el valor de la propiedad address.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Define el valor de la propiedad address.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad hoursLeft.
     * 
     */
    public long getHoursLeft() {
        return hoursLeft;
    }

    /**
     * Define el valor de la propiedad hoursLeft.
     * 
     */
    public void setHoursLeft(long value) {
        this.hoursLeft = value;
    }

    /**
     * Obtiene el valor de la propiedad deliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Define el valor de la propiedad deliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDate(LocalDateTime value) {
        this.deliveryDate = value;
    }

    /**
     * Gets the value of the links property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the links property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtomLinkDtoJaxb }
     * 
     * 
     * @return
     *     The value of the links property.
     */
    public List<AtomLinkDtoJaxb> getLinks() {
        if (links == null) {
            links = new ArrayList<>();
        }
        return this.links;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

}
