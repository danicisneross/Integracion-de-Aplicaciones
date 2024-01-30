package es.udc.rs.deliveries.jaxrs.Exceptions;

import es.udc.rs.deliveries.jaxrs.dto.ExceptionDtoJaxb;
import es.udc.rs.deliveries.model.exceptions.CustomerNoEmpty;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
@Provider
public class CustomerNoEmptyExceptionMapper implements  ExceptionMapper<CustomerNoEmpty>{

    @Override
    public Response toResponse(CustomerNoEmpty ex) {
        ExceptionDtoJaxb exceptionDtoJaxb = new ExceptionDtoJaxb("CustomerNoEmpty");
        exceptionDtoJaxb.addParam("customerId", ex.getCustomerId().toString());
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(exceptionDtoJaxb).build();
    }
}
