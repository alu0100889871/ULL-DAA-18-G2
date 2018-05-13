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
 * Clase para la resolución de problemas de p-centro mediante multiarranque
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MultibootAlgorithm extends Algorithm {
	final static String salida = "";
	private int bootTimes;
	
	/**
	 * Método constructor
	 * @param pcp problema del p-centro a resolver
	 * @param bootTimes límite de repeticiones del arranque como condición de parada
	 */
	public MultibootAlgorithm(PCenterProblem pcp, int bootTimes) {
		super(pcp);
		this.bootTimes = bootTimes;
	}
	/**
	 * Método para resolver el problema
	 * Mientras (i < bootTimes)
	 * 		1. generación de solución actual X mediante GRASP
	 * 		2. generación de solución mejorada X' mediante búsqueda local exhaustiva
	 * 		3. si fObjectivo(X > X') solución temporal = X'
	 * 				3.1 en caso contrario, solución = X
	 * 		4. si fObjetivo(solución) > fObjetivo(solución temporal) 
	 * @return solucion final, un ArrayList\<Point\>
	 */
	public ArrayList<Point> resolve(int grasp_rcl){
		ArrayList<Point> solution = new ArrayList<Point>(new GRASPAlgorithm(getPcp(), grasp_rcl).resolve());
		for (int i = 0; i < bootTimes; i++) {
			ArrayList<Point> actualSolution = new GRASPAlgorithm(getPcp(), grasp_rcl).resolve();
			ArrayList<Point> upgradedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(actualSolution);
			ArrayList<Point> bestSolution = new ArrayList<Point>();
			if(getPcp().funcionObjectivo(actualSolution) > getPcp().funcionObjectivo(upgradedSolution)) {
				bestSolution = new ArrayList<Point>(upgradedSolution);
			} else {
				bestSolution = new ArrayList<Point>(actualSolution);
			}
			if(getPcp().funcionObjectivo(solution) > getPcp().funcionObjectivo(bestSolution)) {
				solution = bestSolution;
			}
		}
		getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
		return solution;
	}
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		try {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		MultibootAlgorithm mba = new MultibootAlgorithm(pcp, Integer.parseInt(args[1]));
		
		long startTime = System.nanoTime();
		ArrayList<Point> solution = mba.resolve(Integer.parseInt(args[2]));
		long endTime = System.nanoTime();
		Files.write(Paths.get(salida),("Ha tardado " + (endTime - startTime) + " ns.").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("SOLUTION POINTS = " + solution).getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("OBJECTIVE FUNCTION = " + mba.getPcp().funcionObjectivo(solution)).getBytes(), StandardOpenOption.APPEND);
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < mba.getPcp().getSolution().getDots().size(); i++) {
			locations.add(mba.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		Files.write(Paths.get(salida),("INDEXES = " + locations).getBytes(), StandardOpenOption.APPEND);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
