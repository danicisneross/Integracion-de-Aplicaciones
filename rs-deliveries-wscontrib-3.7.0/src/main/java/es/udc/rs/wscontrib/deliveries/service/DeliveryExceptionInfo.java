package es.udc.rs.wscontrib.deliveries.service;

public class DeliveryExceptionInfo {

	private String message;

	public DeliveryExceptionInfo() {
	}

	public DeliveryExceptionInfo(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
