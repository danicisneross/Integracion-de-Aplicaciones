package es.udc.rs.wscontrib.reward.service;

public class RewardExceptionInfo {

	private String message;

	public RewardExceptionInfo() {
	}

	public RewardExceptionInfo(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
