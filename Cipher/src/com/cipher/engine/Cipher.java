/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.engine;

// Cipher DB handling
import com.cipher.db.*;
import com.cipher.engine.Cmdline.Input;
import java.util.List;


/**
 *
 * @author Syksy
 */
public class Cipher {
    private static CipherDB db;
    
    public static void main(String[] args){
        db = new CipherDB(); // When the database class is created, a connection attempt to MySQL is done together with the appropriate tables (unless they already exist)
        // Testing tiles
        /*
        db.addTile(new Tile(1, 1, 2, '@'));
        db.addTile(new Tile(1, 1, 3, '.'));
        db.addTile(new Tile(0, 0, 0, '.'));
        */

        // Run Cipher testing in the command line
        Input input = new Input();
        input.CipherCmdline(db);
        
        List<Tile> tiles = db.getTiles();
        System.out.println("Nrow in tiles: " + tiles.size() + "\n");
        for(Tile tile : tiles){
            System.out.println("Tile found: " + tile + "\n");
        }
        List<Edge> edges = db.getEdges();
        System.out.println("Nrow in edges: " + edges.size() + "\n");
        for(Edge edge : edges){
            System.out.println("Edge found: " + edge + "\n");
        }
        System.out.println("\nEnd of main, disconnecting... \n");
        db.disconnect();
    }    
}
