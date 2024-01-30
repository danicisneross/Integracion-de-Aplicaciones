package es.udc.rs.deliveries.jaxrs.util;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxb;
import es.udc.rs.deliveries.jaxrs.resources.CustomerResource;
import es.udc.rs.deliveries.jaxrs.resources.ShipmentResource;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ShipmentToShipmentDtoJaxbConversor {
    public static List<ShipmentDtoJaxb> toShipmentDtoJaxb(List<Shipment> shipments, URI baseUri, String type) {
        List<ShipmentDtoJaxb> shipmentDtoJaxbs = new ArrayList<>(shipments.size());
        for (Shipment shipment : shipments) {
            shipmentDtoJaxbs.add(toShipmentDtoJaxb(shipment, baseUri, type));
        }
        return shipmentDtoJaxbs;
    }
    public static ShipmentDtoJaxb toShipmentDtoJaxb(Shipment shipment,
                                                    URI baseUri, String type) {
        AtomLinkDtoJaxb customerLink = ServiceUtil.getLinkFromUri(baseUri,
                CustomerResource.class, shipment.getCustomerId(), "customer",
                "Owner of the shipment", type);
        AtomLinkDtoJaxb selfLink = ServiceUtil.getLinkFromUri(baseUri, ShipmentResource.class,
                shipment.getShipmentId(), "self", "Self link", type);
        List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
        links.add(customerLink);
        links.add(selfLink);
        if(shipment.getStatus() == ShipmentStatus.PENDING){
            AtomLinkDtoJaxb cancelLink = ServiceUtil.getLinkFromUri(baseUri,
                    CustomerResource.class, shipment.getCustomerId() + "/cancel", "cancel",
                    "Cancel the shipment", type);
            links.add(cancelLink);
        }
            return new ShipmentDtoJaxb(shipment.getShipmentId(), shipment.getCustomerId(),
                    shipment.getPackageReference(), shipment.getAddress(),
                    shipment.getStatus().name(), shipment.getCreationDate().until(
                    shipment.getMaxDeliveryDate(), ChronoUnit.HOURS), shipment.getDeliveryDate(),
                    links);
    }
}