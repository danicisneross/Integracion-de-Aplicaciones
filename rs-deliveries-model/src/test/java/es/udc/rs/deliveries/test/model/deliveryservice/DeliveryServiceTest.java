package es.udc.rs.deliveries.test.model.deliveryservice;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryService;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.deliveryservice.MockDeliveryService;
import es.udc.rs.deliveries.model.exceptions.CustomerNoEmpty;
import es.udc.rs.deliveries.model.exceptions.ShipmentNotPending;
import es.udc.rs.deliveries.model.exceptions.ShipmentStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryServiceTest {

    private static DeliveryService deliveryService = null;

    @BeforeAll
    public static void init() {
        deliveryService = DeliveryServiceFactory.getService();

    }
    private Customer getValidCustomer(){
        return new Customer("Pablo","12456533B","Calle la marquesina");
    }

    private Customer getInvalidCustomer(){
        return new Customer("Pablo","12456533B",null);
    }

    //Tiene que generar un Id automatico
    public Shipment getValidShipment(){
        Customer customer = getValidCustomer();
        return new Shipment(getValidCustomer().getCustomerId(), 1L ,"Calle Susanita", ShipmentStatus.PENDING,
                LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0).plusHours(48));
    }

    @Test
    public void testAddCustomerAndFindCustomerByKeyword() throws InputValidationException {
        Customer cliente = getValidCustomer();
        Customer clienteNuevo;
        List<Customer> listado = null;

        try {
            clienteNuevo = deliveryService.addCustomer(cliente.getName(), cliente.getCif(), cliente.getAddress());
            listado = deliveryService.findCustomerKeyword("Pablo");
            assertEquals(listado.get(0).getCustomerId(), clienteNuevo.getCustomerId());
            assertEquals(listado.get(0).getCif(), clienteNuevo.getCif());
            assertEquals(listado.get(0).getAddress(), clienteNuevo.getAddress());
        } finally {
            ((MockDeliveryService)deliveryService).clear();
        }


    }
    @Test
    public void testDeleteCustomer() throws InputValidationException, CustomerNoEmpty, InstanceNotFoundException {
        Customer cliente = getValidCustomer();
        Customer clienteNuevo;
        List<Customer> listadoVacio= new ArrayList<Customer>();

        try {
            clienteNuevo = deliveryService.addCustomer(cliente.getName(), cliente.getCif(), cliente.getAddress());
            deliveryService.deleteCustomer(clienteNuevo.getCustomerId());

            assertEquals(deliveryService.findCustomerKeyword("Pablo"), listadoVacio);

        } finally {
            ((MockDeliveryService)deliveryService).clear();
        }


    }
    @Test
    public void testAddInvalidCustomer(){
        assertThrows(InputValidationException.class, () -> {
            Customer cliente = getInvalidCustomer();
            deliveryService.addCustomer(cliente.getName(),cliente.getCif(),cliente.getAddress());
        });

        ((MockDeliveryService)deliveryService).clear();
    }

    @Test
    public void testFindInvalidCustomerByKeyword() throws InputValidationException {

        Customer cliente = getValidCustomer();
        Customer buscado;
        List<Customer> listado = null;
        List<Customer> vacio = null;
        buscado =deliveryService.addCustomer(cliente.getName(),cliente.getCif(),cliente.getAddress());
        try{
            listado = deliveryService.findCustomerKeyword("Manolo");
            assertTrue(listado.isEmpty());
        } finally {
            ((MockDeliveryService)deliveryService).clear();
        }
    }

    @Test
    public void testDeleteCustomerWithShipments() throws  InputValidationException {
        Customer cliente = getValidCustomer();
        Customer clienteBorrar = deliveryService.addCustomer(cliente.getName(), cliente.getCif(), cliente.getAddress());
        assertThrows(CustomerNoEmpty.class, () -> {
            long achoPr = 0;
            //List<Shipment> al = new ArrayList<Shipment>();
            deliveryService.addShipment(clienteBorrar.getCustomerId(), ++achoPr, cliente.getAddress());
            //al.add(deliveryService.findShipment(shipmentId));
            deliveryService.deleteCustomer(clienteBorrar.getCustomerId());
            ((MockDeliveryService)deliveryService).clear();
        });
        //Clear data
        ((MockDeliveryService)deliveryService).clear();
    }
    @Test
    public void testCancelShipment() throws InputValidationException, InstanceNotFoundException,
            ShipmentStatusException, ShipmentNotPending {
        assertNotNull(deliveryService);
        Customer c = getValidCustomer();
        try{
            Customer cliente = deliveryService.addCustomer(c.getName(), c.getCif(), c.getAddress());
            Shipment shipment = deliveryService.addShipment(cliente.getCustomerId(), 1234L, c.getAddress());

            //Se cancela el envío:
            deliveryService.cancelShipment(shipment.getShipmentId());
            assertEquals(shipment.getStatus(), ShipmentStatus.CANCELLED);

            //Se trata de cancelar un envío que no existe
            assertThrows(InstanceNotFoundException.class, () -> {
                deliveryService.cancelShipment(9999L);
            });
            Shipment shipmentSent = deliveryService.addShipment(cliente.getCustomerId(), 1234L, c.getAddress());
            deliveryService.updateShipment(shipmentSent.getShipmentId(), ShipmentStatus.SENT);

            //Se trata de cancelar un envío que no está en estado PENDING
            assertThrows(ShipmentNotPending.class, () -> {
                deliveryService.cancelShipment(shipmentSent.getShipmentId());
            });
        }finally {
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        }
    }
    @Test
    public void testAddShipmentAndFindShipments() throws InputValidationException,
            InstanceNotFoundException, ShipmentNotPending, ShipmentStatusException {
        assertNotNull(deliveryService);
        Customer c = getValidCustomer();
        Customer Cliente;
        try{
            Cliente = deliveryService.addCustomer(c.getName(), c.getCif(), c.getAddress());
            long achoPr = 0;
            List<Shipment> sl = new ArrayList<Shipment>();
            List<Shipment> al = new ArrayList<Shipment>();
            Shipment shipment = deliveryService.addShipment(Cliente.getCustomerId(), ++achoPr, c.getAddress());
            al.add(shipment);
            Long shipmentId = shipment.getShipmentId();
            while (achoPr < 100){
                al.add(deliveryService.addShipment(Cliente.getCustomerId(), ++achoPr, c.getAddress()));
            }
            //findShipments
            //Recupera los 100 envíos
            assertEquals(al, deliveryService.findShipments(Cliente.getCustomerId(), null, 0, 100));
            //Recupera los 100 envíos con un count superior al tamaño de la lista
            assertEquals(al, deliveryService.findShipments(Cliente.getCustomerId(), null, 0, 200));
            //Recupera los primeros 50 envíos con estado PENDING
            assertEquals(al.subList(0, 50), deliveryService.findShipments(Cliente.getCustomerId(), ShipmentStatus.PENDING, 0, 50));
            //Recupera los últimos 50 envíos con estado PENDING
            assertEquals(al.subList(50, 100), deliveryService.findShipments(Cliente.getCustomerId(), ShipmentStatus.PENDING, 50, 50));
            //Se devuelve el décimo envío
            assertEquals(al.get(9), deliveryService.findShipments(Cliente.getCustomerId(), null, 9, 1).get(0));
            //La búsqueda supera los límites
            assertEquals(new ArrayList<>(), deliveryService.findShipments(Cliente.getCustomerId(), null, 101, 10));

            deliveryService.cancelShipment(shipmentId+4);
            deliveryService.cancelShipment(shipmentId+24);
            deliveryService.cancelShipment(shipmentId+42);
            deliveryService.updateShipment(shipmentId+52, ShipmentStatus.SENT);
            deliveryService.updateShipment(shipmentId+54, ShipmentStatus.SENT);
            deliveryService.updateShipment(shipmentId+82, ShipmentStatus.SENT);
            deliveryService.updateShipment(shipmentId+99, ShipmentStatus.SENT);

            deliveryService.updateShipment(shipmentId+54, ShipmentStatus.DELIVERED);
            deliveryService.updateShipment(shipmentId+82, ShipmentStatus.REJECTED);
            deliveryService.updateShipment(shipmentId+99, ShipmentStatus.REJECTED);

            sl.add(deliveryService.findShipment(shipmentId+4));
            sl.add(deliveryService.findShipment(shipmentId+24));
            sl.add(deliveryService.findShipment(shipmentId+42));
            sl.add(deliveryService.findShipment(shipmentId+52));
            sl.add(deliveryService.findShipment(shipmentId+54));
            sl.add(deliveryService.findShipment(shipmentId+82));
            sl.add(deliveryService.findShipment(shipmentId+99));

            //Se van recuperando los 3 primeros envíos con estado CANCELLED de uno en uno
            assertEquals(sl.subList(0, 1), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.CANCELLED, 0, 1));
            assertEquals(sl.subList(1, 2), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.CANCELLED, 1, 1));
            assertEquals(sl.subList(2, 3), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.CANCELLED, 2, 6));
            //Se recuperan los envíos con estado SENT
            assertEquals(sl.get(3), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.SENT, 0, 10).get(0));
            //Se recuperan los envíos con estado DELIVERED
            assertEquals(sl.get(4), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.DELIVERED, 0, 10).get(0));
            //Se recuperan los envíos con estado REJECTED
            assertEquals(sl.subList(5, 7), deliveryService.findShipments(Cliente.getCustomerId(),
                    ShipmentStatus.REJECTED, 0, 10));
        }finally {
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        }
    }

    @Test
    public void testAddInvalidShipment() throws InputValidationException, CustomerNoEmpty, InstanceNotFoundException {
        assertNotNull(deliveryService);
        Customer c = getValidCustomer();
        Customer Cliente;
        Cliente = deliveryService.addCustomer(c.getName(), c.getCif(), c.getAddress());
        //El cliente no exite
        assertThrows(InstanceNotFoundException.class, () -> {
            deliveryService.addShipment(9999L, 1232L, c.getAddress());
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        });
        //El customerId no es válido
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addShipment(-1L, 1232L, c.getAddress());
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        });
        //El packageReference no es válido
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addShipment(Cliente.getCustomerId(), 10014L, c.getAddress());
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        });

        //Dirección nula
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addShipment(Cliente.getCustomerId(), 1232L, null);
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        });
        //Dirección vacía
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addShipment(Cliente.getCustomerId(), 1232L, "");
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        });
        deliveryService.deleteCustomer(Cliente.getCustomerId());
    }
    @Test
    public void testInvalidFindShipments() throws InputValidationException, InstanceNotFoundException, ShipmentNotPending {
        assertNotNull(deliveryService);
        Customer c = getValidCustomer();
        Customer Cliente;
        Cliente = deliveryService.addCustomer(c.getName(), c.getCif(), c.getAddress());
        try{
            deliveryService.addShipment(Cliente.getCustomerId(), 1234L, c.getAddress());
            deliveryService.addShipment(Cliente.getCustomerId(), 5678L, c.getAddress()).getShipmentId();
            //Hay 2 envíos asociados al cliente
            //Count es negativo
            assertThrows(InputValidationException.class, () -> {
                deliveryService.findShipments(Cliente.getCustomerId(),
                        null, 0, -1);
            });
            //startIndex es negativo
            assertThrows(InputValidationException.class, () -> {
                deliveryService.findShipments(Cliente.getCustomerId(),
                        null, -1, 3);
            });
        }finally {
            //Clear data
            ((MockDeliveryService)deliveryService).clear();
        }
    }
    @Test
    public void testUpdateCustomer() throws InputValidationException, InstanceNotFoundException, CustomerNoEmpty{
        //primero obtenemos el customer ya creado en el getvalid
        Customer validCustomer = getValidCustomer();
        Customer addedCustomer = null;
        try {
            addedCustomer = deliveryService.addCustomer(validCustomer.getName(),
                    validCustomer.getCif(), validCustomer.getAddress());
            Customer customerToUpdate = new Customer(addedCustomer.getCustomerId(), "dani", "537283793R", "Calle Venezuela", LocalDateTime.now());

            deliveryService.updateCustomer(customerToUpdate);

            Customer updateCustomer = deliveryService.findCustomer(addedCustomer.getCustomerId());

            customerToUpdate.setCreationDate(addedCustomer.getCreationDate());
            assertEquals(customerToUpdate, updateCustomer);

            //Comprobamos las exepciones
            Customer customerInvalid = new Customer(null,"dani", "537283793R", "Calle Venezuela", LocalDateTime.now());
            Customer customerInvalid2 = null;
            assertThrows(InstanceNotFoundException.class, () -> deliveryService.updateCustomer(customerInvalid));
            assertThrows(InputValidationException.class, ()-> deliveryService.updateCustomer(customerInvalid2));

        } finally { // eliminar este final
            if (addedCustomer != null){
                deliveryService.deleteCustomer(addedCustomer.getCustomerId());
            }
        }
    }
    @Test
    public void testFindCustomerById() throws InstanceNotFoundException, CustomerNoEmpty, InputValidationException {

        Customer customer = getValidCustomer();
        try{

            //Create Customer
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            Customer addedCustomer = deliveryService.addCustomer(customer.getName(),customer.getCif(),customer.getAddress());

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            //Find Customer
            Customer foundCustomer = deliveryService.findCustomer(addedCustomer.getCustomerId());

            assertEquals(addedCustomer, foundCustomer);
            assertTrue((foundCustomer.getCreationDate().compareTo(beforeCreationDate) >= 0)
                        && (foundCustomer.getCreationDate().compareTo(afterCreationDate) <= 0));

            //Comprobamos las exepciones
            assertThrows(InstanceNotFoundException.class, ()->deliveryService.findCustomer(71291L));
            assertThrows(InputValidationException.class, ()->deliveryService.findCustomer(null));

        } finally { // eliminar este final
            if (customer != null) {
                ((MockDeliveryService) deliveryService).clear();
            }
        }
    }

    @Test
    public void testUpdateShipment() throws InputValidationException, InstanceNotFoundException, ShipmentStatusException{
        assertNotNull(deliveryService);
        Customer customer = getValidCustomer();
        Shipment shipment= getValidShipment();
        try {
            Customer customer1 = deliveryService.addCustomer(customer.getName(), customer.getCif(),customer.getAddress());
            Shipment shipment1 = deliveryService.addShipment(customer1.getCustomerId(),shipment.getPackageReference(),shipment.getAddress());
            Shipment shipment2 = deliveryService.addShipment(customer1.getCustomerId(),shipment.getPackageReference(),shipment.getAddress());
            Shipment shipment3 = deliveryService.addShipment(customer1.getCustomerId(),shipment.getPackageReference(),shipment.getAddress());
            Shipment shipment4 = deliveryService.addShipment(customer1.getCustomerId(), shipment.getPackageReference(),shipment.getAddress());

            assertSame(shipment1.getStatus(), ShipmentStatus.PENDING);
            assertSame(shipment2.getStatus(), ShipmentStatus.PENDING);


            Shipment sendShipment = deliveryService.updateShipment(shipment1.getShipmentId(), ShipmentStatus.SENT);
            Shipment sendShipment2 = deliveryService.updateShipment(shipment2.getShipmentId(), ShipmentStatus.SENT);


            assertSame(sendShipment.getStatus(), ShipmentStatus.SENT);
            assertSame(sendShipment2.getStatus(), ShipmentStatus.SENT);
            shipment1 = deliveryService.findShipment(shipment1.getShipmentId());
            shipment2 = deliveryService.findShipment(shipment2.getShipmentId());
            assertSame(shipment1.getStatus(), ShipmentStatus.SENT);
            assertSame(shipment2.getStatus(), ShipmentStatus.SENT);

            // Miramos los dos estados
            // shipment1
            sendShipment = deliveryService.updateShipment(shipment1.getShipmentId(), ShipmentStatus.DELIVERED);
            assertSame(sendShipment.getStatus(), ShipmentStatus.DELIVERED);
            shipment1 = deliveryService.findShipment(shipment1.getShipmentId());
            assertSame(shipment1.getStatus(), ShipmentStatus.DELIVERED);

            //shipment2
            sendShipment = deliveryService.updateShipment(shipment2.getShipmentId(), ShipmentStatus.REJECTED);
            assertSame(sendShipment.getStatus(), ShipmentStatus.REJECTED);
            shipment2 = deliveryService.findShipment(shipment2.getShipmentId());
            assertSame(shipment2.getStatus(), ShipmentStatus.REJECTED);

            //Comprobamos las exepciones
            assertThrows(InputValidationException.class,()->deliveryService.updateShipment(null, shipment3.getStatus()));
            assertThrows(InputValidationException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), null));
            assertThrows(InstanceNotFoundException.class, ()-> deliveryService.updateShipment(10L, ShipmentStatus.SENT));

            /*Miramos las excepciones del status de un pedido */

            //PENDING -> DELIVERED/REJECTED
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.DELIVERED));
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.REJECTED));

            deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.SENT);

            //SENT -> PENDING
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.PENDING));

            //DELIVERED/REJECTED -> PENDING
            deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.DELIVERED);
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.PENDING));

            deliveryService.updateShipment(shipment4.getShipmentId(), ShipmentStatus.SENT);
            deliveryService.updateShipment(shipment4.getShipmentId(), ShipmentStatus.REJECTED);
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment4.getShipmentId(), ShipmentStatus.PENDING));

            //DELIVERED/REJECTED -> SENT
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment3.getShipmentId(), ShipmentStatus.SENT));
            assertThrows(ShipmentStatusException.class,()->deliveryService.updateShipment(shipment4.getShipmentId(), ShipmentStatus.SENT));

        } finally {
            if (shipment!= null){
                ((MockDeliveryService)deliveryService).clear();
            }
        }
    }
   @Test
    public void testFindShipmentById() throws InputValidationException, InstanceNotFoundException{
        assertNotNull(deliveryService);
        Customer customer = getValidCustomer();
        Shipment shipment = getValidShipment();
        try{

            //Create shipment
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            Customer customeradded = deliveryService.addCustomer(customer.getName(), customer.getCif(), customer.getAddress());
            Shipment addedShipment = deliveryService.addShipment(customeradded.getCustomerId(),shipment.getPackageReference(), shipment.getAddress());

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            //Find shipment
            Shipment foundShipment = deliveryService.findShipment(addedShipment.getShipmentId());

            assertEquals(addedShipment, foundShipment);
            assertTrue((foundShipment.getCreationDate().compareTo(beforeCreationDate) >= 0)
                    && (foundShipment.getCreationDate().compareTo(afterCreationDate) <= 0));

            //Comprobamos las exepciones
            assertThrows(InputValidationException.class,()->deliveryService.findShipment(null));
            assertThrows(InstanceNotFoundException.class,()->deliveryService.findShipment(27391372L));

        } finally {
            if (shipment != null){
                ((MockDeliveryService)deliveryService).clear();
            }
        }
    }
}
