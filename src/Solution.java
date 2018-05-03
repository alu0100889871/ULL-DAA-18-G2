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
	private ArrayList<Point> dots;
	private int k; //numeros de servicios.
	private double bestFObj;
	
	/**
	 * Solution default constructor.
	 */
	public Solution() {
		this.dots = new ArrayList<Point>();
		this.k = 0;
		this.bestFObj = 0;
	}
	/**
	 * Solution constructor with given parameters.
	 * @param k Number of services.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public Solution (int k, ArrayList<Point> dots) {
		this.dots = new ArrayList<Point>(dots);
		this.k = k;
		this.bestFObj = 0.0;
	}
	/**
	 * Getter of the solution.
	 * @return ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public ArrayList<Point> getDots() {
		return this.dots;
	}
	/**
	 * Setter of the solution.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public void setDots(ArrayList<Point> dots) {
		this.dots = dots;
	}
	/**
	 * Getter of the solution cost.
	 * @return cost.
	 */
	public double getBestFObj(){
		return this.bestFObj;
	}
	/**
	 * Setter of the solution cost.
	 * @param int cost.
	 */
	public void setBestFObj(double bestFObj){
		this.bestFObj = bestFObj;
	}
	/**
	 * Getter of the number of services.
	 * @return Number of services.
	 */
	public int getK() {
		return this.k;
	}
	/**
	 * Setter of the number of services.
	 * @param k Number of services.
	 */
	public void setK(int k) {
		this.k = k;
	}
	/**
	 * toString method
	 * @return string Solution as a string.
	 */
	public String toString() {
		return "Solution [K = " + k + ", solution = " + dots + "]";
	}

}
