package es.udc.rs.wscontrib.crm.service;

import java.util.Calendar;
import java.util.List;

public class Order {
	private Long orderId;
	private String customerId;
	private String address;
	private String zipCode;
	private Calendar creationDate;
	private List<OrderLine> orderLines;
	
	public Order() {
	}

	public Order(Long orderId, String customerId, String address,
			String zipCode, List<OrderLine> orderLines) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderLines = orderLines;
		this.address = address;
		this.zipCode = zipCode;
		this.creationDate = Calendar.getInstance();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerId=" + customerId
				+ ", address=" + address + ", zipCode=" + zipCode
				+ ", creationDate=" + (creationDate != null?creationDate.getTime():creationDate) + ", orderLines="
				+ orderLines + "]";
	}

}
