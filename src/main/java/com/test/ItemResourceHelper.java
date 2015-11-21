package com.test;

import com.test.database.DBUtility;
import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.model.Item;

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
public class ItemResourceHelper {

    public List<Item> getAllItems(String searchKey){

        Connection conn = null;
        Statement stmt = null;
        List<Item> allItems = new ArrayList<Item>();
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT itemid, name, description, cost, quantity, sellerid FROM Item";
            if(searchKey != null && searchKey !="")
                sql = sql + " WHERE description LIKE '%"+searchKey+"%'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int itemId  = rs.getInt("itemid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int cost  = rs.getInt("cost");
                int quantity  = rs.getInt("quantity");
                int sellerid  = rs.getInt("sellerid");

                Item item = new Item();
                item.setItemId(itemId);
                item.setName(name);
                item.setDescription(description);
                item.setCost(cost);
                item.setQuantity(quantity);
                item.setSellerId(sellerid);

                allItems.add(item);
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

        return allItems;

    }

    public Item getItem(String itemId) {

        Connection conn = null;
        Statement stmt = null;
        Item item = null;
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT name, description, cost, quantity, sellerid FROM Item where itemid="+itemId;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                item = new Item();
                String name = rs.getString("name");
                String description = rs.getString("description");
                int cost = rs.getInt("cost");
                int quantity = rs.getInt("quantity");
                int sellerid = rs.getInt("sellerid");

                item.setItemId(Integer.valueOf(itemId));
                item.setName(name);
                item.setDescription(description);
                item.setCost(cost);
                item.setQuantity(quantity);
                item.setSellerId(sellerid);

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
        return item;
    }

    public String addItem(Item item) {

        Connection conn = null;
        Statement stmt = null;
        int itemid = 0;
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO Item (name, description, cost, quantity, sellerId)" +
                    "VALUES ("+"\""+item.getName()+"\",\""+item.getDescription()+"\",\" "+ item.getCost()+ "\",\""+
                    item.getQuantity()+"\",\" "+item.getSellerId() +"\")";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                itemid=rs.getInt(1);
            }
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

        return Integer.toString(itemid);
    }
}
