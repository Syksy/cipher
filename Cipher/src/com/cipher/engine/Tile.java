/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cipher.engine;

import com.cipher.sparsematrixtools.Coord;

/**
 *
 * @author Syksy
 */
public class Tile {
    private Coord xyz = new Coord(0,0,0);
    private char symbol = '#';
    
    /*
    *   CONSTRUCTORS
    */
    public Tile(int x, int y, int z){
        this.xyz = new Coord(x, y, z);
    }
    public Tile(int x, int y, int z, char symbol){
        this.xyz = new Coord(x, y, z);
        this.symbol = symbol;
    }
    public Tile(){
        // Location at 0,0,0 and default settings
    }
    
    // Getters and setters
    public Coord getCoords(){
        return xyz;
    }
    public int getX(){
        return xyz.getX();
    }
    public int getY(){
        return xyz.getY();
    }
    public int getZ(){
        return xyz.getZ();
    }
    public void setCoords(int x, int y, int z){
        this.xyz = new Coord(x, y, z);
    }
    public void setCoords(Coord coords){
        this.xyz = coords;
    }
    public char getSymbol(){
        return this.symbol;
    }
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }
    
    @Override
    public String toString(){
        return "x " + xyz.getX() + ",y " + xyz.getY() + ",z " + xyz.getZ()  + ": " + symbol;
    }
}
