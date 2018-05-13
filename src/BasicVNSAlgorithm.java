import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
 * Clase para la resolución de problemas de p-centro mediante Greedy Randomized Adaptative Search Procedure (GRASP)
 */
public class BasicVNSAlgorithm extends Algorithm{
	final static String salida = "";
	
	public BasicVNSAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * Metodo de agitación para conseguir soluciones vecinas
	 * @param initialSolution solución inicial que modificar
	 * @param kNeighborhood el número identificativo del entorno en el que se moverá
	 * @return la nueva solución vecina de la inicial
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
	 * @return ArrayList<Point> solución del problema
	 */
	public ArrayList<Point> resolve(){
		ArrayList<Point> actualSolution = new ArrayList<Point>();
		do {
			int k = 1;
			do {
				System.out.println("ActualSolution " + actualSolution.toString());
				ArrayList<Point> shakedSolution = new ArrayList<Point>(shake(actualSolution, k));
				System.out.println("shakedSolution " + shakedSolution.toString());
				ArrayList<Point> improvedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(shakedSolution);
				
				System.out.println("ImrpoveSolution " + improvedSolution.toString());
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
		try {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		BasicVNSAlgorithm bvnsa = new BasicVNSAlgorithm(pcp);
		long startTime = System.nanoTime();
		ArrayList<Point> solution = bvnsa.resolve();
		long endTime = System.nanoTime();
		Files.write(Paths.get(salida),("Ha tardado " + (endTime - startTime) + " ns.").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("SOLUTION POINTS = " + solution).getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("OBJECTIVE FUNCTION = " + bvnsa.getPcp().funcionObjectivo(solution)).getBytes(), StandardOpenOption.APPEND);
	   	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < bvnsa.getPcp().getSolution().getDots().size(); i++) {
			locations.add(bvnsa.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		Files.write(Paths.get(salida),("INDEXES = " + locations).getBytes(), StandardOpenOption.APPEND);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
