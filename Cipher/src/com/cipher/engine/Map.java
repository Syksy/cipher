package com.cipher.engine;

import java.util.List;
import java.util.ArrayList;

public class Map {
    // Map consists of tiles (rectangular map units/cubes) and edges (borders/surfaces between said tiles/cubes)
    private List<Tile> tiles;
    private List<Edge> edges;
    // Min/max coord in {x,y,z}
    private int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, minz = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE, maxz = Integer.MIN_VALUE;
    // Width, depth, height
    //private int width = 0, depth = 0, height = 0;
    
    // Constructors
    public Map(){
        this.tiles = new ArrayList<Tile>();
        this.edges = new ArrayList<Edge>();
    }
    public Map(List<Tile> tiles, List<Edge> edges){
        this.tiles = tiles;
        this.edges = edges;
        for(int i=0; i<tiles.size(); i++){            
            // X coordinate
            if(tiles.get(i).getX() < minx) minx = tiles.get(i).getX();
            if(tiles.get(i).getX() > maxx) maxx = tiles.get(i).getX();
            // Y coordinate
            if(tiles.get(i).getY() < miny) miny = tiles.get(i).getY();
            if(tiles.get(i).getY() > maxy) maxy = tiles.get(i).getY();
            // Z coordinate
            if(tiles.get(i).getZ() < minz) minz = tiles.get(i).getZ();
            if(tiles.get(i).getZ() > maxz) maxz = tiles.get(i).getZ();
        }
        for(int i=0; i<edges.size(); i++){
            // X coordinate
            if(edges.get(i).getX1() < minx) minx = edges.get(i).getX1();
            if(edges.get(i).getX2() < minx) minx = edges.get(i).getX2();
            if(edges.get(i).getX1() > maxx) maxx = edges.get(i).getX1();
            if(edges.get(i).getX2() > maxx) maxx = edges.get(i).getX2();
            // Y coordinate
            if(edges.get(i).getY1() < miny) miny = edges.get(i).getY1();
            if(edges.get(i).getY2() < miny) miny = edges.get(i).getY2();
            if(edges.get(i).getY1() > maxy) maxy = edges.get(i).getY1();
            if(edges.get(i).getY2() > maxy) maxy = edges.get(i).getY2();
            // Z coordinate
            if(edges.get(i).getZ1() < minz) minz = edges.get(i).getZ1();
            if(edges.get(i).getZ2() < minz) minz = edges.get(i).getZ2();
            if(edges.get(i).getZ1() > maxz) maxz = edges.get(i).getZ1();
            if(edges.get(i).getZ2() > maxz) maxz = edges.get(i).getZ2();            
        }
    }
    
    // Adders
    public void addTile(Tile tile){
        this.tiles.add(tile);
        // Check if the new addition expands the map borders
        if(tile.getX() > maxx) maxx = tile.getX();
        if(tile.getX() < minx) minx = tile.getX();
        if(tile.getY() > maxy) maxy = tile.getY();
        if(tile.getY() < miny) miny = tile.getY();
        if(tile.getZ() > maxz) maxz = tile.getZ();
        if(tile.getZ() < minz) minz = tile.getZ();
    }
    public void addEdge(Edge edge){
        this.edges.add(edge);
        // Check if the new addition expands the map borders
        if(edge.getX1() < minx | edge.getX2() < minx) minx = Math.min(edge.getX1(), edge.getX2());
        if(edge.getX1() > maxx | edge.getX2() > maxx) maxx = Math.max(edge.getX1(), edge.getX2());
        if(edge.getY1() < miny | edge.getY2() < miny) miny = Math.min(edge.getY1(), edge.getY2());
        if(edge.getY1() > maxy | edge.getY2() > maxy) maxy = Math.max(edge.getY1(), edge.getY2());
        if(edge.getZ1() < minz | edge.getZ2() < minz) minz = Math.min(edge.getZ1(), edge.getZ2());
        if(edge.getZ1() > maxz | edge.getZ2() > maxz) maxz = Math.max(edge.getZ1(), edge.getZ2());
    }
    
    // Checkers
    public boolean checkTileExists(Tile tile){
        // TODO
        return false;
    }
    public boolean checkEdgeExists(Edge edge){
        // TODO
        return false;
    }
    
    // Getters
    public int getXSize(){
        // Redundancy if no tiles or edges are present
        if(tiles.size() == 0 | edges.size() == 0) return 0;
        // If tiles and/or edges exist, take the difference between extremes and account for a single coordinate being one unit
        return Math.abs(maxx - minx) + 1;
    }
    public int getYSize(){
        // Redundancy if no tiles or edges are present
        if(tiles.size() == 0 | edges.size() == 0) return 0;
        // If tiles and/or edges exist, take the difference between extremes and account for a single coordinate being one unit
        return Math.abs(maxy - miny) + 1;
    }
    public int getZSize(){
        // Redundancy if no tiles or edges are present
        if(tiles.size() == 0 | edges.size() == 0) return 0;
        // If tiles and/or edges exist, take the difference between extremes and account for a single coordinate being one unit
        return Math.abs(maxz - minz) + 1;
    }
    public List<Tile> getTiles(){
        return this.tiles;
    }
    public List<Edge> getEdges(){
        return this.edges;
    }
    
    // Return a list of tiles and edges; may be an ASCII representation also (unless it is its own function)
    @Override
    public String toString(){
        String str = "Map x width " + this.getXSize() + ", y depth " + this.getYSize() + ", z height " + this.getZSize() + "\n";
        str+="Tiles in map:\n";
        for(Tile tile:tiles){
            str+=tile + "\n";
        }
        str+="\nEdges in map:\n";
        for(Edge edge:edges){
            str+=edge + "\n";
        }
        return str;
    }
}
