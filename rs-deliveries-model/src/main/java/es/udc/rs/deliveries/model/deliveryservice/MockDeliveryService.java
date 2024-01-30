package es.udc.rs.deliveries.model.deliveryservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.exceptions.ShipmentNotPending;
import es.udc.rs.deliveries.model.exceptions.ShipmentStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;
import es.udc.rs.deliveries.model.exceptions.CustomerNoEmpty;

public class MockDeliveryService implements DeliveryService {


	private static Map<Long,Customer> customersMap = new LinkedHashMap<Long,Customer>();
	private Map<Long,Shipment> shipmentsMap = new LinkedHashMap<Long,Shipment>();
	private Map<Long,List<Shipment>> shipmentsByUserMap = new LinkedHashMap<Long,List<Shipment>>();

	private long lastCustomerId = 0;
	private long lastShippingId = 0;

	private synchronized long getNextCustomertId() {
		return ++lastCustomerId;
	}
	
	private synchronized long getNextShippingId() {
		return ++lastShippingId;
	}


	@Override
	public Customer addCustomer(String name, String Cif, String address)
			throws InputValidationException {
		//Hacer check del address
		PropertyValidator.validateMandatoryString("address", address);
		PropertyValidator.validateMandatoryString("Cif", Cif);
		PropertyValidator.validateMandatoryString("name", name);

		LocalDateTime cd = LocalDateTime.now().withNano(0);
		Long customerId = getNextCustomertId();
		Customer newCustomer = new Customer(customerId,name,Cif,address,cd);

		validateCustomer(newCustomer);
		customersMap.put(newCustomer.getCustomerId(), newCustomer);
		return new Customer(customerId,name,Cif,address,cd);
	}
	@Override
	public void deleteCustomer(Long customerId) throws InstanceNotFoundException, CustomerNoEmpty {
		Customer customer = customersMap.get(customerId);

		if (customer == null) {
			throw new InstanceNotFoundException(customerId, Customer.class.getName());
		}

		List<Shipment> customerShipments = shipmentsByUserMap.get(customerId);

		if (customerShipments!=null) {
			throw new CustomerNoEmpty(customerId);
		}
		customersMap.remove(customerId);
	}
	@Override
	public List<Customer> findCustomerKeyword(String KeyWord){
		List<Customer> findCustomerK = new ArrayList<Customer>();
		if (KeyWord==null){
			return findCustomerK;
		}
		String searchKeyword = KeyWord.toLowerCase();
		for (Customer c : customersMap.values()) {
			if (c.getName().toLowerCase().contains(searchKeyword)) {
				findCustomerK.add(new Customer(c));
			}
		}
		if (findCustomerK.isEmpty()){
			return findCustomerK;
		}

		return findCustomerK;

	}
	@Override
	public void updateCustomer(Customer customer)
			throws InputValidationException, InstanceNotFoundException{
		validateCustomer(customer);
		Customer customer2 = customersMap.get(customer.getCustomerId());
		if(customer2 == null) {
			throw new InstanceNotFoundException(customer.getCustomerId(),
					Customer.class.getName());
		}
		customer2.setName(customer.getName());
		customer2.setCif(customer.getCif());
		customer2.setAddress(customer.getAddress());
	}

	@Override
	public Customer findCustomer(Long customerId)
			throws InstanceNotFoundException, InputValidationException{
		if (customerId == null){
			throw new InputValidationException("The ID can´t be null");
		}
		Customer customer = customersMap.get(customerId);
		if(customer == null){
			throw new InstanceNotFoundException(customerId, Customer.class.getName());
		}
		return new Customer(customer);
	}


	@Override
	public Shipment addShipment(Long customerId, Long packageReference, String address)
			throws InstanceNotFoundException, InputValidationException {
		PropertyValidator.validateLong("customerId", customerId, 0, 10000);
		//Si el cliente no existe
		if (customersMap.get(customerId) == null) {
			throw new InstanceNotFoundException(customerId, Customer.class.getName());
		}
		//El address no puede ser nulo ni vacío
		PropertyValidator.validateMandatoryString("address", address);
		//PropertyValidator.validateMandatoryString
		PropertyValidator.validateLong("packageReference", packageReference, 0, 10000);
		LocalDateTime cd = LocalDateTime.now().withNano(0);
		LocalDateTime mdd = cd.plusHours(48).withNano(0);
		Long shipmentId = getNextShippingId();
		Shipment newShipment = new Shipment(shipmentId, customerId, packageReference,
				address, ShipmentStatus.PENDING, cd, mdd);
		shipmentsMap.put(shipmentId, newShipment);
		//Recuperamos la lista de envios del cliente
		List<Shipment> customerShipments = shipmentsByUserMap.get(customerId);
		//No tiene ningún envío
		if (customerShipments == null) {
			List<Shipment> newShipmentList = new ArrayList<Shipment>();
			newShipmentList.add(newShipment);
			shipmentsByUserMap.put(customerId, newShipmentList);
		} else {
			customerShipments.add(newShipment);
			shipmentsByUserMap.replace(customerId, customerShipments);
		}
		return newShipment;
	}

	@Override
	public Shipment updateShipment(Long shipmentId, ShipmentStatus status)
			throws InputValidationException, InstanceNotFoundException, ShipmentStatusException {
		if (shipmentId == null){
			throw new InputValidationException("The ID can´t be null");
		}
		if (status == null){
			throw new InputValidationException("The status can´t be null");
		}
		//PENDING->SENT->DELIVERED/REJECTED
		ShipmentStatus previousState;
		if (status==ShipmentStatus.SENT){
			previousState=ShipmentStatus.PENDING;
		} else if (status==ShipmentStatus.DELIVERED || status==ShipmentStatus.REJECTED){
			previousState=ShipmentStatus.SENT;
		} else {
			throw new ShipmentStatusException("Unable to update shipment status");
		}
		Shipment shipment = shipmentsMap.get(shipmentId);

		if (shipment == null){
			throw new InstanceNotFoundException(shipmentId, Shipment.class.getName());
		}

		Long customerId = shipment.getCustomerId();
		List<Shipment> customerShipments = shipmentsByUserMap.get(customerId);

		if (previousState == shipment.getStatus()){
			shipment.setStatus(status);
			shipmentsMap.replace(shipmentId, shipment);
			int i = 0;
			for(Shipment shipment1 : customerShipments){
				if(shipment1.getShipmentId() == shipmentId){
					customerShipments.set(i, shipment1);
					shipmentsByUserMap.replace(customerId, customerShipments);
					break;
				}
				i++;
			}

			if(status == ShipmentStatus.DELIVERED){
				shipment.setDeliveryDate(LocalDateTime.now().withNano(0));
			}
		} else {
			throw new ShipmentStatusException("Unable to update shipment status");
		}
		return new Shipment(shipment);
	}

	@Override
	public void cancelShipment(Long shipmentId)
			throws InstanceNotFoundException, ShipmentNotPending {
		Shipment shipment = shipmentsMap.get(shipmentId);

		if(shipment == null) {
			throw new InstanceNotFoundException(shipmentId, Shipment.class.getName());
		} else if(!shipment.getStatus().equals(ShipmentStatus.PENDING)) {
			throw new ShipmentNotPending(shipmentId);
		} else{
			shipment.setStatus(ShipmentStatus.CANCELLED);
		}
	}

	@Override
	public Shipment findShipment(Long shipmentId)
			throws InstanceNotFoundException, InputValidationException{
		if (shipmentId == null){
			throw new InputValidationException("The ID can´t be null");
		}
		Shipment shipment = shipmentsMap.get(shipmentId);

		if (shipment==null){
			throw new InstanceNotFoundException(shipmentId,Shipment.class.getName());
		}
		return new Shipment(shipment);
	}

	@Override
	public List<Shipment> findShipments(Long customerId, ShipmentStatus status/*optional*/,
										int startIndex, int count)
			throws InputValidationException {
		List<Shipment> shipments = shipmentsByUserMap.get(customerId);
		//Devolvemos una copia de los datos
		List<Shipment> customerShipments = shipments == null ? null : new ArrayList<Shipment>(shipments);
		if (customerShipments == null){
			return new ArrayList<Shipment>();
		}
		if (status != null){
			customerShipments = customerShipments.stream()
					.filter(shipment -> shipment.getStatus().equals(status)).
					collect(Collectors.toList());
		}

		//Si count < 0 -> InputValidationException
		PropertyValidator.validateNotNegativeLong("count", count);
		//Si startIndex < 0 -> InputValidationException
		PropertyValidator.validateNotNegativeLong("startIndex", startIndex);

		int fromIndex = (customerShipments.size() > startIndex) ? startIndex
				: customerShipments.size();
		int toIndex = (customerShipments.size() >= startIndex + count) ? startIndex
				+ count : customerShipments.size();

		//subList(2,6) devuelve los elementos 2, 3, 4 y 5
		//customerShipments ya es una copia de los datos originales, por lo que no hace falta hacer un new
		return customerShipments.subList(fromIndex, toIndex);
	}

	private static void validateCustomer(Customer customer) throws InputValidationException{
		if (customer == null){
			throw new InputValidationException("Customer can´t be null.");
		}
		PropertyValidator.validateMandatoryString("name", customer.getName());
		PropertyValidator.validateMandatoryString("Cif", customer.getCif());
		PropertyValidator.validateMandatoryString("address", customer.getAddress());
	}

	public void clear() {
		customersMap.clear();
		shipmentsMap.clear();
		shipmentsByUserMap.clear();

	}
}
