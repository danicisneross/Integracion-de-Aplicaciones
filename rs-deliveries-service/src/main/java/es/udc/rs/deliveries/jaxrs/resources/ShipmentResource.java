package es.udc.rs.deliveries.jaxrs.resources;

import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxbList;
import es.udc.rs.deliveries.jaxrs.util.ServiceUtil;
import es.udc.rs.deliveries.jaxrs.util.ShipmentToShipmentDtoJaxbConversor;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.exceptions.ShipmentNotPending;
import es.udc.rs.deliveries.model.exceptions.ShipmentStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("shipment")
public class ShipmentResource {
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

    @Operation(summary = "Add a new shipment", description = "This is a post statement so the elements must be specified " +
            "in the body, it creates a new shipment for the specified client")
    @ApiResponse(responseCode = "201", description = "Created, a new customer has been added successfully",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation ,the address is null or not valid",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "ERROR customer not found ,the ID of the customer does mach with " +
            "any customer so it is impossible to add the shipment",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
    public Response addShipment(final ShipmentDtoJaxb shipmentDto,
                                @Context UriInfo uriInfo,
                                @Context HttpHeaders headers) throws InstanceNotFoundException, InputValidationException {
        if(shipmentDto.getCustomerId() == null){
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + shipmentDto.getCustomerId() + "'");
        }
        if(shipmentDto.getPackageReference() == null){
            throw new InputValidationException("Invalid Request: " + "unable to parse package reference '" + shipmentDto.getPackageReference() + "'");
        }
        Shipment shipment = DeliveryServiceFactory.getService().addShipment(
                shipmentDto.getCustomerId(), shipmentDto.getPackageReference(),
                shipmentDto.getAddress());
        final ShipmentDtoJaxb resultShipmentDto = ShipmentToShipmentDtoJaxbConversor.
                toShipmentDtoJaxb(shipment, uriInfo.getBaseUri(), ServiceUtil.getTypeAsStringFromHeaders(headers));

        final String requestUri = uriInfo.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + shipment.getShipmentId()))
                .entity(resultShipmentDto).build();
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/cancel")

    @Operation(summary = "update shipment status to CANCELLED",
            description = "This is a post statement so the elements must be specified in the body, it updates the " +
                    "status of a shipment to CANCELLED but it doesnt count as deleted its still in the environment")
    @ApiResponse(responseCode = "204", description = "No content,the shipment status has been updated",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation ,a parameter must be  null or its type is invalid",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "403", description = "ERROR Forbidden because shipment not in PENDING state, " +
            "the status of the shipment differs from PENDING so it cannot be updated to CANCELLED",
            content = @Content(schema = @Schema(implementation = ShipmentNotPending.class)))
    @ApiResponse(responseCode = "404", description = "ERROR shipment not found ,the ID of the shipment does not mach any shipment",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public void cancelShipment(@PathParam("id") final String id)
            throws InputValidationException, InstanceNotFoundException, ShipmentNotPending {

        Long shipmentId;
        try {
            shipmentId = Long.valueOf(id);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse shipment id '" + id + "'");
        }
        DeliveryServiceFactory.getService().cancelShipment(shipmentId);
    }
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

    @Operation(summary = "Get the shipment information and present it in a paginated manner ",
            description = "This is a get statement so the elements must be specified in the URL, it displays " +
                    "shipment information by giving the customer id,status,index and number of pages you want to view")
    @ApiResponse(responseCode = "200", description = "Ok, all parameters are ok so the information is displayed",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation ,a parameter must be  null or its type is invalid",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))

    public Response findCustomerShipments(
            @QueryParam("id") final String id,
            @QueryParam("status") final String status,
            @DefaultValue("0")   @QueryParam("startIndex") final String startIndex,
            @DefaultValue("2")   @QueryParam("count") final String count,
            @Context UriInfo uriInfo,
            @Context HttpHeaders headers) throws InputValidationException {
        int castedStartIndex;
        int castedCount;
        Long customerId;
        try {
            castedStartIndex = Integer.parseInt(startIndex);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to " +
                    "parse startIndex '" + startIndex + "'");
        }
        try {
            castedCount = Integer.parseInt(count);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to " +
                    "parse count '" + count + "'");
        }
        try {
            customerId = Long.valueOf(id);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse " +
                    "customer id '" + id + "'");
        }

        ShipmentStatus castedStatus=null;
        if(status != null){
            try {
                castedStatus = ShipmentStatus.valueOf(status);
            } catch (final IllegalArgumentException ex) {
                throw new InputValidationException(status + " is not a valid status");
            }
        }

        final List<Shipment> shipments = DeliveryServiceFactory.getService().
                findShipments(customerId, castedStatus, castedStartIndex, castedCount);

        String type = ServiceUtil.getTypeAsStringFromHeaders(headers);

        List<ShipmentDtoJaxb> shipmentDtos = ShipmentToShipmentDtoJaxbConversor.
                toShipmentDtoJaxb(shipments, uriInfo.getBaseUri(),
                        ServiceUtil.getTypeAsStringFromHeaders(headers));

        Link selfLink = getSelfLink(uriInfo, customerId, status, castedStartIndex, castedCount, type);
        Link nextLink = getNextLink(uriInfo, customerId, status, castedStartIndex, castedCount, shipments.size(), type);
        Link previousLink = getPreviousLink(uriInfo, customerId, status, castedStartIndex,
                castedCount, type);
        Response.ResponseBuilder response = Response.ok(
                new ShipmentDtoJaxbList(shipmentDtos)).links(selfLink);
        if (nextLink != null) {
            response.links(nextLink);
        }
        if (previousLink != null) {
            response.links(previousLink);
        }
        return response.build();

    }

    private static Link getNextLink(UriInfo uriInfo, Long customerId, String status,
                                    int startIndex, int count, int numberOfProducts, String type) {
        if (numberOfProducts < count) {
            return null;
        }
        return ServiceUtil.getShipmentsIntervalLink(uriInfo, customerId, status, startIndex
                + count, count, "next", "Next interval of products", type);
    }

    private Link getPreviousLink(UriInfo uriInfo, Long customerId, String status,
                                 int startIndex, int count, String type) {
        if (startIndex <= 0) {
            return null;
        }
        startIndex = startIndex - count;
        if (startIndex < 0) {
            startIndex = 0;
        }
        return ServiceUtil.getShipmentsIntervalLink(uriInfo, customerId, status,
                startIndex, count, "previous", "Previous interval of products",
                type);
    }

    private Link getSelfLink(UriInfo uriInfo, Long customerId, String status,
                             int startIndex, int count, String type) {
        return ServiceUtil
                .getShipmentsIntervalLink(uriInfo, customerId, status, startIndex, count,
                        "self", "Current interval of products", type);
    }
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

    @Operation(summary = "Get the shipment by giving the ID ", description = "This is a get statement so the elements " +
            "must be specified in the URL, it displays shipment information by giving the shipment id")
    @ApiResponse(responseCode = "200", description = "Ok,the information is displayed",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation ,ID must be  null or its type is invalid",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "ERROR shipment ID not found , the id provided is not on the list",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public ShipmentDtoJaxb findShipmentById(@PathParam("id") final String id,
                                            @Context UriInfo uriInfo,
                                            @Context HttpHeaders headers)
            throws InputValidationException, InstanceNotFoundException {

        Long shipmentId;
        try{
            shipmentId = Long.valueOf(id);
        } catch (NumberFormatException exception){
            throw new InputValidationException("Invalid Request: " +
                    "unable to parse shipment id " + id);
        }
        Shipment shipment = DeliveryServiceFactory.getService().findShipment(shipmentId);
        return ShipmentToShipmentDtoJaxbConversor.toShipmentDtoJaxb(shipment,
                uriInfo.getBaseUri(), ServiceUtil.getTypeAsStringFromHeaders(headers));
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")

    @Operation(summary = "Update the shipment status", description = "This is a post statement so the elements must be specified in " +
            "the body of the statement, it updates the shipment status to another one, but not to CANCELLED")
    @ApiResponse(responseCode = "204", description = "No content,the shipment status has been updated",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation ,a parameter must be  null or its type is invalid",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "403", description = "ERROR Forbidden because shipment not in PENDING state," +
            " the status of the shipment differs from PENDING so it cannot be updated to CANCELLED",
            content = @Content(schema = @Schema(implementation = ShipmentNotPending.class)))

    @ApiResponse(responseCode = "404", description = "ERROR shipment ID not found , the id provided is not on the list",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public void updateShipment(@PathParam("id") final String shipmentId,
                               @FormParam("status") String status)
            throws InputValidationException, InstanceNotFoundException, ShipmentStatusException {

        if (shipmentId == null){
            throw new InputValidationException("Invalid Request: parameter 'shipmentId' is mandatory");
        }

        if (status == null){
            throw new InputValidationException("Invalid Request: parameter 'status' is mandatory");
        }

        Long shipmentIdAsLong;
        try{
            shipmentIdAsLong = Long.valueOf(shipmentId);
        } catch (final NumberFormatException exception){
            throw new InputValidationException("Invalid Request: " + "unable to parse shipment id " + shipmentId);
        }
        ShipmentStatus castedStatus=null;
        if(status != null){
            try {
                castedStatus = ShipmentStatus.valueOf(status);
            } catch (final IllegalArgumentException ex) {
                throw new InputValidationException(status + " is not a valid status");
            }
        }
        DeliveryServiceFactory.getService().updateShipment(shipmentIdAsLong, castedStatus);
    }
}
