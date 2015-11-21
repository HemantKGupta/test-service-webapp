package com.test;

import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.exception.CustomNotFoundException;
import com.test.model.Item;
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
@Path("item")
public class ItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("search") String searchKey) {

        ItemResourceHelper helper = new ItemResourceHelper();
        List<Item> result = helper.getAllItems(searchKey);
        if (result == null || result.size() == 0)
            throw new CustomNotFoundException("Items not found");
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
    public Response getItem(@PathParam("id") String itemId) {

        ItemResourceHelper helper = new ItemResourceHelper();
        Item result = helper.getItem(itemId);
        if (result == null)
            throw new CustomNotFoundException("Item not found");
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
    public Response addItem( @FormParam("name") String name,
                             @FormParam("description") String description,
                             @FormParam("cost") String cost,
                             @FormParam("quantity") String quantity,
                             @FormParam("sellerid") String sellerId)
    {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setCost(Integer.valueOf(cost));
        item.setQuantity(Integer.valueOf(quantity));
        item.setSellerId(Integer.valueOf(sellerId));

        ItemResourceHelper helper = new ItemResourceHelper();
        String result = helper.addItem(item);
        StringWriter sw = new StringWriter();
        JSONWriter json = new JSONWriter(sw);
        try {
            json.object();
            json.key("itemId").value (result);
            json.endObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(sw.toString(), MediaType.APPLICATION_JSON).build();
    }
}
