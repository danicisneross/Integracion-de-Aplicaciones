package es.udc.rs.deliveries.jaxrs.util;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import  es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class ServiceUtil {

    private static List<MediaType> responseMediaTypes = Arrays
            .asList(new MediaType[] { MediaType.APPLICATION_XML_TYPE,
                    MediaType.APPLICATION_JSON_TYPE });

    public static String getTypeAsStringFromHeaders(HttpHeaders headers) {
        List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
        for (MediaType m : mediaTypes) {
            MediaType compatibleType = getCompatibleAcceptableMediaType(m);
            if (compatibleType != null) {
                return compatibleType.toString();
            }
        }
        return null;
    }

    private static MediaType getCompatibleAcceptableMediaType(MediaType type) {
        for (MediaType m : responseMediaTypes) {
            if (m.isCompatible(type)) {
                return m;
            }
        }
        return null;
    }

    public static AtomLinkDtoJaxb getLinkFromUri(URI baseUri, Class<?> resourceClass,
                                                 Object instanceId, String rel, String title, String type) {
        Link.Builder linkBuilder = Link
                .fromPath(
                        baseUri.toString()
                                + UriBuilder.fromResource(resourceClass)
                                .build().toString() + "/"
                                + instanceId).rel(rel).title(title);
        if (type != null) {
            linkBuilder.type(type);
        }
        Link link = linkBuilder.build();
        return new AtomLinkDtoJaxb(link.getUri(), link.getRel(), link.getType(), link.getTitle());
    }
    public static AtomLinkDtoJaxb getShipmentsIntervalLinkFromUri(URI baseUri, Class<?> resourceClass,
                                                                  Long customerId,
                                                                  String rel, String title, String type) {
        Link.Builder linkBuilder = Link
                .fromUriBuilder(
                                UriBuilder.fromPath(baseUri.toString() + UriBuilder.
                                                fromResource(resourceClass).build().toString())
                                .queryParam("id", customerId)
                                .queryParam("startIndex", 0)
                                .queryParam("count", 10)
                ).rel(rel).title(title);

        if (type != null) {
            linkBuilder.type(type);
        }
        Link link = linkBuilder.build();
        return new AtomLinkDtoJaxb(link.getUri(), link.getRel(), link.getType(), link.getTitle());
    }

    public static Link getShipmentsIntervalLink(UriInfo uriInfo,
                                               Long customerId,
                                               String status,
                                               int startIndex, int count,
                                               String rel, String title, String type) {
        UriBuilder uriBuilder;
        if(status != null){
            uriBuilder = uriInfo.getAbsolutePathBuilder()
                    .queryParam("id", customerId)
                    .queryParam("status", status)
                    .queryParam("startIndex", startIndex)
                    .queryParam("count", count);
        }else{
            uriBuilder = uriInfo.getAbsolutePathBuilder()
                    .queryParam("id", customerId)
                    .queryParam("startIndex", startIndex)
                    .queryParam("count", count);
        }

        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder)
                .rel(rel)
                .title(title);
        if (type!=null) {
            linkBuilder.type(type);
        }
        return linkBuilder.build();
    }

}
