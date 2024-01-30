package es.udc.rs.deliveries.model.exceptions;

public class CustomerNoEmpty extends Exception{

    private Long customerId;
    public CustomerNoEmpty(Long customerId) {
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



