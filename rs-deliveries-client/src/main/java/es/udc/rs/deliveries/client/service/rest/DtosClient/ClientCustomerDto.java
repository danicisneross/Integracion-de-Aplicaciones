package es.udc.rs.deliveries.client.service.rest.DtosClient;

import java.net.URI;
import java.time.LocalDateTime;

public class ClientCustomerDto {

    private Long customerId;

    private String name;

    private String Cif;

    private String address;

    private LocalDateTime creationDate;
    private URI selfUri;
    private URI shipmentsUri;
    private URI deleteCustomerUri;

    public ClientCustomerDto(){}
    public ClientCustomerDto(Long customerId, String name, String Cif, String address,
                             LocalDateTime creationDate, URI selfUri, URI shipmentsUri, URI deleteCustomerUri) {
        this.customerId = customerId;
        this.name = name;
        this.Cif = Cif;
        this.address = address;
        this.creationDate = creationDate;
        this.selfUri = selfUri;
        this.shipmentsUri = shipmentsUri;
        this.deleteCustomerUri = deleteCustomerUri;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return Cif;
    }

    public void setCif(String cif) {
        Cif = cif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public URI getselfUri() {
        return selfUri;
    }

    public void setselfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getshipmentsUri() {
        return shipmentsUri;
    }

    public void setshipmentsUri(URI shipmentsUri) {
        this.shipmentsUri = shipmentsUri;
    }

    public URI getDeleteCustomerUri() {
        return deleteCustomerUri;
    }

    public void setDeleteCustomerUri(URI deleteCustomerUri) {
        this.deleteCustomerUri = deleteCustomerUri;
    }

    @Override
    public String toString() {
        return "ClientCustomerDto [customerId=" + customerId + ", Nombre=" + name
                + ", Cif=" + Cif
                + ", Address=" + address + "]";
    }
}
