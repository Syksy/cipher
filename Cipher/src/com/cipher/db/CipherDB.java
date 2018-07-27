/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.db;

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

// Cipher engine
import com.cipher.engine.Tile;
import com.cipher.engine.Edge;

/**
 *
 * @author Syksy
 */
public class CipherDB {
    // OLD
    // MySQL
    //private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";    
    //private static final String URL = "jdbc:mysql://localhost/cipherdb";
    // PostgreSQL
    private static final String DATABASE_DRIVER = "org.postgresql.Driver";    
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    // OLD MySQL
    // Interact with the MySQL DB
    // ---
    // Interact with the PostgreSQL DB
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
            statement.addBatch("CREATE SCHEMA IF NOT EXISTS cipherdb;");
            // Create the essential tables for the engine
            statement.addBatch("CREATE TABLE IF NOT EXISTS cipherdb.tiles("
                    + "x INT, y INT, z INT, " // Each tile has a x,y,z coordinate
                    + "tiletypeid INT, " // Key for obtaining the type of tile that is present
                    + "symbol CHAR(1), " // For naive visualizations a character symbol is used
                    //+ "properties xml, " // Tile properties are stored using XML formatting or such, allowing flexibility
                    + "PRIMARY KEY (x, y, z)" // x,y,z is the unique combined key to the row
                    + ")"
            );
            // Create the table for edges that lie between tiles (on a z-axis plane)
            statement.addBatch("CREATE TABLE IF NOT EXISTS cipherdb.edges(" 
                    + "x1 INT, y1 INT, x2 INT, y2 INT, z INT, " // Uniquely define the edge location on a certain z plane
                    + "edgetypeid INT," // Key for obtaining the type of edge that is present
                    + "symbol CHAR(1), " // For naive visualizations a character symbol is used
                    //+ "properties xml, " // Edge properties are stored using XML formatting or such, allowing flexibility
                    + "PRIMARY KEY (x1, y1, x2, y2, z)" // To access an edge one has to provide two {x,y} coordinates on a z plane
                    + ")"
            );
            // Create a table that describes tile characteristics with a unique identifier
            statement.addBatch("CREATE TABLE IF NOT EXISTS cipherdb.tiletypes("
                    + "id INT, " // Unique identifier number for tiles
                    + "properties JSON, " // Parameters that define what the tile characteristics are like; allows flexibility for future extensions, though specific columns might be efficient aand safer (i.e. strong typing) for specific implentations
                    + "PRIMARY KEY (id)" // Tile types are retrieved using the unique id key
                    + ")"
            );
            // Create a table that describes edge characteristics with a unique identifier
            statement.addBatch("CREATE TABLE IF NOT EXISTS cipherdb.edgetypes("
                    + "id INT, " // Unique identifier number for edge
                    + "properties JSON, " // Parameters that define what the edge characteristics are like; allows flexibility for future extensions, though specific columns might be efficient aand safer (i.e. strong typing) for specific implentations
                    + "PRIMARY KEY (id)" // Tile types are retrieved using the unique id key
                    + ")"
            );
            // Execute the creation for the database framework
            statement.executeBatch();
        }catch (SQLException e) {
            //System.out.print("\nError creating a blank slate for the Cipher MySQL DB: " + e + "\n");       
            System.out.print("\nError creating a blank slate for the Cipher PostgreSQL DB: " + e + "\n");       
        }
    }
    
    /*
    * FUNCTIONS FOR INTERACTING WITH OBJECTS CHARACTERISTIC FOR THE CIPHER ENGINE
    */
    /*
    * Get
    */
    // Connect to the Cipher DB and get all the available tiles
    public List<Tile> getTiles(){
        List<Tile> tiles = new ArrayList<Tile>();
        try{
            System.out.print("\nFetching tiles...\n");       
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT x, y, z, symbol, tiletypeid FROM cipherdb.tiles");
            while(resultSet.next()){
               //Retrieve by column name
               int x = resultSet.getInt("x");
               int y = resultSet.getInt("y");
               int z = resultSet.getInt("z");
               char symbol = (char) resultSet.getString("symbol").charAt(0);
               tiles.add(new Tile(x,y,z,symbol));
            }
            //resultSet.close();
            //statement.close();
        }catch(Exception e){
                System.out.print("\nError fetching tiles: " + e + "\n");       
        }
        return tiles;
    }
    // Connect to the Cipher DB and get all the available edges
    public List<Edge> getEdges(){
        List<Edge> edges = new ArrayList<Edge>();
        try{
            System.out.print("\nFetching edges...\n");       
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT x1, y1, x2, y2, z, symbol, edgetypeid FROM cipherdb.edges");
            while(resultSet.next()){
               //Retrieve by column name
               int x1 = resultSet.getInt("x1");
               int y1 = resultSet.getInt("y1");
               int x2 = resultSet.getInt("x2");
               int y2 = resultSet.getInt("y2");
               int z = resultSet.getInt("z");
               char symbol = (char) resultSet.getString("symbol").charAt(0);
               edges.add(new Edge(x1,y1,x2,y2,z, symbol));
            }
            //resultSet.close();
            //statement.close();
        }catch(Exception e){
                System.out.print("\nError fetching edges: " + e + "\n");       
        }
        return edges;
    }
    // Get a slice of the {x,y} tiling at a z-slice'
    // ...
    // Construct a map that consists of tiles and edges (?)
    // Probably better not to create a map class, instead stick to tiles and edges
    // ...
    
    /*
    * Add
    */
    // Add a Cipher tile to the database
    public boolean addTile(Tile tile){
        try{
            String str = "INSERT INTO cipherdb.tiles(x, y, z, symbol, tiletypeid) VALUES(" + tile.getX() + ", " + tile.getY() + ", " + tile.getZ() + ", '" + tile.getSymbol() + "', 0)";
            statement.addBatch(str);
            statement.executeBatch();
            return true;
        }catch(Exception e){
            System.out.print("\nError adding a tile to the CipherDB: " + e + "\n");
            return false;
        }
    }
    // Add a Cipher edge to the database
    public boolean addEdge(Edge edge){
        try{
            String str = "INSERT INTO cipherdb.edges(x1, y1, x2, y2, z, symbol, edgetypeid) VALUES(" + edge.getX1() + ", " + edge.getY1() + ", " + edge.getX2() + ", " + edge.getY2() + ", " + edge.getZ() + ", '" + edge.getSymbol() + "', 0)";
            statement.addBatch(str);
            statement.executeBatch();
            return true;
        }catch(Exception e){
            System.out.print("\nError adding a tile to the CipherDB: " + e + "\n");
            return false;
        }
    }
    // Remove a Cipher tile at a specific position
    public boolean deteleTile(int x, int y, int z){
        try{
            String str = "DELETE FROM cipherdb.tiles WHERE x = " + x + " AND y = " + y + " AND z = " + z + ";";
            statement.addBatch(str);
            statement.executeBatch();
            return true;
        }catch(Exception e){
            System.out.print("\nError deleting a tile from the CipherDB: " + e + "\n");
            return false;
        }
    }
    // Remove a Cipher edge at a specific position
    public boolean deteleEdge(int x1, int y1, int x2, int y2, int z){
        try{
            String str = "DELETE FROM cipherdb.edges WHERE x1 = " + x1 + " AND y1 = " + y1 + " AND x2 = " + x2 + " AND y2 = " + y2 + " AND z = " + z + ";";
            statement.addBatch(str);
            statement.executeBatch();
            return true;
        }catch(Exception e){
            System.out.print("\nError deleting an edge from the CipherDB: " + e + "\n");
            return false;
        }
    }
    
    
    /*
    * FUNCTIONS FOR CONNECTING TO THE SQL DATABASE
    */
    // Connect to the database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                //System.out.print("Successfully connected to the dummy Cipher MySQL database\n");
                System.out.print("Successfully connected to the dummy Cipher PostgreSQL database\n");
            } catch (Exception e) {
                //System.out.print("\nError connecting to the Cipher MySQL DB: " + e + "\n");       
                System.out.print("\nError connecting to the Cipher PostgreSQL DB: " + e + "\n");       
            }
        }
        return connection;
    }
    // Disconnect from the database and clean up
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (resultSet != null){
                    resultSet.close();
                    resultSet = null;
                }
            } catch (SQLException e) {
                //System.out.print("\nError disconnecting from the Cipher MySQL DB: " + e + "\n");       
                System.out.print("\nError disconnecting from the Cipher PostgreSQL DB: " + e + "\n");       
            }
        }
    }

}
