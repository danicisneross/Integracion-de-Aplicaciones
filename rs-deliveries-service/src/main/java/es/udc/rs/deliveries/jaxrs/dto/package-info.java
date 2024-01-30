@XmlSchema(namespace = "http://ws.udc.es/ia02/xml", elementFormDefault = jakarta.xml.bind.annotation.XmlNsForm.QUALIFIED)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type= LocalDateTime.class,
                value= LocalDateTimeXmlAdapter.class)
})
package es.udc.rs.deliveries.jaxrs.dto;
import es.udc.rs.deliveries.jaxrs.jaxb.LocalDateTimeXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;
