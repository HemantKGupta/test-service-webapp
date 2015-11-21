package com.test.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by root on 21/11/15.
 */
public class CustomNotFoundException extends WebApplicationException {

    public CustomNotFoundException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}