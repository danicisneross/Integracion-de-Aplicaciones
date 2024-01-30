package es.udc.rs.deliveries.jaxrs.util;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.deliveries.jaxrs.resources.CustomerResource;
import es.udc.rs.deliveries.jaxrs.resources.ShipmentResource;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.ws.util.exceptions.InputValidationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CustomerToCustomerDtoJaxBConversor {
        public static List<CustomerDtoJaxb> toCustomerDtoJaxb(List<Customer> customers,
                                                              URI baseUri, String type) throws InputValidationException {
            List<CustomerDtoJaxb> customerDtos = new ArrayList<>(customers.size());
            for (Customer customer : customers) {
                customerDtos.add(toCustomerDtoJaxb(customer, baseUri, type));
            }
            return customerDtos;
        }

        public static CustomerDtoJaxb toCustomerDtoJaxb(Customer customer,
                                                        URI baseUri, String type) throws InputValidationException {
            AtomLinkDtoJaxb shipmentsLink = ServiceUtil.getShipmentsIntervalLinkFromUri(baseUri,
                    ShipmentResource.class,
                    customer.getCustomerId(), "cusShipments",
                    "First 10 shipments of the client", type);
            AtomLinkDtoJaxb selfLink = ServiceUtil.getLinkFromUri(baseUri, CustomerResource.class,
                    customer.getCustomerId(), "self", "Self link", type);
            List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
            links.add(shipmentsLink);
            links.add(selfLink);
            List<Shipment> shipmentsList = DeliveryServiceFactory.getService().findShipments(customer.getCustomerId(), null,0,1);
            if (shipmentsList.isEmpty()){
                AtomLinkDtoJaxb deleteCustomerLink = ServiceUtil.getLinkFromUri(baseUri,
                            CustomerResource.class, customer.getCustomerId(),"DeleteCustomer", "Delete the customer", type);
                links.add(deleteCustomerLink);
            }
            return new CustomerDtoJaxb(customer.getCustomerId(), customer.getName(),
                    customer.getCif(), customer.getAddress(), links);
        }

        public static Customer toCustomer(CustomerDtoJaxb customer) {
            return new Customer(customer.getCustomerId(), customer.getName(),customer.getCif(), customer.getAddress());
        }
}


