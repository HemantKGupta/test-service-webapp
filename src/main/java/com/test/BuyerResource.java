package com.test;

import com.test.exception.APIException;
import com.test.exception.APIErrorMessage;
import com.test.exception.CustomNotFoundException;
import com.test.model.Buyer;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONWriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by root on 14/11/15.
 */
@Path("buyer")
public class BuyerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        BuyerResourceHelper helper = new BuyerResourceHelper();
        List<Buyer> result = helper.getAllBuyers();
        if (result == null || result.size() == 0)
            throw new CustomNotFoundException("Buyers not found");
        ObjectMapper mapper = new ObjectMapper();
        String outputJson = "";
        try {
            outputJson = mapper.writeValueAsString(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(outputJson, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuyer(@PathParam("id") String buyerId) {


        BuyerResourceHelper helper = new  BuyerResourceHelper();
        Buyer result = helper.getBuyer(buyerId);
        if(result == null)
            throw new CustomNotFoundException("Buyer not found");
        ObjectMapper mapper = new ObjectMapper();
        String outputJson = "";
        try {
            outputJson = mapper.writeValueAsString(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(outputJson, MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addBuyer( @FormParam("firstname") String firstName,
                             @FormParam("lastname") String lastName,
                             @FormParam("email") String email,
                             @FormParam("phone") String phone,
                             @FormParam("address") String address
                             ) {

        Buyer buyer =  new Buyer();
        buyer.setFirstName(firstName);
        buyer.setLastName(lastName);
        buyer.setEmail(email);
        buyer.setPhone(Integer.valueOf(phone));
        buyer.setAddress(address);


        BuyerResourceHelper helper = new BuyerResourceHelper();
        String result = helper.addBuyer(buyer);
        StringWriter sw = new StringWriter();
        JSONWriter json = new JSONWriter(sw);
        try {
            json.object();
            json.key("buyerId").value (result);
            json.endObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(sw.toString(), MediaType.APPLICATION_JSON).build();
    }
}
