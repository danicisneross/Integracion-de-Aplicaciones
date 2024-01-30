package es.udc.rs.deliveries.client.service.rest.DtosClient;

public class ClientCustomerNoEmptyException extends Exception{

    private Long customerId;
    public ClientCustomerNoEmptyException(Long customerId) {
        super("The customer " +  customerId + " has elements in the shipment list, it needs to be empty to delete it" );
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
