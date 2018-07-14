/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.engine;

// Cipher DB handling
import com.cipher.db.*;

import java.util.List;


/**
 *
 * @author Syksy
 */
public class Cipher {
    static CipherDB db;
    
    public static void main(String[] args){
        db = new CipherDB();
        // Testing tiles
        db.addTile(new Tile(1, 1, 2, '@'));
        db.addTile(new Tile(1, 1, 3, '.'));
        db.addTile(new Tile(0, 0, 0, '.'));
        
        List<Tile> tiles = db.getTiles();
        System.out.println("Nrow in tiles: " + tiles.size() + "\n");
        for(Tile tile : tiles){
            System.out.println("Tile found: " + tile + "\n");
        }
        System.out.println("End of main\n");
    }    
}
