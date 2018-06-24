package SparseMatrixTools;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Syksy
 */
import java.util.List;
import java.util.ArrayList;

public class SparseMatrix{
        // Coordinates for non-null elements in the sparse matrix
	private List<Coord> coords = new ArrayList<Coord>();
        // The elements corresponding to the coordinate indices
        private List<Element> elements = new ArrayList<Element>();
        // Total number of non-null elements        
        private int n = 0;
        
	/*
	 * CONSTRUCTORS
	 */
	public SparseMatrix(){}
        
        /*
	 *
	 */
	public void addElement(int x, int y, int z, Element e){
            Coord xyz = new Coord(x, y, z);
            this.coords.add(xyz);
            this.elements.add(e);
            this.n++;
	}
        public void addElement(Coord xyz, Element e){
            this.coords.add(xyz);
            this.elements.add(e);
            this.n++;
        }
        
        
        // TODO
	// Return the whole sparse {x,y,z} cube representation
	public Element[][][] getSparseCube(){
            return new Element[0][0][0];
	}
	// Return a slice of {x,y} at coordinate z
	public Element[][] getSlice(int z){
            return new Element[0][0];
	}
	// Return the number of non empty elements in the sparse matrix/cube
	public int getSize(){
		return this.coords.size();
	}

	// TODO: Always sort the sparse matrix coordinates in order: z-axis, y-axis, x-axis
}
