package es.udc.rs.wscontrib.deliveries.service;

import es.udc.rs.wscontrib.crm.service.CrmException;
import es.udc.rs.wscontrib.crm.service.CrmExceptionInfo;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;


@WebService(name = "DeliveryProvider", serviceName = "DeliveryProviderService", targetNamespace = "http://rs.udc.es/delivery")
public class DeliveryService {

    private static Map<Long, Shipment> shipments = new LinkedHashMap<Long, Shipment>();
    private static long lastShipmentId = 0;

    public DeliveryService() {
    }

    private static synchronized long getNextShipmentId() {
        return ++lastShipmentId;
    }

    @PostConstruct()
    private void init() {

    }

    @WebMethod(operationName = "createShipment")
    public Shipment createShipment(@WebParam(name = "customerId") String customerId,
                                   @WebParam(name = "shipmentRef") String shipmentRef,
                                   @WebParam(name = "address") String address)
            throws DeliveryException {
        Calendar creationDate = Calendar.getInstance();
        Long shipmentId = getNextShipmentId();
        Shipment shipment = new Shipment(shipmentId, customerId, shipmentRef, address, creationDate,
                48, ShipmentStatus.PENDING);
        shipments.put(shipmentId, shipment);
        System.out.println(
                "createShipment\n  input parameter" + "\n    customerId: " + customerId
                        + "\n    shipmentRef: " + shipmentRef + "\n    address: " + address
                        + "\n  return " + shipment);
        return shipment;
    }

    @WebMethod(operationName = "findShipment")
    public Shipment findShipment(@WebParam(name = "shipmentId") Long shipmentId) throws DeliveryException {
        System.out.println("findShipment\n  input parameter" + "\n    shipmentId: " + shipmentId);
        Shipment shipment = shipments.get(shipmentId);
        if (shipment == null) {
            throw new DeliveryException(new DeliveryExceptionInfo("Shipment '" + shipmentId
                    + "' not found"));
        }
        System.out.println("  return value" + "\n    " + shipment);
        return shipment;
    }

    @WebMethod(operationName = "changeStatus")
    public Shipment changeStatus(@WebParam(name = "shipmentId") Long shipmentId,
                              @WebParam(name = "shipmentStatus") ShipmentStatus shipmentStatus) throws DeliveryException {
        System.out.println(
                "changeStatus\n  input parameter" + "\n    shipmentId: " + shipmentId + "\n	shipmentStatus: " + shipmentStatus);
        Shipment shipment = shipments.get(shipmentId);
        if (shipment == null) {
            throw new DeliveryException(new DeliveryExceptionInfo("Shipment '" + shipmentId
                    + "' not found"));
        }

        boolean changeAllowed = true;
        switch (shipmentStatus) {
            case PENDING:
                changeAllowed = false;
                break;
            case SENT:
                if (shipment.getShipmentStatus() != ShipmentStatus.PENDING) {
                    changeAllowed = false;
                };
                break;
            case CANCELLED:
                if (shipment.getShipmentStatus() != ShipmentStatus.PENDING) {
                    changeAllowed = false;
                };
                break;
            case DELIVERED:
                if (shipment.getShipmentStatus() != ShipmentStatus.SENT) {
                    changeAllowed = false;
                };
                break;
            case REJECTED:
                if (shipment.getShipmentStatus() != ShipmentStatus.SENT) {
                    changeAllowed = false;
                };
                break;
        }
        if (!changeAllowed) {
            throw new DeliveryException(new DeliveryExceptionInfo("Shipment with shipmentId=\"" + shipmentId +
                    "\" (" + shipment.getShipmentStatus() + ") could not change to \"" + shipmentStatus));
        }
        shipment.setShipmentStatus(shipmentStatus);
        if (shipment.getShipmentStatus() == ShipmentStatus.DELIVERED) {
            shipment.setDeliveryDate(Calendar.getInstance());
        }
        System.out.println("  return value" + "\n    " + shipment);

        return shipment;

    }

}
