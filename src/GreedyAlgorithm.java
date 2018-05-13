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
 * Clase para la resolución de problemas de p-centro mediante la construcción voraz determinista de una solución
 */
import java.util.ArrayList;
import java.nio.file.*;



public class GreedyAlgorithm extends Algorithm{
	final static String salida = "output";
	/**
	 * Método constructor
	 * @param pcp problema del p-centro a resolver
	 */
	public GreedyAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * Método para resolver el problema
	 * De 1 a K, se busca la mejor solución siguiente a su tamaño anterior, tomando el mejor elemento
	 * de una lista ordenada de soluciones.
	 * 
	 * Al ser un método bruto y basado en combinaciones, a medida que el problema aumenta en tamaño,
	 * aumentará el tiempo de ejecución exponencialmente
	 * 
	 * @return solucion final, un ArrayList\<Point\>
	 */
    public ArrayList<Point> resolve() {
    	ArrayList<Point> solution = new ArrayList<Point>();
    	for (int i = 0; i < getPcp().getSolution().getK(); i++) {
    		solution = new ArrayList<Point>(this.makeOrderedCombinations(solution).get(0));
    	}
    	getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
    	return solution;
    }
    
    public ArrayList<Point> resolve2() {
    	ArrayList<ArrayList<Point>> combs = new ArrayList<ArrayList<Point>>(getCombinations(getPcp().getSolution().getK()));
    	ArrayList<Point> solution = new ArrayList<Point>();
    	while(!combs.isEmpty()) {
    		if(getPcp().funcionObjectivo(solution) > getPcp().funcionObjectivo(combs.get(0))) {
    			solution = new ArrayList<Point>(combs.get(0));
    		}
    		combs.remove(0);
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
		GreedyAlgorithm ga = new GreedyAlgorithm(pcp);
		//ArrayList<Point> solution = ga.resolve();
		long startTime = System.nanoTime();
		ArrayList<Point> solution = ga.resolve2();
		long endTime = System.nanoTime();
        Files.write(Paths.get(salida),("Ha tardado " + (endTime - startTime) + " ns.").getBytes(), StandardOpenOption.APPEND);
        Files.write(Paths.get(salida),("SOLUTION POINTS = " + solution).getBytes(), StandardOpenOption.APPEND);
        Files.write(Paths.get(salida),("OBJECTIVE FUNCTION = " + ga.getPcp().funcionObjectivo(solution)).getBytes(), StandardOpenOption.APPEND);
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		Files.write(Paths.get(salida),("INDEXES = " + locations).getBytes(), StandardOpenOption.APPEND);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
