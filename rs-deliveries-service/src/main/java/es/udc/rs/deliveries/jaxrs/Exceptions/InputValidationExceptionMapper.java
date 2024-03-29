package es.udc.rs.deliveries.jaxrs.Exceptions;

import es.udc.rs.deliveries.jaxrs.dto.InputValidationExceptionDtoJaxb;
import es.udc.ws.util.exceptions.InputValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InputValidationExceptionMapper implements
		ExceptionMapper<InputValidationException> {

	@Override
	public Response toResponse(InputValidationException ex) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(new InputValidationExceptionDtoJaxb(ex.getMessage()))
				.build();
	}

}
