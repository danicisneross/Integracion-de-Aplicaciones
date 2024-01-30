package es.udc.rs.deliveries.client.service.rest.DtosClient;

import java.net.URI;
import java.time.LocalDateTime;

public class ClientShipmentDto {
    private Long shipmentId;
    private Long customerId;
    private Long packageReference;
    private String address;
    private String status;
    private long hoursLeft;
    private LocalDateTime deliveryDate;
    private URI customerUri;
    private URI selfUri;
    private URI cancelUri;


    public ClientShipmentDto(Long shipmentId, Long customerId, Long packageReference, String address, String status,
                             long hoursLeft, LocalDateTime deliveryDate, URI categoryUri, URI selfUri, URI cancelUri) {
        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.status = status;
        this.hoursLeft = hoursLeft;
        this.deliveryDate = deliveryDate;
        this.customerUri = categoryUri;
        this.selfUri = selfUri;
        this.cancelUri = cancelUri;
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

    public URI getCustomerUri() {
        return customerUri;
    }

    public void setCustomerUri(URI customerUri) {
        this.customerUri = customerUri;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getCancelUri() {
        return cancelUri;
    }

    public void setCancelUri(URI cancelUri) {
        this.cancelUri = cancelUri;
    }

    @Override
    public String toString() {
        return "\nClientShipmentDto{" +
                "shipmentId=" + shipmentId +
                ",\n customerId=" + customerId +
                ",\n packageReference=" + packageReference +
                ",\n address='" + address + '\'' +
                ",\n status='" + status + '\'' +
                ",\n hoursLeft=" + hoursLeft +
                ",\n deliveryDate=" + deliveryDate +
                ",\n customerUri=" + customerUri +
                ",\n selfUri=" + selfUri +
                ",\n cancelUri=" + cancelUri +
                '}';
    }
}