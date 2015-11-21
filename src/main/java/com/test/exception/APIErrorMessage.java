package com.test.exception;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by root on 21/11/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIErrorMessage {

    private static final transient ObjectMapper MAPPER = new ObjectMapper();

    String message;

    public APIErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson(){

            try
            {
                return MAPPER.writeValueAsString(this);
            }
            catch (Exception e)
            {
                return "{\"message\":\"An internal server error occurred\"}";
            }
        }

}
