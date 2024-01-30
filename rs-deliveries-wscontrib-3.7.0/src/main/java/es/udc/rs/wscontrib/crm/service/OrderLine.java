package es.udc.rs.wscontrib.crm.service;

public class OrderLine {
	private Long productId;
	private String name;
	private String description;
	private Long amount;
	private Double price;

	public OrderLine() {
	}

	public OrderLine(Long productId, String name, String description,
			Long amount, Double price) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderLine [productId=" + productId + ", name=" + name
				+ ", description=" + description + ", price=" + price + "]";
	}

}
