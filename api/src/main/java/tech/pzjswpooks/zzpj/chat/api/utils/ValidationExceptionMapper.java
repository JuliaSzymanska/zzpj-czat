package tech.pzjswpooks.zzpj.chat.api.utils;


import tech.pzjswpooks.zzpj.chat.api.payloads.response.MessageResponseDto;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Klasa mapowania wyjątków walidacji.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponseDto(exception.getMessage())).build();
    }
}