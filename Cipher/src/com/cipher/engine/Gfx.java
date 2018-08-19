package com.cipher.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.cipher.sparsematrixtools.Coord;

public class Gfx {
    
    // 8 directions for a facing in a rectangular tiling, clockwise order when on a single z-plane;
    // 2 additional directions (up or down; above or below) when z-plane is moved on.
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
            BELOW       // #9
    }

    // Walls in the Facing order
    private static final int[][][] walls = {    
        // NORTH
        {
            { 0, 0,29,29}, // Polygon x coordinates
            {14,44,59,29}  // Polygon y coordinates
        },
        // NORTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // EAST
        {
            {29,29,59,59}, // Polygon x coordinates
            {29,59,44,14}  // Polygon y coordinates
        },
        // SOUTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // SOUTH
        {
            {29,29,59,59}, // Polygon x coordinates
            { 0,29,44,14}  // Polygon y coordinates
        },
        // SOUTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // WEST
        {
            { 0, 0,29,29}, // Polygon x coordinates
            {14,44,29, 0}  // Polygon y coordinates
        },
        // NORTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // ABOVE - z axis increases
        {
            { 0,29,59,29},//x
            {44,59,44,29} //y
        },
        // BELOW - z axis decreases
        {
            { 0,29,59,29},//x
            {14,29,14, 0} //y
        }
    };
    // Amount of shift in the relative coordinates when plotting something new, allows building a multitile/-edge map
    private static final int[][][] shifts = {    
        // NORTH
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // NORTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // EAST
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // SOUTHEAST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // SOUTH
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // SOUTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // WEST
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // NORTHWEST - redundant for edges
        {
            { 0, 0, 0, 0}, // Polygon x coordinates
            { 0, 0, 0, 0}  // Polygon y coordinates
        },
        // ABOVE - z axis increases
        {
            { 0, 0, 0, 0},//x
            { 0, 0, 0, 0} //y
        },
        // BELOW - z axis decreases
        {
            { 0, 0, 0, 0},//x
            { 0, 0, 0, 0} //y
        }
    };
    
    
    
    // Coordinate for drawing a unit cube with a "west" wall, "east" wall, and a unit tile drawn as a top
    private static final int[][][] unitcube =
    {
        // Left wall
        {
            { 0, 0,29,29},// x
            {14,44,29, 0} // y
        },  
        // Right wall
        {
            {29,29,59,59},// x
            { 0,29,44,14} // y
        },
        // Top tile
        {
            { 0,29,59,29},// x
            {44,59,44,29} // y
        }
    };
    
    // Unit cubes are bitmap images 60 pixels high and 60 pixels wide
    public static final int unitWidth = 60;
    public static final int unitHeight = 60;
    // Some placeholder colors will function as textures in preliminary graphics
    public static final Color texture1 = Color.CYAN;
    public static final Color texture2 = Color.BLUE;
    // The graphics device location for the center from where to position {x,y,z} = {0,0,0}
    public static int xcenter = 0, ycenter = 0;
    // Graphics device canvas size
    public static int xsize = 1000, ysize = 1000;

    public static void setCenter(int x, int y){ xcenter = x; ycenter = y; }
    public static void setSize(int x, int y){ xsize = x; ysize = y; }
    
    // Wrapper; if no location is provided, assume location {0,0,0}
    //public static BufferedImage Rasterize(Tile tile){
    //    return Rasterize(tile, new Coord(0,0,0));
    //}
    // Wrapper; no image buffer is provided, thus using the default unit canvas
    //public static BufferedImage Rasterize(Tile tile, Coord location){
    //    // Tile parameter is currently ignored and everything is drawn as the unit cube for time being to indicate that a tile location exists
    //    BufferedImage bi = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
    //    return Rasterize(tile, location, bi);
    //}
    // Main Rasterize for a tile, with a customizable location and a parameterized buffered image canvas
    public static Graphics2D Rasterize(Tile tile, Coord location, Graphics2D g2d){    
        System.out.println("Drawing tile...");
        //System.out.println("xcenter: " + xcenter + " ; ycenter: " + ycenter);
        //Graphics2D g2d = bi.createGraphics();
        // Affine transformation that flips the y-axis; I prefer the mathematical y-axis where increasing values point upwards in a rasterized 2d image
        //AffineTransform tform = AffineTransform.getTranslateInstance( 0, ysize-1);
        //tform.scale( 1, -1);
        //g2d.setTransform( tform);
        // Draw a placeholder unit cube
        int[][] left = unitcube[0];
        int[][] right = unitcube[1];
        int[][] top = unitcube[2];
        // GRAPHICS DEVICE SHIFTS
        // Computing x and y shifts on the graphics device based on the world Coord {x,y,z}
        int xshift = xcenter, yshift = ycenter;
        // x-axis game object shift on pixel coordinates
        if(location.getX()>0){
            xshift += location.getX()*30;
            yshift += location.getX()*15;
        }else if(location.getX()<0){
            xshift -= location.getX()*30;
            yshift -= location.getX()*15;
        }
        // y-axis game object shift on pixel coordinates
        if(location.getY()>0){
            xshift -= location.getY()*30;
            yshift += location.getY()*15;            
        }else if(location.getY()<0){
            xshift += location.getY()*30;
            yshift -= location.getY()*15;            
        }
        // z-axis game object shift on pixel coordinates
        if(location.getZ()>0){
            // No changes on pixel x-coordinates
            yshift += location.getZ()*60;
        }else if(location.getZ()<0){
            // No changes on pixel x-coordinates
            yshift -= location.getZ()*60;
        }
        System.out.println("Tile : " + tile);
        System.out.println("Final xshift: " + xshift + " ; yshift: " + yshift);        
        // Graphics device coordinates as single dimensional int-arrays
        int[] xleft = left[0], yleft = left[1];
        int[] xright = right[0], yright = right[1];
        int[] xtop = top[0], ytop = top[1];
        // Add the shifts to the ooordinates
        for(int i=0; i<4; i++){ // All the polygons have 4 vertices
            xleft[i] += xshift;
            yleft[i] += yshift;
            xright[i] += xshift;
            yright[i] += yshift;
            xtop[i] += xshift;
            ytop[i] += yshift;
        }
        // Colors for the unit cube
        Color col1 = Color.red;
        Color col2 = Color.green;
        Color col3 = Color.blue;
        // Draw left side of the cube
        g2d.setColor(col1);
        g2d.fillPolygon(xleft, yleft, 4); // Filling
        g2d.setColor(Color.black);
        g2d.drawPolygon(xleft, yleft, 4); // Outline
        // Draw right of the cube
        g2d.setColor(col2);
        g2d.fillPolygon(xright, yright, 4); // Filling
        g2d.setColor(Color.black);
        g2d.drawPolygon(xright, yright, 4); // Outline
        // Draw top of the cube
        g2d.setColor(col3);
        g2d.fillPolygon(xtop, ytop, 4); // Filling
        g2d.setColor(Color.black); 
        g2d.drawPolygon(xtop, ytop, 4); // Outline
        // Wrap up
        //g2d.dispose();
        // Check out what we've rendered
        // DISABLED
        //try{
        //    ImageIO.write((RenderedImage) bi, "png", new File("D:\\tile.png"));
        //}catch(Exception e){
        //    System.out.println("Exception in Cipher.Gfx.Rasterize for a Tile: " + e);
        //}
        //return bi;
        return g2d;
    }
    
    // Wrapper; if no location is provided, assume location {0,0,0}
    //public static BufferedImage Rasterize(Edge edge){
    //    return Rasterize(edge, new Coord(0,0,0));
    //}
    // Wrapper; no image buffer is provided, thus using the default unit canvas
    //public static BufferedImage Rasterize(Edge edge, Coord location){
    //    // For the time being ignore the edge itself and just draw a wall to the corrct facing
    //    BufferedImage bi = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
    //    return Rasterize(edge, location, bi);
    //}
    // Main Rasterize for an edge, with a customizable location and a parameterized buffered image canvas
    public static Graphics2D Rasterize(Edge edge, Coord location, Graphics2D g2d){
        System.out.println("Drawing edge...");
        //System.out.println("xcenter: " + xcenter + " ; ycenter: " + ycenter);
        //Graphics2D g2d = bi.createGraphics();
        // Affine transformation that flips the y-axis; I prefer the mathematical y-axis where increasing values point upwards in a rasterized 2d image
        //AffineTransform tform = AffineTransform.getTranslateInstance( 0, ysize-1);
        //tform.scale( 1, -1);
        //g2d.setTransform( tform);
        // Firstly, determine the facing based on which 
        Facing face = Facing.NORTH;
        if(edge.getX1() == edge.getX2() - 1){
            face = Facing.WEST;
        }else if(edge.getX1() == edge.getX2() + 1){
            face = Facing.EAST;
        }else if(edge.getY1() == edge.getY2() - 1){
            face = Facing.SOUTH;
        }else if(edge.getY1() == edge.getY2() + 1){
            face = Facing.NORTH;
        }else if(edge.getZ1() == edge.getZ2() - 1){
            face = Facing.ABOVE;
        }else if(edge.getZ1() == edge.getZ2() + 1){
            face = Facing.BELOW;
        }else{
            // Throw a Cipher error; edge is not bordering two adjacent tiles?
        }
        System.out.println("Facing: " + face);
        // GRAPHICS DEVICE SHIFTS
        // Computing x and y shifts on the graphics device based on the world Coord {x,y,z}
        int xshift = xcenter, yshift = ycenter;
        // x-axis game object shift on pixel coordinates
        if(location.getX()>0){
            xshift += location.getX()*30;
            yshift += location.getX()*15;
        }else if(location.getX()<0){
            xshift -= location.getX()*30;
            yshift -= location.getX()*15;
        }
        // y-axis game object shift on pixel coordinates
        if(location.getY()>0){
            xshift -= location.getY()*30;
            yshift += location.getY()*15;            
        }else if(location.getY()<0){
            xshift += location.getY()*30;
            yshift -= location.getY()*15;            
        }
        // z-axis game object shift on pixel coordinates
        if(location.getZ()>0){
            // No changes on pixel x-coordinates
            yshift += location.getZ()*60;
        }else if(location.getZ()<0){
            // No changes on pixel x-coordinates
            yshift -= location.getZ()*60;
        }
        System.out.println("Final xshift: " + xshift + " ; yshift: " + yshift);
        // The x and y coordinates on graphic device are obtained from the walls table
        int[] x = walls[face.ordinal()][0];
        int[] y = walls[face.ordinal()][1];
        // Apply the desired shifting to the x and y (graphic) coordinates based on the world Coord location
        // Add the shifts to the ooordinates
        for(int i=0; i<4; i++){ // All the polygons have 4 vertices
            x[i] += xshift;
            y[i] += yshift;
        }
        
        // After obtaining the facing, draw the corresponding coordinates from the walls-table
        // Abusing the enum-notation? Possibly a better way for utilizing the compass directions than current enum implementation
        g2d.setColor(texture1);
        g2d.fillPolygon(x, y, 4);
        g2d.setColor(Color.black);
        g2d.drawPolygon(x, y, 4);
        //g2d.dispose();
        // Check out what we've rendered
        // DISABLED
        //try{
        //    ImageIO.write((RenderedImage) bi, "png", new File("D:\\edge.png"));
        //}catch(Exception e){
        //    System.out.println("Exception in Cipher.Gfx.Rasterize for an Edge: " + e);
        //}
        return g2d;
    }
    

    // Draw a map of tiles and edges; much more complex than drawing either of them alone
    public static RenderedImage Rasterize(
            // Parameter of tiles and edges to be drawn
            Map map//,
            // The coordinate of the top-left most tile that is to be drawn; 
            // This is world coordinate {x,y,z} corresponding to starting the drawing {0,0} pixel coordinate in the graphics device
            //Coord topleft
            // Bitmap image width and height
            //int width,
            //int height
        ){
        // For maps, we have multiple overlapping x, y and z axes; drawing the whole map is non-trivial
        // Compute the need of pixel width and height for drawing the whole map
        // TODO
        //int xmapsize = map.getXSize();
        //int ymapsize = map.getYSize();
        //int zmapsize = map.getZSize();
   
        
        System.out.println("Starting new Map drawing, xsize: " + xsize + " ; ysize: " + ysize);
        BufferedImage bi = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        // Affine transformation that flips the y-axis; I prefer the mathematical y-axis where increasing values point upwards in a rasterized 2d image
        //AffineTransform tform = AffineTransform.getTranslateInstance( 0, ysize-1);
        //tform.scale( 1, -1);
        //g2d.setTransform(tform);
        // Loop over to draw the tiles and edges
        // Get the tiles and edges
        List<Tile> tiles = map.getTiles();
        List<Edge> edges = map.getEdges();
        // Loop over tiles
        for(Tile tile:tiles){ 
            g2d = Rasterize(tile, tile.getCoords(), g2d);
        }
        // Loop over edges
        for(Edge edge:edges){
            g2d = Rasterize(edge, edge.getCoords1(), g2d);
        }
        // Draw the image
        g2d.drawImage(bi, 0, 0, null);
        // Release the graphics device
        g2d.dispose();
        // Check out the rendered Map
        try{
            ImageIO.write((RenderedImage) bi, "png", new File("D:\\map.png"));
        }catch(Exception e){
            System.out.println("Exception in Cipher.Gfx.Rasterize for a Map: " + e);
        }        
        return (RenderedImage) bi;
    }
}
