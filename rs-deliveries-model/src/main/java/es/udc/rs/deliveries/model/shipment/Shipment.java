package es.udc.rs.deliveries.model.shipment;

import java.time.LocalDateTime;
import java.util.Objects;

public class Shipment {

    private Long shipmentId;
    private Long customerId;
    private Long packageReference;
    private String address;
    private ShipmentStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime maxDeliveryDate;
    private LocalDateTime deliveryDate;

	public Shipment(Long shipmentId, Long customerId, Long packageReference, String address, ShipmentStatus status,
			LocalDateTime creationDate, LocalDateTime maxDeliveryDate) {
		super();
		this.shipmentId = shipmentId;
		this.customerId = customerId;
		this.packageReference = packageReference;
		this.address = address;
		this.creationDate = creationDate;
		this.maxDeliveryDate = maxDeliveryDate;
		if (status == null ){
			this.status = ShipmentStatus.PENDING;
		} else {
			this.status = status;
		}
	}

	public Shipment(Long customerId, Long packageReference, String address, ShipmentStatus status,
					LocalDateTime creationDate, LocalDateTime maxDeliveryDate) {
		super();
		this.customerId = customerId;
		this.packageReference = packageReference;
		this.address = address;
		this.creationDate = creationDate;
		this.maxDeliveryDate = maxDeliveryDate;
		if (status == null ){
			this.status = ShipmentStatus.PENDING;
		} else {
			this.status = status;
		}
	}

	public Shipment(Shipment shipment) {
		this(shipment.shipmentId, shipment.customerId, shipment.packageReference, shipment.address,
				shipment.status, shipment.creationDate, shipment.maxDeliveryDate, shipment.getDeliveryDate());
	}

	public Shipment(Long shipmentId, Long customerId, Long packageReference, String address, ShipmentStatus status,
					LocalDateTime creationDate, LocalDateTime maxDeliveryDate, LocalDateTime deliveryDate) {
		this.shipmentId = shipmentId;
		this.customerId = customerId;
		this.packageReference = packageReference;
		this.address = address;
		this.creationDate = creationDate;
		this.maxDeliveryDate = maxDeliveryDate;
		if (status == null ){
			this.status = ShipmentStatus.PENDING;
		} else {
			this.status = status;
		}
		this.deliveryDate = deliveryDate;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPackageReference() {
		return packageReference;
	}

	public void setPackageReference(Long packageReference) {
		this.packageReference = packageReference;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ShipmentStatus getStatus() {
		return status;
	}

	public void setStatus(ShipmentStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getMaxDeliveryDate() {
		return maxDeliveryDate;
	}

	public void setMaxDeliveryDate(LocalDateTime maxDeliveryDate) {
		this.maxDeliveryDate = maxDeliveryDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shipment other = (Shipment) obj;
		if (shipmentId == null) {
			if (other.shipmentId != null)
				return false;
		} else if (!shipmentId.equals(other.shipmentId))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (packageReference == null) {
			if (other.packageReference != null)
				return false;
		} else if (!packageReference.equals(other.packageReference))
			return false;
		if (maxDeliveryDate == null) {
			if (other.maxDeliveryDate != null)
				return false;
		} else if (!maxDeliveryDate.equals(other.maxDeliveryDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shipmentId == null) ? 0 : shipmentId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((packageReference == null) ? 0 : packageReference.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((maxDeliveryDate == null) ? 0 : maxDeliveryDate.hashCode());
		result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		return result;
	}
}