package ru.ifmo.web.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {
    @Override
    public Response toResponse(InvalidParameterException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity("Invalid param: " + e.getParamName()).build();
    }
}
