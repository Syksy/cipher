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
    // Location of the tile in the {x,y,z} coordinate axes
    protected Coord xyz;
    // Symbol for naive visualizations
    protected char symbol;
    
    /*
    *   CONSTRUCTORS
    */
    // Tile with xyz-coordinates, default symbol
    public Tile(int x, int y, int z){
        this.xyz = new Coord(x, y, z);
        this.symbol = '.';
    }
    // Tile with xyz-coordinates, custom symbol
    public Tile(int x, int y, int z, char symbol){
        this.xyz = new Coord(x, y, z);
        this.symbol = symbol;
    }
    // Location at 0,0,0 and default settings
    public Tile(){
        this.xyz = new Coord(0,0,0);
        this.symbol = '.';
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
    public void setX(int x){
        this.xyz.setX(x);
    }
    public void setY(int y){
        this.xyz.setY(y);
    }
    public void setZ(int z){
        this.xyz.setZ(z);
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
        //return "x " + xyz.getX() + ",y " + xyz.getY() + ",z " + xyz.getZ()  + ": symbol '" + symbol + "'";
        return "Tile {" + this.xyz.getX() + "," + this.xyz.getY() + "," + this.xyz.getZ() + "}";
    }
}
