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
 * Clase para la resoluci�n de problemas de p-centro mediante la construcci�n voraz determinista de una soluci�n
 */
import java.util.ArrayList;

public class GreedyAlgorithm extends Algorithm{
	/**
	 * M�todo constructor
	 * @param pcp problema del p-centro a resolver
	 */
	public GreedyAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * M�todo para resolver el problema
	 * De 1 a K, se busca la mejor soluci�n siguiente a su tama�o anterior, tomando el mejor elemento
	 * de una lista ordenada de soluciones.
	 * 
	 * Al ser un m�todo bruto y basado en combinaciones, a medida que el problema aumenta en tama�o,
	 * aumentar� el tiempo de ejecuci�n exponencialmente
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
		PCenterProblem pcp = new PCenterProblem(args[0]);
		GreedyAlgorithm ga = new GreedyAlgorithm(pcp);
		//ArrayList<Point> solution = ga.resolve();
		ArrayList<Point> solution = ga.resolve2();
		System.out.println("SOLUTION POINTS = " + solution);
    	System.out.println("OBJECTIVE FUNCTION = " + ga.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
}
