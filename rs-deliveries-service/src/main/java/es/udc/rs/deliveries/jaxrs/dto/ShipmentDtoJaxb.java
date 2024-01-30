package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlSchemaType;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "shipment")
@XmlType(name="shipmentInfo", propOrder = {"shipmentId", "customerId", "packageReference",
        "address", "status", "hoursLeft", "deliveryDate", "links"})
public class ShipmentDtoJaxb {
    @XmlAttribute(name = "shipmentId", required = true)
    private Long shipmentId;
    @XmlElement(required = true)
    private Long customerId;
    @XmlElement(required = true)
    private Long packageReference;
    @XmlElement(required = true)
    private String address;
    private String status;
    private long hoursLeft;
    @XmlSchemaType(name = "dateTime")
    private LocalDateTime deliveryDate;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;

    public ShipmentDtoJaxb() {}
    public ShipmentDtoJaxb(Long shipmentId, Long customerId, Long packageReference,
                           String address, String status, long hoursLeft,
                           LocalDateTime deliveryDate, List<AtomLinkDtoJaxb> links) {
        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.status = status;
        this.hoursLeft = hoursLeft;
        this.deliveryDate = deliveryDate;
        this.links = links;
    }

    public ShipmentDtoJaxb(Long shipmentId, Long customerId, Long packageReference,
                           String address, String status, long hoursLeft, LocalDateTime deliveryDate) {
        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.status = status;
        this.hoursLeft = hoursLeft;
        this.deliveryDate = deliveryDate;
    }

    public ShipmentDtoJaxb(Long shipmentId, Long customerId, Long packageReference, String address, String status, long hoursLeft) {
        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.status = status;
        this.hoursLeft = hoursLeft;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPackageReference() {
        return packageReference;
    }

    public void setPackageReference(Long packageReference) {
        this.packageReference = packageReference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getHoursLeft() {
        return hoursLeft;
    }

    public void setHoursLeft(long hoursLeft) {
        this.hoursLeft = hoursLeft;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }

}
