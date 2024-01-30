package es.udc.rs.deliveries.jaxrs.Exceptions;

import es.udc.rs.deliveries.jaxrs.dto.ExceptionDtoJaxb;
import es.udc.rs.deliveries.model.exceptions.ShipmentStatusException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ShipmentStatusExceptionMapper implements ExceptionMapper<ShipmentStatusException> {

    @Override
    public Response toResponse(ShipmentStatusException ex) {
        ExceptionDtoJaxb exceptionDtoJaxb = new ExceptionDtoJaxb("ShipmentStatusException");
        exceptionDtoJaxb.addParam("message", ex.getMessage());
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(exceptionDtoJaxb).build();
    }
}
