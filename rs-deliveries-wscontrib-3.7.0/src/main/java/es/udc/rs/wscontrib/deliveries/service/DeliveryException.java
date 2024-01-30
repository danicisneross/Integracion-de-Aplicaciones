package es.udc.rs.wscontrib.deliveries.service;


import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "DeliveryException", targetNamespace = "http://rs.udc.es/delivery")
public class DeliveryException extends Exception {

    private DeliveryExceptionInfo faultInfo;

    public DeliveryException(DeliveryExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public DeliveryExceptionInfo getFaultInfo() {
        return faultInfo;
    }

}
