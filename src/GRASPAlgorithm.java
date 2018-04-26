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
 * Clase para la resolución de problemas de p-centro mediante Greedy Randomized Adaptative Search Procedure (GRASP)
 */
import java.util.ArrayList;

public class GRASPAlgorithm extends Algorithm{
	/**
	 * Entero que marca el tamaño de la lista de posibles candidatos
	 */
	private int RCLSize;
	/**
	 * Método constructor
	 * @param pcp problema del p-centro a resolver
	 * @param RCLSize tamaño del que se quiera hacer la lista de candidatos
	 */
	public GRASPAlgorithm(PCenterProblem pcp, int RCLSize) {
		super(pcp);
		this.RCLSize = RCLSize;
	}
	/**
	 * Método para resolver el problema
	 * Hasta que se forme una solución factible:
	 * 		1. construcción del siguiente candidato
	 * 		2. Adición de ese candidato a la solución
	 * 		3. Mejora de la solución actual mediante búsqueda local exhaustiva
	 * @return solucion final, un ArrayList\<Point\>
	 */
	public ArrayList<Point> resolve() {
    	ArrayList<Point> solution = new ArrayList<Point>();
    	for (int i = 0; i < getPcp().getSolution().getK(); i++) {
    		solution = new ArrayList<Point>(nextGRASPSelected(solution));
    		solution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(solution);
    	}
    	getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
    	return solution;
    }
	/**
	 * Constructor del siguiente candidato a añadir a la solución
	 * Dada una lista ordenada de candidatos vecinos, coger de los (RCLSize) mejores, uno aleatorio
	 * @param initialSolution solución inicial de la que se parte para obtener la posible solución siguiente
	 * @return ArrayList\<Point\>, solución con el elemento añadido
	 */
	public ArrayList<Point> nextGRASPSelected(ArrayList<Point> initialSolution) {
		ArrayList<ArrayList<Point>> combinations = this.makeOrderedCombinations(initialSolution);
		int randomSelected = (int) (Math.random() * RCLSize);
		return combinations.get(randomSelected);
	}
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		GRASPAlgorithm ga = new GRASPAlgorithm(pcp, 3);
		
		ArrayList<Point> solution = ga.resolve();
		System.out.println("SOLUTION POINTS = " + solution);
    	System.out.println("OBJECTIVE FUNCTION = " + ga.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
}
