package es.udc.rs.deliveries.client.service;

import es.udc.rs.deliveries.client.service.rest.DtosClient.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;


public interface ClientDeliveryService {

    ClientCustomerDto addCustomer(String name, String Cif, String address)
            throws InputValidationException;
    void removeCustomer(Long customerId)
            throws InstanceNotFoundException, ClientCustomerNoEmptyException;
    ShipmentListInterval findShipments(Long customerId,
                                       String status /*optional*/)
            throws InputValidationException;

    void changeStatus(Long shipmentId, String status)
            throws InputValidationException, InstanceNotFoundException, ClientShipmentStatusException;
}
