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
public class Edge extends Tile {
    // Edge is uniquely defined by its location and which side it is 'Facing' to
    // Coord xyz and char symbol are inherited from Tile
    //private Coord xyz1;
    Facing face;
    
    // Two {x,y} coordinates that have to be located on the same z-axis plane; default symbol
    public Edge(int x, int y,int z, Facing face, char symbol){
        this.xyz = new Coord(x, y, z);
        this.face = face;
        this.symbol = symbol;
        
    }
    public Edge(int x, int y,int z, Facing face){
        this.xyz = new Coord(x, y, z);
        this.face = face;
        this.symbol = '-';
        
    }
    
    // Setters
    // Moved to parent class Tile
    /*
    public void setX(int x){
        this.xyz.setX(x);
    }
    public void setY(int y){
        this.xyz.setY(y);
    }
    public void setZ(int z){
        this.xyz.setZ(z);
    }
    */
    // Getters
    // Redundancy now due to inheritance from Tile
    //public int getX(){ return this.xyz.getX(); }
    //public int getY(){ return this.xyz.getY(); }
    //public int getZ(){ return this.xyz.getZ(); }
    //public char getSymbol() { return this.symbol; }
    //public Coord getCoords() { return this.xyz; }
    public Facing getFacing() { return this.face; }
    
    @Override
    public String toString(){
        //return "x1 " + xyz1.getX() + ",y1 " + xyz1.getY() + ",x2 " + xyz2.getX() + ",y2 " + xyz2.getY() + ",z1 " + xyz1.getZ()  + ",z2 " + xyz2.getZ() + ": symbol '" + symbol + "'";
        return "Edge {" + this.xyz.getX() + "," + this.xyz.getY() + "," + this.xyz.getZ() + "} with facing " + this.face;
    }
}
