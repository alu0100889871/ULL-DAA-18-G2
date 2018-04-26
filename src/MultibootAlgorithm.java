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
 * Clase para la resoluci�n de problemas de p-centro mediante multiarranque
 */
import java.util.ArrayList;

public class MultibootAlgorithm extends Algorithm {
	private int bootTimes;
	private final int GRASP_RCL = 3;
	
	/**
	 * M�todo constructor
	 * @param pcp problema del p-centro a resolver
	 * @param bootTimes l�mite de repeticiones del arranque como condici�n de parada
	 */
	public MultibootAlgorithm(PCenterProblem pcp, int bootTimes) {
		super(pcp);
		this.bootTimes = bootTimes;
	}
	/**
	 * M�todo para resolver el problema
	 * Mientras (i < bootTimes)
	 * 		1. generaci�n de soluci�n actual X mediante GRASP
	 * 		2. generaci�n de soluci�n mejorada X' mediante b�squeda local exhaustiva
	 * 		3. si fObjectivo(X > X') soluci�n temporal = X'
	 * 				3.1 en caso contrario, soluci�n = X
	 * 		4. si fObjetivo(soluci�n) > fObjetivo(soluci�n temporal) 
	 * @return solucion final, un ArrayList\<Point\>
	 */
	public ArrayList<Point> resolve(){
		ArrayList<Point> solution = new ArrayList<Point>(new GRASPAlgorithm(getPcp(), GRASP_RCL).resolve());
		for (int i = 0; i < bootTimes; i++) {
			ArrayList<Point> actualSolution = new GRASPAlgorithm(getPcp(), GRASP_RCL).resolve();
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
		PCenterProblem pcp = new PCenterProblem(args[0]);
		MultibootAlgorithm mba = new MultibootAlgorithm(pcp, 10);
		
		ArrayList<Point> solution = mba.resolve();
		System.out.println("SOLUTION POINTS = " + solution);
    	System.out.println("OBJECTIVE FUNCTION = " + mba.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < mba.getPcp().getSolution().getDots().size(); i++) {
			locations.add(mba.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}

}
