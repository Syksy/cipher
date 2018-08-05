package com.cipher.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gfx {
    
    // 8 directions for a facing in a rectangular tiling, clockwise order when on a single z-plane;
    // 2 additional directions (up or down; above or below) when z-plane is being moved on.
    public enum Facing { 
            NORTH,      // #0
            NORTHEAST,  // #1
            EAST,       // #2
            SOUTHEAST,  // #3
            SOUTH,      // #4
            SOUTHWEST,  // #5
            WEST,       // #6
            NORTHWEST,  // #7
            ABOVE,      // #8
            BELOW      // #9
    }

    // Walls in the Facing order
    private final int[][][] walls = {    
        // NORTH
        {
            { 1, 1,30,30}, // Polygon x coordinates
            {15,45,60,30}  // Polygon y coordinates
        },
        // NORTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // EAST
        {
            {30,30,60,60}, // Polygon x coordinates
            {30,60,45,15}  // Polygon y coordinates
        },
        // SOUTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // SOUTH
        {
            {30,30,60,60}, // Polygon x coordinates
            { 1,30,45,15}  // Polygon y coordinates
        },
        // SOUTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // WEST
        {
            { 1, 1,30,30}, // Polygon x coordinates
            {15,45,30, 1}  // Polygon y coordinates
        },
        // NORTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // ABOVE - z axis increases
        {
            { 0, 0, 0, 0},
            { 0, 0, 0, 0}
        },
        // BELOW - z axis decreases
        {
            { 0, 0, 0, 0},
            { 0, 0, 0, 0}
        }
    };
    
    // Coordinate for drawing a unit cube with a "west" wall, "east" wall, and a unit tile drawn as a top
    private final int[][][] unitcube =
    {
        // Left wall
        {
            { 0, 0,29,29}, // x
            {14,44,29, 0} // y
        },  
        // Right wall
        {
            {29,29,59,59}, // x
            { 0,29,44,14} // y
        },
        // Top tile
        {
            { 0,29,59,29}, // x
            {44,59,44,29} // y
        }
    };
    
    // Unit cubes are bitmap images 60 pixels high and 60 pixels wide
    public final int unitWidth = 60;
    public final int unitHeight = 60;
    // Some placeholder colors will function as textures in preliminary graphics
    public final Color texture1 = Color.CYAN;
    public final Color texture2 = Color.BLUE;
    
    // Generate 60x60 raster PNG images of a tile
    // Draw a tile representation
    public RenderedImage Rasterize(Tile tile){
        // Tile parameter is currently ignored and everything is drawn as the unit cube for time being to indicate that a tile location exists
        BufferedImage bi = new BufferedImage(unitWidth, unitHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        // Draw a placeholder unit cube
        int[][] left = unitcube[0];
        int[][] right = unitcube[1];
        int[][] top = unitcube[2];
        // Colors for the unit cube
        Color col1 = Color.red;
        Color col2 = Color.green;
        Color col3 = Color.blue;
        // Draw left
        //for(int i=1; i<left.length; i++){
        g2d.setColor(col1);
        g2d.fillPolygon(left[0], left[1], 4);
        g2d.setColor(Color.black);
        g2d.drawPolygon(left[0], left[1], 4);
        //}
        // Draw right
        //for(int i=1; i<right.length; i++){
        g2d.setColor(col2);
        g2d.fillPolygon(right[0], right[1], 4);
        g2d.setColor(Color.black);
        g2d.drawPolygon(right[0], right[1], 4);
        //}
        // Draw top
        //for(int i=1; i<top.length; i++){
        g2d.setColor(col3);
        g2d.fillPolygon(top[0], top[1], 4);
        g2d.setColor(Color.black);
        g2d.drawPolygon(top[0], top[1], 4);
        //}
        g2d.dispose();
        // Check out what we've rendered
        try{
            ImageIO.write((RenderedImage) bi, "png", new File("D:\\tile.png"));
        }catch(Exception e){
            System.out.println("Exception in Cipher.Gfx.Rasterize for a Tile: " + e);
        }
        return (RenderedImage) bi;
    }
    // Generate 60x60 raster PNG images of an edge
    // Draw an edge representation
    public RenderedImage Rasterize(Edge edge){
        // For the time being ignore the edge itself and just draw a wall to the corrct facing
        BufferedImage bi = new BufferedImage(unitWidth, unitHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        // Firstly, determine the facing based on which 
        Facing face;
        
        // TODO
        g2d.dispose();
        return (RenderedImage) bi;
    }
    // Draw a map of tiles and edges; much more complex than drawing either of them alone
    public RenderedImage Rasterize(Map map){
        // For maps, we have multiple overlapping x, y and z axes; drawing the whole map is non-trivial
        BufferedImage bi = new BufferedImage(unitWidth, unitHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        // TODO
        g2d.dispose();
        return (RenderedImage) bi;
    }
}
