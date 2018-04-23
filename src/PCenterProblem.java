import java.util.ArrayList;

public class PCenterProblem {
	private Matrix values;
	private Solution solution;
	
	public PCenterProblem(int k, ArrayList<Point> dots) {
		this.values = new Matrix(dots);
		this.solution = new Solution(k, new ArrayList<Point>());
	}
	
	public PCenterProblem(String path) {
		PCenterProblem pcp = new PCenterProblemReader(path).generate();
		this.values = new Matrix(pcp.getValues().getDots());
		this.solution = pcp.getSolution();
	}
	
	/**
	 * Getter of the matrix that stores the distances.
	 * @return Matrix
	 */
	public Matrix getValues() {
		return values;
	}
	/**
	 * Setter of Matrix that stores the distances between the nodes.
	 * @param Matrix entry matrix of the problem.
	 */
	public void setValues(Matrix values) {
		this.values = values;
	}
	/**
	 * Getter of the p-center problem solution stored
	 * @return solution stored
	 */
	public Solution getSolution() {
		return solution;
	}
	/**
	 * Setter of the p-center problem solution
	 * @param solution to store
	 */
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	/**
	 * Method to calculate de objective function of the problem, given a list of services and clients
	 * @param services service nodes selected for the problem definition
	 * @param clients client nodes selected for the problem definition
	 * @return the result of the objective function
	 */
	public double funcionObjectivo(ArrayList<Integer> services, ArrayList<Integer> clients) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < clients.size(); i++) {
			double min = Double.MAX_VALUE;
			for(int j = 0; j < services.size(); j++) {
				if(services.get(j) < values.getDots().size() && clients.get(i) < values.getDots().size() 
						&& min > values.getDistance(services.get(j), clients.get(i))){
					min = values.getDistance(services.get(j), clients.get(i));
				}
			}
			distances.add(min);
		}
		double max = Double.MIN_VALUE;
		for (int i = 0; i < distances.size(); i++) {
			if(max < distances.get(i)) {
				max = distances.get(i);
			}
		}
		return max;
	}
	
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		System.out.println(pcp.getValues());
		System.out.println("K:" + pcp.getSolution().getK());
		System.out.println("Dots: " + pcp.getValues().getDots());
	}
}
