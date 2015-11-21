package com.test.database;

import com.test.model.Item;

import java.sql.*;

/**
 * Created by root on 14/11/15.
 */
public class DBUtility {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/testdatabase";

    //  Database credentials
    static final String USER = "testuser";
    static final String PASS = "testpassword";

    public static Connection getDBConnection(){
        Connection conn = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{

        }//end try

        return conn;
        }

    public static Item getDBConnection1(){

        Connection conn = null;
        Statement stmt = null;
        Item item = new Item();
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT itemid, name, description, cost FROM Item";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int itemId  = rs.getInt("itemid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int cost  = rs.getInt("cost");

                item.setItemId(itemId);
                item.setName(name);
                item.setDescription(description);
                item.setCost(cost);

                //Display values
                System.out.print("Item ID: " + itemId);
                System.out.print(", Name: " + name);
                System.out.println(", Description: " + description);
                System.out.println(", Cost: " + cost);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return item;
    }
}
