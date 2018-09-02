// Implements the Cipher data structure through PostgreSQL

package com.cipher.db;

// Java SQL packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Cipher engine; useful classes for extracing/saving the world state
import com.cipher.engine.Tile;
import com.cipher.engine.Edge;
import com.cipher.engine.Facing;
import com.cipher.engine.Map;

public class CipherDBPostgreSQL extends CipherDB {
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
    
    
    public CipherDBPostgreSQL(){
        this.connect();
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
                    + "x INT, y INT, z INT, face INT, " // Uniquely define the edge location on a certain z plane
                    + "edgetypeid INT, " // Key for obtaining the type of edge that is present
                    + "symbol CHAR(1), " // For naive visualizations a character symbol is used
                    //+ "properties xml, " // Edge properties are stored using XML formatting or such, allowing flexibility
                    + "PRIMARY KEY (x, y, z, face)" // To access an edge one has to provide two {x,y} coordinates on a z plane
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
            // Create a table that describes buildings
            statement.addBatch("CREATE TABLE IF NOT EXISTS cipherdb.buildings("
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
            resultSet = statement.executeQuery("SELECT x, y, z, symbol, tiletypeid FROM cipherdb.tiles ORDER BY x DESC, y DESC, z DESC");
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
            resultSet = statement.executeQuery("SELECT x, y, z, face, symbol, edgetypeid FROM cipherdb.edges ORDER BY x DESC, y DESC, z DESC, face");
            while(resultSet.next()){
               //Retrieve by column name
               int x = resultSet.getInt("x");
               int y = resultSet.getInt("y");
               int z = resultSet.getInt("z");
               Facing face = Facing.facings[resultSet.getInt("face")];
               char symbol = (char) resultSet.getString("symbol").charAt(0);               
               // TODO: edgetypeid
               edges.add(new Edge(x,y,z,face,symbol));
            }
            //resultSet.close();
            //statement.close();
        }catch(Exception e){
                System.out.print("\nError fetching edges: " + e + "\n");       
        }
        return edges;
    }
    // Create a map of tiles and edges based on the database
    public Map getMap(){
        System.out.println("Construcing a tile/edge map...\n");
        return new Map(this.getTiles(), this.getEdges());
    }    
    // Get a slice of the {x,y} tiling that touches at a certain z-axis plane, as this is the height axis
    public Map getSlice(int zplane){
        List<Tile> tiles = new ArrayList<Tile>();
        try{
            statement = connection.createStatement();
            // Conditional that z is a certain value for tile Coord
            resultSet = statement.executeQuery("SELECT x, y, z, symbol, tiletypeid FROM cipherdb.tiles WHERE z = " + zplane + " ORDER BY x DESC, y DESC, z DESC");
            while(resultSet.next()){
               //Retrieve by column name
               int x = resultSet.getInt("x");
               int y = resultSet.getInt("y");
               int z = resultSet.getInt("z");
               char symbol = (char) resultSet.getString("symbol").charAt(0);
               tiles.add(new Tile(x,y,z,symbol));
            }
        }catch(Exception e){
                System.out.print("\nError fetching tiles in getSlice: " + e + "\n");       
        }
        List<Edge> edges = new ArrayList<Edge>();
        try{
            System.out.print("\nFetching edges...\n");       
            statement = connection.createStatement();
            // Conditional that the edge has to touch the given z plane
            resultSet = statement.executeQuery("SELECT x, y, z, face, symbol, edgetypeid FROM cipherdb.edges WHERE z = " + zplane + " ORDER BY x DESC, y DESC, z DESC, face");
            while(resultSet.next()){
               //Retrieve by column name
               int x = resultSet.getInt("x");
               int y = resultSet.getInt("y");
               int z = resultSet.getInt("z");
               Facing face = Facing.facings[resultSet.getInt("face")];
               char symbol = (char) resultSet.getString("symbol").charAt(0);
               edges.add(new Edge(x,y,z,face,symbol));
            }
        }catch(Exception e){
                System.out.print("\nError fetching edges in getSlice: " + e + "\n");       
        }
        return new Map(tiles, edges);
    }

    
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
            String str = "INSERT INTO cipherdb.edges(x, y, z, face, symbol, edgetypeid) VALUES(" + edge.getX() + ", " + edge.getY() + ", " + edge.getZ() + ", " + edge.getFacing().getLevel() + ", '" + edge.getSymbol() + "', 0)";
            statement.addBatch(str);
            statement.executeBatch();
            return true;
        }catch(Exception e){
            System.out.print("\nError adding an edge to the CipherDB: " + e + "\n");
            return false;
        }
    }
    
    
    /*
    *   Removal/deleting of entries in DB
    */
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
    public boolean deteleEdge(int x, int y, int z, Facing face){
        try{
            String str = "DELETE FROM cipherdb.edges WHERE x = " + x + " AND y = " + y + " AND z = " + z + " AND face = " + face.getLevel() + ";";
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
    public boolean connect() {
        if (this.connection == null) {
            try {
                // Use the JDBC driver to connect to the PostgreSQL database
                Class.forName(DATABASE_DRIVER);
                this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.print("Successfully connected to the dummy Cipher PostgreSQL database\n");
                return true;
            } catch (Exception e) {
                // Something went wrong, return false as an indicator for failure
                System.out.print("\nError connecting to the Cipher PostgreSQL DB: " + e + "\n");       
                return false;
            }
        }else{
            // Connection already exists, return false as an indicator for failure
            return false;
        }
    }
    // Disconnect from the database and clean up
    public boolean disconnect() {
        if (connection != null) {
            try {
                // Closing the SQL related variables and returning true if all calls can be conducted
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
                return true;
            } catch (SQLException e) {
                // Something went wrong, return false as an indicator for failure
                System.out.print("\nError disconnecting from the Cipher PostgreSQL DB: " + e + "\n");
                return false;
            }
        }else{
            // Failure to disconnect as the connection had already been terminated or was never established in the first place
            return false;
        }
    }
}
