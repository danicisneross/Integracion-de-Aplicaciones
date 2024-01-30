package es.udc.rs.deliveries.client.service.rest.util;


import es.udc.rs.deliveries.client.service.rest.DtosClient.ClientShipmentDto;
import es.udc.rs.deliveries.client.service.rest.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.dto.ClientShipmentDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.dto.ClientShipmentDtoJaxbList;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShipmentDtoToShipmentDtoJaxbConversor {
    public static ClientShipmentDto toShipmentDto(ClientShipmentDtoJaxb shipment) {
        List<AtomLinkDtoJaxb> links = shipment.getLinks();
        URI customerUri = LinkUtil.getLinkUriFromList(links, "customer");
        URI selfUri = LinkUtil.getLinkUriFromList(links, "self");
        URI cancelUri = LinkUtil.getLinkUriFromList(links, "cancel");
        return new ClientShipmentDto(shipment.getId(), shipment.getCustomerId(),
                shipment.getPackageReference(), shipment.getAddress(),
                shipment.getStatus(), shipment.getHoursLeft(), shipment.getDeliveryDate(),
                customerUri, selfUri, cancelUri);
    }

    public static List<ClientShipmentDto> toShipmentDtos(ClientShipmentDtoJaxbList shipmentListDto) {
        List<ClientShipmentDto> shipmentDtos = new ArrayList<>(shipmentListDto.getShipments().size());
        for (ClientShipmentDtoJaxb ShipmentDtoJaxb : shipmentListDto.getShipments()) {
            shipmentDtos.add(toShipmentDto(ShipmentDtoJaxb));
        }
        return shipmentDtos;
    }
}
