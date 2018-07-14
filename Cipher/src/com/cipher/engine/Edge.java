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
    
}
