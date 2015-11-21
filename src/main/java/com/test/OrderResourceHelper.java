package com.test;

import com.test.database.DBUtility;
import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.model.Item;
import com.test.model.Order;
import com.test.model.Seller;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15/11/15.
 */
public class OrderResourceHelper {

    public String createOrder(int itemId, int buyerId, String shippingDate, int orderCost, int itemQuantity){
        String result = "";

        ItemResourceHelper helper = new ItemResourceHelper();
        Item item = helper.getItem(Integer.toString(itemId));

        if(itemQuantity > item.getQuantity()){
            result = "Insufficient Inventory";
            return result;
        }
        if (orderCost < item.getCost()* itemQuantity){
            result = "Insufficient payment";
            return result;
        }
        int remainingQuantity = item.getQuantity()-itemQuantity;
        result = updateInventory(item, buyerId, shippingDate, orderCost, itemQuantity, remainingQuantity);


        return  result;
    }

    private String updateInventory(Item item, int buyerId, String shippingDate, int orderCost, int itemQuantity, int remainingQuantity) {

        Connection conn = null;
        Statement stmt = null;
        int orderId = 0;
        String result = "";
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            // Update item
            String sql = "UPDATE Item " +
                    "SET quantity="+remainingQuantity+" WHERE itemid="+item.getItemId();
            int result1 = stmt.executeUpdate(sql);
            //Create order
            sql = "INSERT INTO orders (buyerid, orderdate, shippingdate, ordercost, status)" +
                    "VALUES ("+buyerId + ", \""+System.currentTimeMillis()+"\",\" "+ shippingDate + "\","+orderCost+",\"Payment Done\")";

            int result2 = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                orderId=rs.getInt(1);
            }
            //Create order details
            sql = "INSERT INTO orderdetails (orderid, itemid, itemquantity, itemcost)" +
                    "VALUES ("+orderId+", "+item.getItemId()+", "+ itemQuantity+ ","+
                    item.getCost()+")";
            int result3 = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            if (result1 == 1 && result2 == 1 && result3 == 1){
                result = "Order successful";
            } else {
                result = "Order failed";
            }
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return result;
    }

    public List<Order> getAllOrders(){

        Connection conn = null;
        Statement stmt = null;
        List<Order> allOrders = new ArrayList<Order>();
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT orderid, orderdate, shippingdate, ordercost, status, buyerid FROM orders";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int orderId  = rs.getInt("orderid");
                String orderDate = rs.getString("orderdate");
                String shippingDate = rs.getString("shippingdate");
                int orderCost  = rs.getInt("ordercost");
                String status = rs.getString("status");
                int buyerId  = rs.getInt("buyerid");

                Order order = new Order();
                order.setOrderId(orderId);
                order.setOrderDate(orderDate);
                order.setShippingDate(shippingDate);
                order.setOrderCost(orderCost);
                order.setStatus(status);
                order.setBuyerId(buyerId);
                allOrders.add(order);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }catch(Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return allOrders;

    }
}
