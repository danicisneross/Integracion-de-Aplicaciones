package es.udc.rs.deliveries.client.ui;

import es.udc.rs.deliveries.client.service.ClientDeliveryService;
import es.udc.rs.deliveries.client.service.ClientDeliveryServiceFactory;
import es.udc.rs.deliveries.client.service.rest.DtosClient.*;
import es.udc.rs.deliveries.client.service.rest.RestClientDeliveryService;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.net.URI;
import java.util.List;

public class DeliveryServiceClient {

	public static void main(String[] args) {

		if (args.length == 0) {
			printUsageAndExit();
		}
		ClientDeliveryService clientDeliveryService = ClientDeliveryServiceFactory.getService();
		if ("-addCustomer".equalsIgnoreCase(args[0])) {
			validateArgs(args, 4, new int[] {});

			// [-addCustomer] DeliveryServiceClient -addCustomer <name> <Cif> <address>

			try {

				ClientCustomerDto customerId = clientDeliveryService.addCustomer(args[1],args[2],args[3]);
				System.out.println("Customer " + customerId.getCustomerId() + " " + "created sucessfully");
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-deleteCustomer".equalsIgnoreCase(args[0])) {
			validateArgs(args, 2, new int[] { 1 });

			// [-deleteCustomer] ClientDeliveryServiceClient -deleteCustomer <customerId>

			try {
				clientDeliveryService.removeCustomer(Long.parseLong(args[1]));
				System.out.println("Customer with id " + args[1] + " removed sucessfully");
			} catch (NumberFormatException | InstanceNotFoundException | ClientCustomerNoEmptyException ex) {
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		}else if ("-findByCustomer".equalsIgnoreCase(args[0])) {
			validateArgs(args, 3, new int[]{ 1 });
			// [-findByCustomer] DeliveryServiceClient -findByCustomer <customerId> <status>
			try {
				String status = args[2].equals("ANY") ? null : args[2];
				Long customerId = Long.parseLong(args[1]);
				ShipmentListInterval shipments = clientDeliveryService.findShipments(customerId,status);
				System.out.println("\n*** Shipment list interval***\n");
				System.out.println(shipments);
				URI nextIntervalUri = shipments.getNextIntervalUri();
				while (nextIntervalUri != null) {
					shipments = ((RestClientDeliveryService)clientDeliveryService).findShipmentsByUri(nextIntervalUri);
					System.out.println("\n*** Shipment list interval***\n");
					System.out.println(shipments);
					nextIntervalUri = shipments.getNextIntervalUri();
				}
				System.out.println("\n*** Customer: " + customerId +
						"\n    Number of shipments: "
						+ ((RestClientDeliveryService)clientDeliveryService).getNumberOfShipments(customerId, status));
			} catch (NumberFormatException | InputValidationException ex) {
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		} else if ("-changeState".equalsIgnoreCase(args[0])) {
			validateArgs(args, 3, new int[]{ 1 });

			// [-changeState] DeliveryServiceClient -changeState <shipmentId> <state>

			try {
				clientDeliveryService.changeStatus(Long.parseLong(args[1]), args[2]);
				System.out.println("State of shipment " + args[1] + " changed succesfully");
			} catch (ClientShipmentStatusException | InstanceNotFoundException | InputValidationException e) {
				e.printStackTrace(System.err);
            }  catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
        }else{
			printUsageAndExit();
		}
	}

	public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
		if (expectedArgs != args.length) {
			printUsageAndExit();
		}
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	public static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	public static void printUsage() {
		System.err.println("Usage:\n" +
				"    [-addCustomer]    DeliveryServiceClient -addCustomer <name> <Cif> <address>\n" +
				"    [-deleteCustomer] DeliveryServiceClient -deleteCustomer <customerId>\n" +
				"    [-findByCustomer] DeliveryServiceClient -findByCustomer <customerId> <status>\n" +
				"    [-changeState] DeliveryServiceClient -changeState <shipmentId> <state> \n" +
				"...\n");
	}
}
