package es.udc.rs.deliveries.jaxrs.Exceptions;

import es.udc.rs.deliveries.jaxrs.dto.InstanceNotFoundExceptionDtoJaxb;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InstanceNotFoundExceptionMapper implements
		ExceptionMapper<InstanceNotFoundException> {

	@Override
	public Response toResponse(InstanceNotFoundException ex) {
		return Response
				.status(Response.Status.NOT_FOUND)
				.entity(new InstanceNotFoundExceptionDtoJaxb(
						ex.getInstanceId(), ex.getInstanceType())).build();

	}

}