package com.test;

import com.test.database.DBUtility;
import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
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
public class SellerResourceHelper {

    public List<Seller> getAllSellers(){

        Connection conn = null;
        Statement stmt = null;
        List<Seller> allSellers = new ArrayList<Seller>();
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT sellerid, name, email, phone, address FROM Seller";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int sellerid  = rs.getInt("sellerid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phone  = rs.getInt("phone");
                String address = rs.getString("address");

                Seller seller = new Seller();
                seller.setSellerId(sellerid);
                seller.setName(name);
                seller.setEmail(email);
                seller.setPhone(phone);
                seller.setAddress(address);

                allSellers.add(seller);

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

        return allSellers;

    }

    public Seller getSeller(String sellerId){

        Connection conn = null;
        Statement stmt = null;
        Seller seller = null;
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT name, email, phone, address FROM Seller where sellerid="+sellerId;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                String name = rs.getString("name");
                String email = rs.getString("email");
                int phone  = rs.getInt("phone");
                String address = rs.getString("address");
                seller = new Seller();
                seller.setSellerId(Integer.valueOf(sellerId));
                seller.setName(name);
                seller.setEmail(email);
                seller.setPhone(phone);
                seller.setAddress(address);
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

        return seller;

    }

    public String addSeller(Seller seller) {

        Connection conn = null;
        Statement stmt = null;
        int sellerId = 0;
        try{

            conn = DBUtility.getDBConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO Seller (name, email, phone, address)" +
                    "VALUES ("+"\""+seller.getName()+"\",\" "+ seller.getEmail()+ "\",\""+
                    seller.getPhone()+ "\",\""+ seller.getAddress() + "\")";
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                sellerId=rs.getInt(1);
            }
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());
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

        return Integer.toString(sellerId);
    }
}
