package es.udc.rs.wscontrib.deliveries.service;

import java.util.Calendar;

public class Shipment {
    private Long shipmentId;
    private String customerId;
    private String shipmentRef;
    private String address;

    private Calendar creationDate;
    private int expectedHoursToDeliver;

    private ShipmentStatus shipmentStatus;

    private Calendar deliveryDate;

    public Shipment() {
    }

    public Shipment(Long shipmentId, String customerId, String shipmentRef, String address, Calendar creationDate,
                    int expectedHoursToDeliver, ShipmentStatus shipmentStatus) {
        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.shipmentRef = shipmentRef;
        this.address = address;
        this.creationDate = creationDate;
        this.expectedHoursToDeliver = expectedHoursToDeliver;
        this.shipmentStatus = shipmentStatus;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShipmentRef() {
        return shipmentRef;
    }

    public void setShipmentRef(String shipmentRef) {
        this.shipmentRef = shipmentRef;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public int getExpectedHoursToDeliver() {
        return expectedHoursToDeliver;
    }

    public void setExpectedHoursToDeliver(int expectedHoursToDeliver) {
        this.expectedHoursToDeliver = expectedHoursToDeliver;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Calendar getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Calendar deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Shipment[" +
                "shipmentId=" + shipmentId +
                ", customerId='" + customerId + '\'' +
                ", shipmentRef='" + shipmentRef + '\'' +
                ", address='" + address + '\'' +
                ", creationDate=" + (creationDate != null?creationDate.getTime():creationDate) +
                ", expectedHoursToDeliver=" + expectedHoursToDeliver +
                ", shipmentStatus=" + shipmentStatus +
                ", deliveryDate=" +  (deliveryDate != null?deliveryDate.getTime():deliveryDate) +
                ']';
    }
}
