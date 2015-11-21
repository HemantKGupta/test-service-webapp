package com.test;

import com.test.database.DBUtility;
import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import com.test.exception.CustomNotFoundException;
import com.test.model.Buyer;

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
public class BuyerResourceHelper {

    public List<Buyer> getAllBuyers(){

        Connection conn = null;
        Statement stmt = null;
        List<Buyer> allBuyers = new ArrayList<Buyer>();
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT buyerid, firstname, lastname, email, phone, address FROM Buyer";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int buyerid  = rs.getInt("buyerid");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String email = rs.getString("email");
                int phone  = rs.getInt("phone");
                String address = rs.getString("address");

                Buyer buyer = new Buyer();
                buyer.setBuyerId(buyerid);
                buyer.setFirstName(firstName);
                buyer.setLastName(lastName);
                buyer.setEmail(email);
                buyer.setPhone(phone);
                buyer.setAddress(address);

                allBuyers.add(buyer);
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
        return allBuyers;
    }

    public Buyer getBuyer(String buyerId){

        Connection conn = null;
        Statement stmt = null;
        Buyer buyer = null;
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT firstname, lastname, email, phone, address FROM Buyer where buyerid="+buyerId;
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String email = rs.getString("email");
                int phone  = rs.getInt("phone");
                String address = rs.getString("address");
                buyer =  new Buyer();
                buyer.setBuyerId(Integer.valueOf(buyerId));
                buyer.setFirstName(firstName);
                buyer.setLastName(lastName);
                buyer.setEmail(email);
                buyer.setPhone(phone);
                buyer.setAddress(address);
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
        return buyer;
    }

    public String addBuyer(Buyer buyer) {

        Connection conn = null;
        Statement stmt = null;
        int buyerId = 0;
        try{
            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO Buyer (firstname, lastname, email, phone, address)" +
                    "VALUES ("+"\""+buyer.getFirstName()+"\",\""+buyer.getLastName()+"\",\" "+ buyer.getEmail()+ "\",\""+
                    buyer.getPhone()+ "\",\""+ buyer.getAddress() + "\")";
            System.out.println("sql is "+ sql);
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                buyerId=rs.getInt(1);
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
        return Integer.toString(buyerId);
    }
}
