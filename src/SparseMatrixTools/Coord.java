package SparseMatrixTools;

/**
 *
 * @author Syksy
 */

public class Coord{
    // {x,y,z} coords always hold 3 coordinates; by default they are zero
    protected int x, y, z;
    
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
}


