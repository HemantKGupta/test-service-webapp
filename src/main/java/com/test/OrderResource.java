package com.test;

import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.exception.CustomNotFoundException;
import com.test.model.Order;
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
@Path("order")
public class OrderResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createOrder( @FormParam("itemid") String itemId,
                             @FormParam("buyerid") String buyerId,
                             @FormParam("shippingdate") String shippingDate,
                             @FormParam("ordercost") String orderCost,
                             @FormParam("itemquantity") String itemQuantity
                             ) {

        int itemIdInt = Integer.valueOf(itemId);
        int buyerIdInt = Integer.valueOf(buyerId);
        int ordeCostInt = Integer.valueOf(orderCost);
        int itemQuantityInt = Integer.valueOf(itemQuantity);
        OrderResourceHelper helper = new OrderResourceHelper();


        String result = helper.createOrder(itemIdInt, buyerIdInt, shippingDate, ordeCostInt, itemQuantityInt);
        StringWriter sw = new StringWriter();
        JSONWriter json = new JSONWriter(sw);
        try {
            json.object();
            json.key("result").value (result);
            json.endObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }
        return Response.ok(sw.toString(), MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        OrderResourceHelper helper = new OrderResourceHelper();
        List<Order> result = helper.getAllOrders();
        if (result == null || result.size() == 0)
            throw new CustomNotFoundException("Orders not found");
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

}
