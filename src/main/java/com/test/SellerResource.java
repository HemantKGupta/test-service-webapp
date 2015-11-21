package com.test;

import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.exception.CustomNotFoundException;
import com.test.model.Seller;
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
@Path("seller")
public class SellerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        SellerResourceHelper helper = new SellerResourceHelper();
        List<Seller> result = helper.getAllSellers();
        if (result == null || result.size() == 0)
            throw new CustomNotFoundException("Sellers not found");
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

    @Path("/{sellerid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeller(@PathParam("sellerid") String sellerId) {

        SellerResourceHelper helper = new SellerResourceHelper();
        Seller result = helper.getSeller(sellerId);
        if (result == null)
            throw new CustomNotFoundException("Seller not found");
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
    public Response addBuyer( @FormParam("name") String name,
                              @FormParam("email") String email,
                              @FormParam("phone") String phone,
                              @FormParam("address") String address
    ) {
        Seller seller =  new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setPhone(Integer.valueOf(phone));
        seller.setAddress(address);


        SellerResourceHelper helper = new SellerResourceHelper();
        String result = helper.addSeller(seller);
        StringWriter sw = new StringWriter();
        JSONWriter json = new JSONWriter(sw);
        try {
            json.object();
            json.key("sellerId").value (result);
            json.endObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(sw.toString(), MediaType.APPLICATION_JSON).build();
    }

}
