package es.udc.rs.deliveries.client.service.rest.util;

import es.udc.rs.deliveries.client.service.rest.DtosClient.ClientCustomerDto;
import es.udc.rs.deliveries.client.service.rest.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.dto.ClientCustomerDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.dto.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerDtoCustomerDtoJaxbConversor {

    public static JAXBElement<ClientCustomerDtoJaxb> toJaxbCustomer(String name, String Cif, String address) {
        ClientCustomerDtoJaxb customer = new ClientCustomerDtoJaxb();
        customer.setName(name);
        customer.setCif(Cif);
        customer.setAddress(address);
        JAXBElement<ClientCustomerDtoJaxb> jaxbElement = new ObjectFactory().createCustomer(customer);
        return jaxbElement;
    }

    public static ClientCustomerDto toCustomerDto(ClientCustomerDtoJaxb customer) {
        List<AtomLinkDtoJaxb> links = customer.getLinks();
        URI shipmentsUri = LinkUtil.getLinkUriFromList(links, "cusShipments");
        URI selfUri = LinkUtil.getLinkUriFromList(links, "self");
        URI deleteCustomerUri = LinkUtil.getLinkUriFromList(links, "deleteCustomer");
        return new ClientCustomerDto(customer.getId(),customer.getName(),customer.getCif(),
                customer.getAddress(), LocalDateTime.now(), selfUri, shipmentsUri, deleteCustomerUri);
    }

    public static List<ClientCustomerDto> toCustomerDtos(List<ClientCustomerDtoJaxb> customerListDto) {
        List<ClientCustomerDto> customerDtos = new ArrayList<>(customerListDto.size());
        for (ClientCustomerDtoJaxb customerDtoJaxb : customerListDto) {
            customerDtos.add(toCustomerDto(customerDtoJaxb));
        }
        return customerDtos;
    }
}
