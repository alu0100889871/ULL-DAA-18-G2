import java.util.ArrayList;
/**
 * 
 * @author Sebastián José Díaz Rodríguez
 * @author Ernesto Echeverría González
 * @author Norberto García Gaspar
 * @author Victoria Quintana Martí
 * 
 * @version 1.0.0
 * @since 17/04/2018
 *
 * Class to store a p-center solution as an array.
 *
 */
public class Solution {
	private ArrayList<Point> solution;
	private int k;					   
	
	/**
	 * Solution default constructor.
	 */
	public Solution() {
		this.solution = new ArrayList<Point>();
		this.k = 0;
	}
	/**
	 * Solution constructor with given parameters.
	 * @param k int Number of services.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public Solution (int k, ArrayList<Point> solution) {
		this.k = k;
		this.solution = new ArrayList<Point>(solution);
	}
	/**
	 * Getter of the solution.
	 * @return ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public ArrayList<Point> getSolution() {
		return this.solution; 
	}
	/**
	 * Setter of the solution.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public void setSolution(ArrayList<Point> solution) { 
		this.solution = solution; 
	}
	/**
	 * Getter of the number of services.
	 * @return int Number of services.
	 */
	public int getK() { 
		return this.k; 
	}
	/**
	 * Setter of the number of services.
	 * @param k int Number of services.
	 */
	public void setK(int k) { 
		this.k = k; 
	}
	/**
	 * toString method
	 * @return string Solution as a string.
	 */
	public String toString() {
		return "Solution [K = " + k + ", solution = " + solution + "]";
	}
	
}
