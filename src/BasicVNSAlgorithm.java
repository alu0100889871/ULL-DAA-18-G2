import java.util.ArrayList;

public class BasicVNSAlgorithm extends Algorithm{
	
	public BasicVNSAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * Metodo de agitaci�n para conseguir soluciones vecinas
	 * @param initialSolution soluci�n inicial que modificar
	 * @param kNeighborhood el n�mero identificativo del entorno en el que se mover�
	 * @return la nueva soluci�n vecina de la inicial
	 */
	public ArrayList<Point> shake(ArrayList<Point> initialSolution, int kNeighborhood){
		ArrayList<Point> shakedSolution = new ArrayList<Point>();
		ArrayList<ArrayList<Point>> combs;
		switch(kNeighborhood) {
			case 1:
				combs = this.getKCombinations(initialSolution);
				shakedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(combs.get((int)(Math.random() * combs.size())));
				break;
			default:
				combs = this.getKCombinations(initialSolution);
				shakedSolution = new LocalSearchMaker(getPcp()).randomKLocationChangeSearch(combs.get((int)(Math.random() * combs.size())), kNeighborhood);
				break;
		}
		return shakedSolution;
	}
	/**
	 * Function VNS (x, kmax, tmax );
     * repeat
 	 * 		k <- 1;
	 * 		repeat
	 * 			x' <- Shake(x, k) ;
	 * 			x'' <- FirstImprovement(x' )  Local search ;
	 * 			if f(x'') < f(x)
	 * 				x = x''
	 * 				k <- 1
	 * 			else
	 * 				k <- k + 1
	 * 		until k = k_max ;
	 * 		t <- t + 1
 	 * until t > t_max ;
 	 * return x
	 * @return ArrayList<Point> soluci�n del problema
	 */
	public ArrayList<Point> resolve(){
		ArrayList<Point> actualSolution = new ArrayList<Point>();
		do {
			int k = 1;
			do {
				ArrayList<Point> shakedSolution = new ArrayList<Point>(shake(actualSolution, k));
				ArrayList<Point> improvedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(shakedSolution);
				if(getPcp().funcionObjectivo(improvedSolution) < getPcp().funcionObjectivo(actualSolution)) {
						actualSolution = new ArrayList<Point>(improvedSolution);
						k = 1;
				} else {
					k = k + 1;
				}
			} while(k < getPcp().getSolution().getK() && actualSolution.size() < getPcp().getSolution().getK());
		} while(actualSolution.size() < getPcp().getSolution().getK());
		getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(actualSolution));
    	getPcp().getSolution().setDots(actualSolution);
		return actualSolution;
	}
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		BasicVNSAlgorithm bvnsa = new BasicVNSAlgorithm(pcp);
		
		ArrayList<Point> solution = bvnsa.resolve();
		System.out.println("SOLUTION POINTS = " + solution);
	   	System.out.println("OBJECTIVE FUNCTION = " + bvnsa.getPcp().funcionObjectivo(solution));
	   	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < bvnsa.getPcp().getSolution().getDots().size(); i++) {
			locations.add(bvnsa.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
}
