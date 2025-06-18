package com.bookstore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

@Provider
class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        ErrorResponse error = new ErrorResponse(
            exception.getMessage(),
            exception.getMessage()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                      .entity(error)
                      .build();
    }
} 