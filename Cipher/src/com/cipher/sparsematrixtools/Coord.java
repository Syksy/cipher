package com.cipher.sparsematrixtools;

/**
 *
 * @author Syksy
 */

public class Coord{
    // {x,y,z} coords always hold 3 coordinates; by default they are zero
    protected int x = 0, y = 0, z = 0;
    
    /*
     * CONSTRUCTORS
     */
    public Coord(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
    }
    public Coord(){
            this.x = 0;
            this.y = 0;
            this.z = 0;
    }
        
    // Getters
    public int[] getCoords(){
        int[] tridim = {x, y, z};
        return tridim;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getZ(){
        return z;
    }
    // Setters
    public void setCoords(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setZ(int z){
        this.z = z;
    }
    
}


