/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ciphervaadinui;

// Java MySQL interaction
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Some useful Java data types
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Syksy
 */
public class CipherVaadinDB {
    // Static Cipher-game database
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";    
    private static final String URL = "jdbc:mysql://localhost/cipherdb";
    // User login specific information
    private String USERNAME = "root";
    private String PASSWORD = "";
    // Interact with the MySQL DB
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null; 
    
    
    public CipherVaadinDB(){
        connection = this.connect();
    }  
    //public CipherVaadinDB(String USERNAME, String PASSWORD){
    //    connection = this.connect();
    //}  
    
   // Connect to the Cipher DB and get all the available tiles
    public List<CipherVaadinTile> getTiles(){
        List<CipherVaadinTile> tiles = new ArrayList<CipherVaadinTile>();
        try{
            System.out.print("\nFetching tiles...\n");       
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT x, y, z, symbol, uniqid FROM tiles");
            while(resultSet.next()){
               //Retrieve by column name
               int x = resultSet.getInt("x");
               int y = resultSet.getInt("y");
               int z = resultSet.getInt("z");
               char symbol = (char) resultSet.getString("symbol").charAt(0);
               tiles.add(new CipherVaadinTile(x,y,z,symbol));
               //tiles.add(new Tile(x,y,z,'-'));
            }
            resultSet.close();
            statement.close();
        }catch(Exception e){
                System.out.print("\nError fetching tiles: " + e + "\n");       
        }
        return tiles;
    }
 
    // Connect to the database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.print("Successfully connected to the dummy Cipher MySQL database\n");
            } catch (Exception e) {
                System.out.print("\nError connecting to the Cipher MySQL DB: " + e + "\n");       
            }
        }
        return connection;
    }

    // Disconnect from the database and clean up
    public void disconnect() {
        if (connection != null) {
            try {
                if(connection != null){
                    connection.close();
                    connection = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
                System.out.print("\nError disconnecting from the Cipher MySQL DB: " + e + "\n");       
            }
        }
    }
}
