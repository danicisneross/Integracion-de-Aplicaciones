package es.udc.rs.wscontrib.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "CrmProvider", serviceName = "CrmProviderService", targetNamespace = "http://rs.udc.es/crm")
public class CrmService {

	private Map<Long, Order> orders;

	public CrmService() {
	}

	@PostConstruct()
	private void init() {

		orders = new HashMap<Long, Order>();
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		orderLines.add(new OrderLine(1L, "Product 1", "Product description 1",
				1L, 10.0));
		orderLines.add(new OrderLine(2L, "Product 2", "Product description 2",
				2L, 20.0));
		orderLines.add(new OrderLine(3L, "Product 3", "Product description 3",
				1L, 30.0));
		orderLines.add(new OrderLine(4L, "Product 4", "Product description 4",
				3L, 40.0));
		Order order = new Order(1L, "11838555H", "Street 1", "C0001",
				orderLines);
		orders.put(order.getOrderId(), order);

		orderLines = new ArrayList<OrderLine>();
		orderLines.add(new OrderLine(1L, "Product 1", "Product description 1",
				1L, 10.0));
		orderLines.add(new OrderLine(30L, "Product 30", "Product description 30",
				3L, 20.0));
		orderLines.add(new OrderLine(5L, "Product 5", "Product description 5",
				1L, 30.0));
		order = new Order(2L, "11838555H", "Street 1", "C0001", orderLines);
		orders.put(order.getOrderId(), order);

		orderLines = new ArrayList<OrderLine>();
		orderLines.add(new OrderLine(1L, "Product 1", "Product description 1",
				1L, 10.0));
		orderLines.add(new OrderLine(4L, "Product 4", "Product description 4",
				2L, 20.0));
		orderLines.add(new OrderLine(5L, "Product 5", "Product description 5",
				1L, 30.0));
		order = new Order(3L, "41262338Q", "Street 2", "C0002", orderLines);
		orders.put(order.getOrderId(), order);

		orderLines = new ArrayList<OrderLine>();
		orderLines.add(new OrderLine(1L, "Product 1", "Product description 1",
				5L, 10.0));
		orderLines.add(new OrderLine(20L, "Product 2", "Product description 20",
				1L, 20.0));
		order = new Order(4L, "08724415D", "Street 3", "C0003", orderLines);
		orders.put(order.getOrderId(), order);

		orderLines = new ArrayList<OrderLine>();
		orderLines.add(new OrderLine(1L, "Product 1", "Product description 1",
				2L, 10.0));
		order = new Order(5L, "81191750W", "Street 4", "C0004", orderLines);
		orders.put(order.getOrderId(), order);

	}

	@WebMethod(operationName = "getOrder")
	public Order getOrder(@WebParam(name = "orderId") Long orderId)
			throws CrmException {
		Order order = orders.get(orderId);
		if (order == null) {
			throw new CrmException(new CrmExceptionInfo("Order '" + orderId
					+ "' not found"));
		}
		return order;
	}

}
