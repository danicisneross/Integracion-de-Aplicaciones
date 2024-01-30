package es.udc.rs.deliveries.client.service.rest;

import es.udc.rs.deliveries.client.service.rest.DtosClient.*;
import es.udc.rs.deliveries.client.service.rest.dto.*;
import es.udc.rs.deliveries.client.service.rest.dto.ClientShipmentDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.util.CustomerDtoCustomerDtoJaxbConversor;
import es.udc.rs.deliveries.client.service.rest.util.JaxbExceptionConversor;
import es.udc.rs.deliveries.client.service.rest.util.LinkUtil;
import es.udc.rs.deliveries.client.service.rest.util.ShipmentDtoToShipmentDtoJaxbConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import es.udc.rs.deliveries.client.service.ClientDeliveryService;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import jakarta.ws.rs.core.Response;
import es.udc.rs.deliveries.client.service.rest.json.JaxbJsonContextResolver;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class RestClientDeliveryService implements ClientDeliveryService {

	private static jakarta.ws.rs.client.Client client = null;

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientDeliveryService.endpointAddress";
	private final static String endpointAddress =
			ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

	private final static String MEDIA_TYPE_PARAMETER = "RestClientDeliveryService.mediaType";
	private MediaType mediaType = null;
	/*
	 * Client instances are expensive resources. It is recommended a configured
	 * instance is reused for the creation of Web resources. The creation of Web
	 * resources, the building of requests and receiving of responses are
	 * guaranteed to be thread safe. Thus, a Client instance and WebTarget
	 * instances may be shared between multiple threads.
	 */
	private static Client getClient() {

		client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		client.register(JaxbJsonContextResolver.class);
		return client;

	}


	protected MediaType getMediaType(){
		if (this.mediaType == null) {
			String mediaTypeAsString = ConfigurationParametersManager
					.getParameter(MEDIA_TYPE_PARAMETER);
			this.mediaType = MediaType.valueOf(mediaTypeAsString);
		}
		return this.mediaType;
	}

	@Override
	public ClientCustomerDto addCustomer(String name, String Cif, String address) throws InputValidationException {
		try (Client client = getClient()) {
			WebTarget wt = client.target(endpointAddress).path("customer");
			try (Response response = wt.request()
					.accept(this.getMediaType())
					.post(Entity.entity(
							CustomerDtoCustomerDtoJaxbConversor.toJaxbCustomer(name,Cif,address),
							this.getMediaType()))) {
				validateResponse(Response.Status.CREATED.getStatusCode(), response);
				ClientCustomerDtoJaxb resultCustomer = response.readEntity(ClientCustomerDtoJaxb.class);
				return CustomerDtoCustomerDtoJaxbConversor.toCustomerDto(resultCustomer);
			} catch (InputValidationException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}



	@Override
	public void removeCustomer(Long customerId) throws InstanceNotFoundException, ClientCustomerNoEmptyException {
		try (Client client = getClient()) {
			WebTarget wt = client.target(endpointAddress)
					.path("customer/{id}").resolveTemplate("id", customerId);
			try (Response response = wt.request()
					.accept(this.getMediaType()).delete()) {
				validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
			} catch (InstanceNotFoundException | ClientCustomerNoEmptyException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	@Override
	public ShipmentListInterval findShipments(Long customerId, String status) throws InputValidationException {
		return findShipments(customerId, status, 0, 1);
	}

	public ShipmentListInterval findShipments(Long customerId, String status, int startIndex, int count)
			throws InputValidationException {
		try (Client client = getClient()) {
			WebTarget wt = client.target(endpointAddress)
					.path("shipment").queryParam("id", customerId)
					.queryParam("status", status).queryParam("startIndex", startIndex)
					.queryParam("count", count);
			return findShipments(wt, this.getMediaType());
		}
	}
	public ShipmentListInterval findShipmentsByUri(URI uri) throws InputValidationException {
		try (Client client = getClient()) {
			WebTarget wt = client.target(uri);
			return findShipments(wt, this.getMediaType());
		}
	}
	private ShipmentListInterval findShipments(WebTarget wt, MediaType type) throws InputValidationException {
		try (Response response = (type != null) ? wt.request().accept(type).get()
				: wt.request().get()) {
			validateResponse(Response.Status.OK.getStatusCode(), response);
			ClientShipmentDtoJaxbList shipments =
					response.readEntity(ClientShipmentDtoJaxbList.class);
			return new ShipmentListInterval(
					ShipmentDtoToShipmentDtoJaxbConversor.toShipmentDtos(shipments),
					LinkUtil.getHeaderLinkUri(response, "next"),
					LinkUtil.getHeaderLinkUri(response, "previous"));
		} catch (InputValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public int getNumberOfShipments(Long customerId, String status) {
		try (Client client = getClient()) {
			int numShipments = 0;
			WebTarget wt = client.target(endpointAddress)
					.path("shipment")
					.queryParam("id", customerId)
					.queryParam("status", status);
			Response response = wt.request().accept(this.getMediaType()).get();
			try {
				validateResponse(Response.Status.OK.getStatusCode(), response);
				ClientShipmentDtoJaxbList shipments = response
						.readEntity(ClientShipmentDtoJaxbList.class);
				numShipments += shipments.getShipments().size();
				while (response.getLink("next") != null) {
					Response linkResponse = getClient().invocation(
							response.getLink("next")).get();
					try {
						validateResponse(Response.Status.OK.getStatusCode(),
								linkResponse);
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
					shipments = linkResponse.readEntity(ClientShipmentDtoJaxbList.class);
					numShipments += shipments.getShipments().size();
					response.close();
					response = linkResponse;
				}
				return numShipments;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
	}


	@Override
	public void changeStatus(Long shipmentId, String status)
			throws InputValidationException, InstanceNotFoundException, ClientShipmentStatusException {

		try (Client client = getClient()){
			WebTarget wt = client.target(endpointAddress).path("shipment/{id}").resolveTemplate("id", shipmentId);
			Form form = new Form();
			form.param("status", status.toString());

			try (Response response = wt.request().accept(this.getMediaType()).post(Entity.form(form))){
				validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
			} catch (InputValidationException | ClientShipmentStatusException | InstanceNotFoundException ex) {
				throw ex;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void validateResponse(int expectedStatusCode, Response response)
			throws InstanceNotFoundException, ClientCustomerNoEmptyException, InputValidationException, ClientShipmentStatusException
	{

		Response.Status statusCode  = Response.Status.fromStatusCode(response.getStatus());

		/* Success? */
		if (statusCode.getStatusCode() == expectedStatusCode) {
			return;
		}

		String contentType = response.getMediaType() != null ? response.getMediaType().toString() : null;
		boolean expectedContentType = this.getMediaType().toString().equalsIgnoreCase(contentType);

		if (!expectedContentType && (statusCode != Response.Status.NO_CONTENT)) {
			throw new RuntimeException("HTTP error; status code = " + statusCode);
		}

		switch (statusCode) {
			case NOT_FOUND: {
				ClientInstanceNotFoundExceptionDtoJaxb exDto = response.readEntity(ClientInstanceNotFoundExceptionDtoJaxb.class);
				throw JaxbExceptionConversor.toInstanceNotFoundException(exDto);
			}
			case BAD_REQUEST: {
				ClientInputValidationExceptionDtoJaxb exDto = response.readEntity(ClientInputValidationExceptionDtoJaxb.class);
				throw JaxbExceptionConversor.toInputValidationException(exDto);
			}
			case FORBIDDEN: {
				ExceptionDtoJaxb exDto = response
						.readEntity(ExceptionDtoJaxb.class);
				if (exDto.getErrorType().equals("CustomerNoEmpty")) {
					throw JaxbExceptionConversor.toCustomerNoEmptyException(exDto);
				}
				if (exDto.getErrorType().equals("ShipmentStatusException")) {
					throw JaxbExceptionConversor.toShipmentStatusException(exDto);
				}
				else {
					throw new RuntimeException("HTTP error; status code = "
							+ statusCode + " errorType = " + exDto.getErrorType());
				}
			}
			default:
				throw new RuntimeException("HTTP error; status code = " + statusCode);
		}
	}


}
