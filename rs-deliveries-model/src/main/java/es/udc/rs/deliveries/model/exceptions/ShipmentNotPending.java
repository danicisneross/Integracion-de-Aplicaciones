package es.udc.rs.deliveries.model.exceptions;

public class ShipmentNotPending extends Exception{
    private Long shipmentId;
    public ShipmentNotPending(Long shipmentId) {
        super("The shipment " +  shipmentId + " is not in the PENDING state" );
        this.shipmentId = shipmentId;

    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

}