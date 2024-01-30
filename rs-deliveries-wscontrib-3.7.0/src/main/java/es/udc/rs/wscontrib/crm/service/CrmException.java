package es.udc.rs.wscontrib.crm.service;

import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "CrmException", targetNamespace = "http://rs.udc.es/crm")
public class CrmException extends Exception {

	private CrmExceptionInfo faultInfo;

	public CrmException(CrmExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public CrmExceptionInfo getFaultInfo() {
		return faultInfo;
	}

}
