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
 * Definition of a p-center Problem.
 *
 */
public class PCenterProblem {
	private Matrix values;
	private Solution solution;
	
	/**
	 * Constructor of a p-center Problem.
	 * @param k int number of services.
	 * @param dots ArrayList<Point> array with all the client nodes.
	 */
	public PCenterProblem(int k, ArrayList<Point> dots) {
		this.solution = new Solution(k, dots);
		this.values = new Matrix(dots);
	}
	/**
	 * Constructor using a file.
	 * @param path String file path.
	 */
	public PCenterProblem(String path) {
		PCenterProblem pcp = new PCenterProblemReader(path).generate();
		this.values = new Matrix(pcp.getValues().getDots());
		this.solution = pcp.getSolution();
	}
	
	/**
	 * Getter of the matrix that stores the distances.
	 * @return Matrix matrix of the problem.
	 */
	public Matrix getValues() {
		return values;
	}
	/**
	 * Setter of Matrix that stores the distances between the nodes.
	 * @param values Matrix entry matrix of the problem.
	 */
	public void setValues(Matrix values) {
		this.values = values;
	}
	/**
	 * Getter of the p-center problem solution stored.
	 * @return Solution solution stored.
	 */
	public Solution getSolution() {
		return solution;
	}
	/**
	 * Setter of the p-center problem solution.
	 * @param solution Solution solution to store.
	 */
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	/**
	 * M�todo para el c�lculo de la funci�n objetivo seg�n la soluci�n establecida previamente
	 * @return el total de la soluci�n obtenida. Es decir, la distancia m�xima desde los servicios a los clientes
	 */
	public double funcionObjectivo() {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < getSolution().getDots().size(); i++) {
			double min = Double.MAX_VALUE;
			for(int j = 0; j < getSolution().getK(); j++) {
				int index = getValues().getDots().indexOf(getSolution().getDots().get(j));
				if(min > getValues().getDistance(index, i)){
					min = getValues().getDistance(index, i);
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
	/**
	 * M�todo para el c�lculo de la funci�n objetivo en funci�n de unos nodos clientes
	 * @return el total de la soluci�n obtenida. Es decir, la distancia m�xima desde los servicios a los clientes
	 */
	public double funcionObjectivo(ArrayList<Point> servicesDots) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < getValues().getDots().size(); i++) {
			double min = Double.MAX_VALUE;
			for(int j = 0; j < servicesDots.size(); j++) {
				int index = getValues().getDots().indexOf(servicesDots.get(j));
				//System.out.println("Point-> " + servicesDots.get(j));
				//System.out.println("i-> " + index);
				if(min > getValues().getDistance(index, i)){
					min = getValues().getDistance(index, i);
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
		ArrayList<Point> solution = new ArrayList<Point>();
		solution.add(new Point(2,3));
		solution.add(new Point(10,17));
		solution.add(new Point(8,14));
		System.out.println(solution);
		System.out.println(pcp.getValues().getDots().indexOf(new Point(8,14)));
		System.out.println("objF = " + pcp.funcionObjectivo(solution));
	}
}
