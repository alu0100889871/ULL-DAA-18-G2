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
 * Multiboot algorithm
 *
 */
public class Multiboot {
	private int parameter;
	
	/**
	 * Multiboot constructor
	 * @param parameter int Number of iterations
	 */
	public Multiboot(int parameter) {
		this.parameter = parameter;
	}
	
	public Solution run(Matrix m, ArrayList<Integer> localizations, int max_localizations, int max_iterations, int max_no_improv, double greediness_factor) {
		// Generating solution
		Solution grasp = GRASP.Grasp(m, localizations, max_localizations, max_iterations, max_no_improv, greediness_factor);
		double objectiveFValue = objectiveFunction(new ArrayList<Integer>(localizations.subList(0, max_localizations - 1)), new ArrayList<Integer>(localizations.subList(max_localizations, localizations.size() - 1)), m);
		
		for(int i = 0; i < getParameter(); i++) {
			Solution factible = GRASP.Grasp(m, localizations, max_localizations, max_iterations, max_no_improv, greediness_factor);
			double factObjectiveFValue = objectiveFunction(new ArrayList<Integer>(localizations.subList(0, max_localizations - 1)), new ArrayList<Integer>(localizations.subList(max_localizations, localizations.size() - 1)), m);
			Solution localS = GRASP.localSearch(factible, m, localizations, max_localizations, max_no_improv);
			double localSObjectiveFValue = objectiveFunction(new ArrayList<Integer>(localizations.subList(0, max_localizations - 1)), new ArrayList<Integer>(localizations.subList(max_localizations, localizations.size() - 1)), m);
			if(factObjectiveFValue > localSObjectiveFValue) {
				factible = localS;
			}
			if(objectiveFValue > factObjectiveFValue) {
				grasp = factible;
			}
		}
		return grasp;
	}
	
	public double objectiveFunction(ArrayList<Integer> services, ArrayList<Integer> clients, Matrix m) {
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < clients.size(); i++) {
			double min = Double.MAX_VALUE;
			for(int j = 0; j < services.size(); j++) {
				if(services.get(j) < m.getDots().size() && clients.get(i) < m.getDots().size() 
						&& min > m.getDistance(services.get(j), clients.get(i))){
					min = m.getDistance(services.get(j), clients.get(i));
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
	 * Getter of the parameter attribute.
	 * @return int the parameter attribute
	 */
	public int getParameter() {
		return this.parameter;
	}
}