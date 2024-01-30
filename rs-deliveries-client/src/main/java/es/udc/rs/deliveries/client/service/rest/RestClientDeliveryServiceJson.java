package es.udc.rs.deliveries.client.service.rest;

import jakarta.ws.rs.core.MediaType;

public class RestClientDeliveryServiceJson extends RestClientDeliveryService {

	@Override
	protected MediaType getMediaType() {
		return MediaType.APPLICATION_JSON_TYPE;
	}

}
