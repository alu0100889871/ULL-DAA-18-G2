import java.util.ArrayList;
/**
 *
 * @author Sebasti�n Jos� D�az Rodr�guez
 * @author Ernesto Echeverr�a Gonz�lez
 * @author Norberto Garc�a Gaspar
 * @author Victoria Quintana Mart�
 *
 * @version 1.0.0
 * @since 17/04/2018
 *
 * Class to store a p-center solution as an array.
 *
 */
public class Solution {
	private ArrayList<Integer> solution;
	double costSolution;
	private int k;

	/**
	 * Solution default constructor.
	 */
	public Solution() {
		this.solution = new ArrayList<Integer>();
		this.k = 0;
		this.costSolution = 0;
	}
	/**
	 * Solution constructor with given parameters.
	 * @param k int Number of services.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public Solution (int k, ArrayList<Integer> solution) {
		this.k = k;
		this.solution = new ArrayList<Integer>(solution);
	}
	/**
	 * Getter of the solution.
	 * @return ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public ArrayList<Integer> getSolution() {
		return this.solution;
	}
	/**
	 * Getter of the solution cost.
	 * @return int cost.
	 */
	public double getCostSolution(){
		return this.costSolution;
	}
	/**
	 * Setter of the solution cost.
	 * @param int cost.
	 */
	public void setCostSolution(double costSolution){
		this.costSolution = costSolution;
	}
	/**
	 * Setter of the solution.
	 * @param solution ArrayList<Point> Array with all the nodes. The first k-points are the services.
	 */
	public void setSolution(ArrayList<Integer> solution) {
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
