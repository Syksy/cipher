package com.ciphervaadinui;

/**
 *
 * @author Syksy
 */

public class CipherVaadinTile {
    public int x = 0, y = 0, z = 0;
    public char symbol;
    
    public CipherVaadinTile(int x, int y, int z, char symbol){
        this.x = x;
        this.y = y;
        this.z = z;
        this.symbol = symbol;
    }
    
    @Override
    public String toString(){
        return "x " + x + " y " + y + " z " + z + " symbol " + symbol + "\n";
    }
}
