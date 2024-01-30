package es.udc.rs.deliveries.jaxrs.dto;

import es.udc.rs.deliveries.model.customer.Customer;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "customer")
@XmlType(name="customerInfo", propOrder = {"customerId", "name", "Cif", "address", "links"})
public class CustomerDtoJaxb {

    @XmlAttribute(name = "customerId", required = true)
    private Long customerId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String Cif;
    @XmlElement(required = true)
    private String address;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;

    public CustomerDtoJaxb(){}
    public CustomerDtoJaxb(Long customerId, String name, String cif, String address, List<AtomLinkDtoJaxb> links) {
        this.customerId = customerId;
        this.name = name;
        this.Cif = cif;
        this.address = address;
        this.links = links;
    }
    public CustomerDtoJaxb(Long customerId, String name, String cif, String address) {
        this.customerId = customerId;
        this.name = name;
        this.Cif = cif;
        this.address = address;
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

    public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "CustomerDtoJaxb [customerId=" + customerId + ", Nombre=" + name
                + ", Cif=" + Cif
                + ", Address=" + address + "]";
    }

}
