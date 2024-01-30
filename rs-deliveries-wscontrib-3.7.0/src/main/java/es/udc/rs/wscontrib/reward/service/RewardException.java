package es.udc.rs.wscontrib.reward.service;

import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "RewardException", targetNamespace = "http://rs.udc.es/reward")
public class RewardException extends Exception {

	private RewardExceptionInfo faultInfo;

	public RewardException(RewardExceptionInfo faultInfo) {
		this.faultInfo = faultInfo;
	}

	public RewardExceptionInfo getFaultInfo() {
		return faultInfo;
	}

}
