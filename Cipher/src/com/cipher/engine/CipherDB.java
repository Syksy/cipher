/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.engine;

// Java MySQL packages
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author Syksy
 */
public class CipherDB {
    // MySQL
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";    
    private static final String URL = "jdbc:mysql://localhost/cipherdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    // Interact with the MySQL DB
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null; 
    
    
    public CipherDB(){
        connection = this.connect();
        this.CreateCipherDB();
    }
    
    // Create the empty schema in Cipher MySQL database
    public void CreateCipherDB(){
        try{
            statement = connection.createStatement();
            // Create database if it does not yet exist
            statement.addBatch("CREATE DATABASE IF NOT EXISTS cipherdb;");
            //statement.addBatch("CREATE SCHEMA IF NOT EXISTS cipher;");
            // Create the essential tables for the engine
            statement.addBatch("CREATE TABLE IF NOT EXISTS tiles("
                    + "x INT, y INT, z INT, " // Each tile has a x,y,z coordinate
                    + "symbol CHAR(1), " // For the time being tiles are just represented with characters
                    + "uniqid INT, " // Connect each tile to something unique (building, character, ...)
                    + "PRIMARY KEY (x, y, z)" // x,y,z is the unique combined key to the row
                    + ")"
            );
            statement.executeBatch();
        }catch (SQLException e) {
            System.out.print("\nError creating a blank slate for the Cipher MySQL DB: " + e + "\n");       
        }
    }
    
    // Connect to the Cipher DB and get all the available tiles
    public List<Tile> getTiles(){
        List<Tile> tiles = new ArrayList<Tile>();
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
               tiles.add(new Tile(x,y,z,symbol));
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

    // Add a Cipher tile to the database
    public void addTile(Tile tile){
        try{
            String str = "INSERT INTO tiles(x, y, z, symbol, uniqid) VALUES(" + tile.getX() + ", " + tile.getY() + ", " + tile.getZ() + ", '" + tile.getSymbol() + "', 0)";
            statement.addBatch(str);
            statement.executeBatch();
        }catch(Exception e){
            System.out.print("\nError adding a tile to the CipherDB: " + e + "\n");
        }
    }
    // Get all tiles in the database
    //public Tile[] getTiles(){
        
    //}
}
