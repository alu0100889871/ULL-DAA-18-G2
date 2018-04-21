package pcenterproblem;

import java.util.ArrayList;

public class Solution {
	private ArrayList<Point> solution;
	private int k;
	
	public Solution() {;}
	public Solution (int k, ArrayList<Point> solution) {
		this.k = k;
		this.solution = new ArrayList<Point>(solution);
	}
	
	public ArrayList<Point> getSolution() { return solution; }
	public void setSolution(ArrayList<Point> solution) { this.solution = solution; }
	
	public int getK() { return k; }
	public void setK(int k) { this.k = k; }
	
	public String toString() {
		return "Solution [K=" + k + ", solution=" + solution + "]";
	}
	
}
