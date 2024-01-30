//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package es.udc.rs.deliveries.client.service.rest.dto;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.rs.deliveries.client.service.rest.dto package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Link_QNAME = new QName("http://www.w3.org/2005/Atom", "link");
    private static final QName _Customer_QNAME = new QName("http://ws.udc.es/ia02/xml", "customer");
    private static final QName _Error_QNAME = new QName("http://ws.udc.es/ia02/xml", "error");
    private static final QName _InputValidationException_QNAME = new QName("http://ws.udc.es/ia02/xml", "inputValidationException");
    private static final QName _InstanceNotFoundException_QNAME = new QName("http://ws.udc.es/ia02/xml", "instanceNotFoundException");
    private static final QName _Shipment_QNAME = new QName("http://ws.udc.es/ia02/xml", "shipment");
    private static final QName _ShipmentNotPending_QNAME = new QName("http://ws.udc.es/ia02/xml", "shipmentNotPending");
    private static final QName _Shipments_QNAME = new QName("http://ws.udc.es/ia02/xml", "shipments");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.rs.deliveries.client.service.rest.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AtomLinkDtoJaxb }
     * 
     * @return
     *     the new instance of {@link AtomLinkDtoJaxb }
     */
    public AtomLinkDtoJaxb createAtomLinkDtoJaxb() {
        return new AtomLinkDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientCustomerDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ClientCustomerDtoJaxb }
     */
    public ClientCustomerDtoJaxb createClientCustomerDtoJaxb() {
        return new ClientCustomerDtoJaxb();
    }

    /**
     * Create an instance of {@link ExceptionDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ExceptionDtoJaxb }
     */
    public ExceptionDtoJaxb createExceptionDtoJaxb() {
        return new ExceptionDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientInputValidationExceptionDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ClientInputValidationExceptionDtoJaxb }
     */
    public ClientInputValidationExceptionDtoJaxb createClientInputValidationExceptionDtoJaxb() {
        return new ClientInputValidationExceptionDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientInstanceNotFoundExceptionDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ClientInstanceNotFoundExceptionDtoJaxb }
     */
    public ClientInstanceNotFoundExceptionDtoJaxb createClientInstanceNotFoundExceptionDtoJaxb() {
        return new ClientInstanceNotFoundExceptionDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientShipmentDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ClientShipmentDtoJaxb }
     */
    public ClientShipmentDtoJaxb createClientShipmentDtoJaxb() {
        return new ClientShipmentDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientShipmentNotPendingExceptionDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ClientShipmentNotPendingExceptionDtoJaxb }
     */
    public ClientShipmentNotPendingExceptionDtoJaxb createClientShipmentNotPendingExceptionDtoJaxb() {
        return new ClientShipmentNotPendingExceptionDtoJaxb();
    }

    /**
     * Create an instance of {@link ClientShipmentDtoJaxbList }
     * 
     * @return
     *     the new instance of {@link ClientShipmentDtoJaxbList }
     */
    public ClientShipmentDtoJaxbList createClientShipmentDtoJaxbList() {
        return new ClientShipmentDtoJaxbList();
    }

    /**
     * Create an instance of {@link ExceptionParamDtoJaxb }
     * 
     * @return
     *     the new instance of {@link ExceptionParamDtoJaxb }
     */
    public ExceptionParamDtoJaxb createExceptionParamDtoJaxb() {
        return new ExceptionParamDtoJaxb();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomLinkDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AtomLinkDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "link")
    public JAXBElement<AtomLinkDtoJaxb> createLink(AtomLinkDtoJaxb value) {
        return new JAXBElement<>(_Link_QNAME, AtomLinkDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientCustomerDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientCustomerDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "customer")
    public JAXBElement<ClientCustomerDtoJaxb> createCustomer(ClientCustomerDtoJaxb value) {
        return new JAXBElement<>(_Customer_QNAME, ClientCustomerDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExceptionDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExceptionDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "error")
    public JAXBElement<ExceptionDtoJaxb> createError(ExceptionDtoJaxb value) {
        return new JAXBElement<>(_Error_QNAME, ExceptionDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientInputValidationExceptionDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientInputValidationExceptionDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "inputValidationException")
    public JAXBElement<ClientInputValidationExceptionDtoJaxb> createInputValidationException(ClientInputValidationExceptionDtoJaxb value) {
        return new JAXBElement<>(_InputValidationException_QNAME, ClientInputValidationExceptionDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientInstanceNotFoundExceptionDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientInstanceNotFoundExceptionDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "instanceNotFoundException")
    public JAXBElement<ClientInstanceNotFoundExceptionDtoJaxb> createInstanceNotFoundException(ClientInstanceNotFoundExceptionDtoJaxb value) {
        return new JAXBElement<>(_InstanceNotFoundException_QNAME, ClientInstanceNotFoundExceptionDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientShipmentDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientShipmentDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "shipment")
    public JAXBElement<ClientShipmentDtoJaxb> createShipment(ClientShipmentDtoJaxb value) {
        return new JAXBElement<>(_Shipment_QNAME, ClientShipmentDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientShipmentNotPendingExceptionDtoJaxb }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientShipmentNotPendingExceptionDtoJaxb }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "shipmentNotPending")
    public JAXBElement<ClientShipmentNotPendingExceptionDtoJaxb> createShipmentNotPending(ClientShipmentNotPendingExceptionDtoJaxb value) {
        return new JAXBElement<>(_ShipmentNotPending_QNAME, ClientShipmentNotPendingExceptionDtoJaxb.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClientShipmentDtoJaxbList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClientShipmentDtoJaxbList }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/ia02/xml", name = "shipments")
    public JAXBElement<ClientShipmentDtoJaxbList> createShipments(ClientShipmentDtoJaxbList value) {
        return new JAXBElement<>(_Shipments_QNAME, ClientShipmentDtoJaxbList.class, null, value);
    }

}
