package es.udc.rs.deliveries.client.service.rest.util;

import es.udc.rs.deliveries.client.service.rest.dto.*;
import es.udc.rs.deliveries.client.service.rest.DtosClient.ClientCustomerNoEmptyException;
import es.udc.rs.deliveries.client.service.rest.DtosClient.ClientShipmentStatusException;
import es.udc.rs.deliveries.client.service.rest.dto.ClientInputValidationExceptionDtoJaxb;
import es.udc.rs.deliveries.client.service.rest.dto.ClientInstanceNotFoundExceptionDtoJaxb;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class JaxbExceptionConversor {
    public static InputValidationException toInputValidationException(
            ClientInputValidationExceptionDtoJaxb exDto) {
        return new InputValidationException(exDto.getMessage());
    }

    public static InstanceNotFoundException toInstanceNotFoundException(
            ClientInstanceNotFoundExceptionDtoJaxb exDto) {
        return new InstanceNotFoundException(exDto.getInstanceId(),
                exDto.getInstanceType());
    }

    public static ClientCustomerNoEmptyException toCustomerNoEmptyException(
            ExceptionDtoJaxb exDto) {
        Long customerId = null;

        for (ExceptionParamDtoJaxb exParamDto : exDto.getParams()) {
            switch (exParamDto.getKey()) {
                case "customerId": {
                    customerId = Long.valueOf(exParamDto.getValue());
                    break;
                }
            }
        }
        return new ClientCustomerNoEmptyException(customerId);
    }
    public static ClientShipmentStatusException toShipmentStatusException(
            ExceptionDtoJaxb exDto) {
        String message = null;
        for (ExceptionParamDtoJaxb exParamDto : exDto.getParams()) {
            switch (exParamDto.getKey()) {
                case "message": {
                    message = exParamDto.getValue();
                    break;
                }
            }
        }
        return new ClientShipmentStatusException(message);
    }


}
