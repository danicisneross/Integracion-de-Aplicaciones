package es.udc.rs.deliveries.jaxrs.Exceptions;

import es.udc.rs.deliveries.jaxrs.dto.ShipmentNotPendingDtoJaxb;
import es.udc.rs.deliveries.model.exceptions.ShipmentNotPending;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ShipmentNotPendingExceptionMapper implements
        ExceptionMapper<ShipmentNotPending> {
    @Override
    public Response toResponse(ShipmentNotPending ex) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ShipmentNotPendingDtoJaxb(ex.getShipmentId()))
                .build();
    }
}
