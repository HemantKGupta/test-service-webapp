package com.test.exception;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by root on 21/11/15.
 */
public class APIException extends WebApplicationException
{

    public APIException(Response.Status responseStatus, String errorMessage) {
        super(Response.status(responseStatus)
                .entity(errorMessage).type(MediaType.APPLICATION_JSON).build());
    }

}

