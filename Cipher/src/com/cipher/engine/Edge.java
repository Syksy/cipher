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
public class Edge {
    // Edge can be defined uniquely using two tiles, for which the z-plane value is equal
    private Coord xyz1;
    private Coord xyz2;
    
    // Two {x,y} coordinates that have to be located on the same z-axis plane
    public Edge(int x1, int x2, int y1, int y2, int z){
        this.xyz1 = new Coord(x1, y1, z);
        this.xyz2 = new Coord(x2, y2, z);
    }
    
    // Setters
    public void setX1(int x1){
        this.xyz1.setX(x1);
    }
    public void setX2(int x2){
        this.xyz2.setX(x2);
    }
    public void setY1(int y1){
        this.xyz1.setY(y1);
    }
    public void setY2(int y2){
        this.xyz2.setY(y2);
    }
    public void setZ(int z){
        this.xyz1.setZ(z);
        this.xyz2.setZ(z);
    }
    // Getters
    public int getX1(){ return this.xyz1.getX(); };
    public int getX2(){ return this.xyz2.getX(); };
    public int getY1(){ return this.xyz1.getY(); };
    public int getY2(){ return this.xyz2.getY(); };
    public int getZ(){ return this.xyz1.getZ(); }; // xyz1 and xyz2 should have equal z-plane
    
}
