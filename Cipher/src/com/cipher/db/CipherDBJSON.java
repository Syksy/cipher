package com.cipher.db;

// A Cipher game that is upon start (connection) is read from a JSON file, game state is kept in memory, and then written back to a JSON file once the session stops (disconnect)

// Lists/arrays
import java.util.List;
import java.util.ArrayList;
// Cipher engine specific classes
import com.cipher.engine.Edge;
import com.cipher.engine.Facing;
import com.cipher.engine.Map;
import com.cipher.engine.Tile;
// File IO, for reading and writing the JSON into text

// Conditional stream picking for ArrayList objects
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CipherDBJSON extends CipherDB{
    private List<Tile> tiles;
    private List<Edge> edges;
    private Map map;
    
    public CipherDBJSON(){
        this.CreateCipherDB();
    }
    // Create an empty database for storing all essential information for the Cipher game engine
    public void CreateCipherDB(){
        this.tiles = new ArrayList<Tile>();
        this.edges = new ArrayList<Edge>();
        this.map = new Map(tiles, edges);
    }
    /*
    * Get
    */
    // Connect to the Cipher DB and get all the available tiles
    public List<Tile> getTiles(){
        return this.tiles;
    }
    // Connect to the Cipher DB and get all the available edges
    public List<Edge> getEdges(){
        return this.edges;
    }
    // Create a map of tiles and edges based on the database
    public Map getMap(){
        return this.map;
    }
    // Get a slice of the {x,y} tiling that touches at a certain z-axis plane, as this is the height axis
    public Map getSlice(int zplane){
        // Filter and collect conditional lists based on the z-axis Predicate(s)
        List<Tile> slicedTiles = this.tiles.stream().filter(t -> t.getZ() == zplane).collect(Collectors.toList());
        List<Edge> slicedEdges = this.edges.stream().filter(e -> e.getZ() == zplane).collect(Collectors.toList());
        return new Map(slicedTiles, slicedEdges);
    }
    /*
    * Add
    */
    // Add a Cipher tile to the database
    public boolean addTile(Tile tile){
        this.tiles.add(tile);
        return true;
    }
    // Add a Cipher edge to the database
    public boolean addEdge(Edge edge){
        this.edges.add(edge);
        return true;
    }

    /*
    *   Removal/deleting of entries in DB
    */
    // Remove a Cipher tile at a specific position
    public boolean deteleTile(int x, int y, int z){
        return false;
    }
    // Remove a Cipher edge at a specific position
    public boolean deteleEdge(int x, int y, int z, Facing face){
        return false;
    }

    /*
    * Conduct parsed JSON file read/write to load/save gamestate from memory
    */
    // Read gamestate from a JSON parsed file
    public boolean connect(){
        return false;
    }
    // Write gamestate to a JSON parsed file
    public boolean disconnect(){
        return false;
    }
}
