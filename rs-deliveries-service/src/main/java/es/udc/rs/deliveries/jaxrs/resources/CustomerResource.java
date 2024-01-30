package es.udc.rs.deliveries.jaxrs.resources;
import java.net.URI;
import java.util.List;


import es.udc.rs.deliveries.jaxrs.util.ServiceUtil;
import es.udc.rs.deliveries.model.shipment.Shipment;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

import es.udc.rs.deliveries.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.exceptions.CustomerNoEmpty;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import es.udc.rs.deliveries.jaxrs.util.CustomerToCustomerDtoJaxBConversor;

@Path("customer")
@OpenAPIDefinition(
        info = @Info(
                title = "ia-02",
                version = "2.2.15",
                description = "RS JAX-RS Documentacion"),
        servers = {
                @Server(
                        description = "Localhost server",
                        url = "http://localhost:10000/rs-deliveries-service")
        })

public class CustomerResource {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "Adds a new customer", description = "This is a post statement so the elements must be " +
            "specified in the body, they are Name,Cif and Address")
    @ApiResponse(responseCode = "201", description = "Created, a new shipment has been added to the customer successfully",
            content = @Content(schema = @Schema(implementation = Shipment.class)))
    @ApiResponse(responseCode = "400",
            description = "ERROR of validation ,some input parameters are invalid, could be: Name, Cif or the address",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    public Response addCustomer(final CustomerDtoJaxb customerDto, @Context UriInfo uriInfo,
                                @Context HttpHeaders headers) throws InputValidationException {

        Customer customer = CustomerToCustomerDtoJaxBConversor.toCustomer(customerDto);
        customer = DeliveryServiceFactory.getService().addCustomer(customer.getName(),customer.getCif(),customer.getAddress());
        final CustomerDtoJaxb resultCustomerDto = CustomerToCustomerDtoJaxBConversor.
                toCustomerDtoJaxb(customer, uriInfo.getBaseUri(), ServiceUtil.getTypeAsStringFromHeaders(headers));

        final String requestUri = uriInfo.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + customer.getCustomerId()))
                .entity(resultCustomerDto).build();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

    @Operation(summary = "Get the information of a customer by its ID",
            description = "This is a get statement so the ID must be specified in the URL")
    @ApiResponse(responseCode = "200", description = "OK, the ID matches a customer so its information is displayed",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "ERROR of validation, the ID is probably not a number",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "ERROR customer not found, the ID does not match any customer",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
    public CustomerDtoJaxb findCustomerById(@PathParam("id") final String id,
                                            @Context UriInfo uriInfo,
                                            @Context HttpHeaders headers)
            throws InputValidationException, InstanceNotFoundException {

        Long customerId;

        try{
            customerId = Long.valueOf(id);
        } catch (final NumberFormatException exception){
            throw new InputValidationException("Invalid Request: unable to parse customer id " + id);
        }
        return CustomerToCustomerDtoJaxBConversor.toCustomerDtoJaxb(DeliveryServiceFactory.getService().
                findCustomer(customerId), uriInfo.getBaseUri(), ServiceUtil.getTypeAsStringFromHeaders(headers));
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "Get the information from a customer that matches the specified keyword",
            description = "This is a get statement so the keyword must be specified in the URL, " +
                    "the keyword must refer to the name of the customer")
    @ApiResponse(responseCode = "200", description="OK,customers matching the specified keyword, " +
            "as a whole or its just part of its name. It can be a empty list if nothing matches",
            content = {	@Content(
                    mediaType = "application/xml",
                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))
            )
            })
    public List<CustomerDtoJaxb> findCustomersKey(@QueryParam("keywords") final String keywords,
                                                  @Context UriInfo uriInfo,
                                                  @Context HttpHeaders headers) throws InputValidationException {

        final List<Customer> customers = DeliveryServiceFactory.getService().findCustomerKeyword(keywords);
        return CustomerToCustomerDtoJaxBConversor.toCustomerDtoJaxb(customers,
                uriInfo.getBaseUri(), ServiceUtil.getTypeAsStringFromHeaders(headers));

    }
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @Operation(summary = "Update or change the information of a customer by its ID",
            description = "This is a put statement so the ID must be specified in the URL and the changes must be " +
                    "specified in its body. All fields (ID,NAME,CIF,ADDRESS) must have content, they cannot be null or empty")
    @ApiResponse(responseCode = "204", description="No content, the update was a success",
            content = {	@Content(
                    mediaType = "application/xml",
                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))
            )
            })
    @ApiResponse(responseCode = "400", description = "ERROR of validation, the ID is probably not a number",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "ERROR customer not found, the ID does not match any customer",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public void updateCustomer(final CustomerDtoJaxb customerDto, @PathParam("id") final String id)
            throws InputValidationException, InstanceNotFoundException {

        Long customerId;
        try{
            customerId = Long.valueOf(id);
        } catch (final NumberFormatException exception){
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id " + id);
        }

        if(!customerId.equals(customerDto.getCustomerId())){
            throw new InputValidationException(
                    "Invalid Request: invalid customer Id " + customerDto.getCustomerId() +
                            " for customer " + customerId);
        }
        final Customer customer = CustomerToCustomerDtoJaxBConversor.toCustomer(customerDto);
        DeliveryServiceFactory.getService().updateCustomer(customer);
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")

    @Operation(summary = "Delete a customer by its ID",
            description = "This is a delete statement so the ID must be specified in the URL")
    @ApiResponse(responseCode = "204", description="No content, the has been successfully deleted ",
            content = {	@Content(
                    mediaType = "application/xml",
                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))
            )
            })
    @ApiResponse(responseCode = "400", description = "ERROR of validation, the ID is probably not a number",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "403", description = "ERROR Forbidden, the customer has shipments attached to it, " +
            "they must be deleted in order to be able to delete the customer",
            content = @Content(schema = @Schema(implementation = CustomerNoEmpty.class)))
    @ApiResponse(responseCode = "404", description = "ERROR customer not found, the ID does not match any customer",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public void deleteCustomer(@PathParam("id") final String id)
            throws InputValidationException, InstanceNotFoundException, CustomerNoEmpty
    {

        Long customerId;
        try {
            customerId = Long.valueOf(id);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + id + "'");
        }
        DeliveryServiceFactory.getService().deleteCustomer(customerId);
    }

}
