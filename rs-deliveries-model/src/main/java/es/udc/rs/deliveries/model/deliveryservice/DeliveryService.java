package es.udc.rs.deliveries.model.deliveryservice;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.exceptions.ShipmentNotPending;
import es.udc.rs.deliveries.model.exceptions.ShipmentStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.rs.deliveries.model.exceptions.CustomerNoEmpty;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;
//Usar las excepciones de es.udc.ws.util
public interface DeliveryService {

    Customer addCustomer(String name, String Cif, String address)
            throws InputValidationException;
    void deleteCustomer(Long customerId)
            throws InstanceNotFoundException, CustomerNoEmpty;
    void updateCustomer(Customer customer)
            throws InputValidationException, InstanceNotFoundException;
    Customer findCustomer(Long customerId)
            throws InputValidationException, InstanceNotFoundException;
    List<Customer> findCustomerKeyword(String KeyWord);

    Shipment addShipment(Long customerId, Long packageReference, String address)
            throws InstanceNotFoundException, InputValidationException;
    Shipment updateShipment(Long shipmentId, ShipmentStatus status)
            throws InputValidationException, InstanceNotFoundException, ShipmentStatusException;
    void cancelShipment(Long shipmentId)
            throws InstanceNotFoundException, ShipmentNotPending;
    Shipment findShipment(Long shipmentId)
            throws InputValidationException, InstanceNotFoundException;
    List<Shipment> findShipments(Long customerId, ShipmentStatus status/*optional*/,
                                 int startIndex, int count)
            throws InputValidationException;
}
