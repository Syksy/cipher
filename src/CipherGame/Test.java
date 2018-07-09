/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CipherGame;

// Java MySQL packages
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author teelaa
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try{
            // Setting up an example MySQL DB
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.getConnection("jdbc:mysql://localhost/test","root", "");
            System.out.print("Successfully connected to the dummy MySQL database");
            conn.close();
        }
        catch(Exception e){
            System.out.print("Error connecting to the MySQL DB:" + e.toString());
        }
    }    
}
