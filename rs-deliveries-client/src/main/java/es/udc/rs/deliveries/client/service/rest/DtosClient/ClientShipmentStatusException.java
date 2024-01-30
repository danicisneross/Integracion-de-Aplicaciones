package es.udc.rs.deliveries.client.service.rest.DtosClient;


public class ClientShipmentStatusException extends Exception{


    public ClientShipmentStatusException(String message){
        //super("Shipment with id=\"" + shipmentId + "\" can't change it's (Shipment status = \"" + status + "\")");
        super(message);

    }
}