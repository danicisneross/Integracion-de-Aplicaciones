package es.udc.rs.wscontrib.crm.service;

public class CrmExceptionInfo {

	private String message;

	public CrmExceptionInfo() {
	}

	public CrmExceptionInfo(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
