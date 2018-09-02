/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.cipher.engine.Tile;
import com.cipher.engine.Edge;
import com.cipher.engine.Map;
import com.cipher.engine.Facing;

public abstract class CipherDB {
    // Create an empty database for storing all essential information for the Cipher game engine
    abstract public void CreateCipherDB();
    /*
    * Get
    */
    // Connect to the Cipher DB and get all the available tiles
    abstract public List<Tile> getTiles();
    // Connect to the Cipher DB and get all the available edges
    abstract public List<Edge> getEdges();
    // Create a map of tiles and edges based on the database
    abstract public Map getMap();
    // Get a slice of the {x,y} tiling that touches at a certain z-axis plane, as this is the height axis
    abstract public Map getSlice(int zplane);    
    /*
    * Add
    */
    // Add a Cipher tile to the database
    abstract public boolean addTile(Tile tile);
    // Add a Cipher edge to the database
    abstract public boolean addEdge(Edge edge);

    /*
    *   Removal/deleting of entries in DB
    */
    // Remove a Cipher tile at a specific position
    abstract public boolean deteleTile(int x, int y, int z);
    // Remove a Cipher edge at a specific position
    abstract public boolean deteleEdge(int x, int y, int z, Facing face);
    /*
    * FUNCTIONS FOR CONNECTING TO THE SQL DATABASE
    */
    // Connect to the database
    abstract public boolean connect();
    // Disconnect from the database and clean up
    abstract public boolean disconnect();
}
