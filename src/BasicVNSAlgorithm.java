import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
 * Clase para la resoluci�n de problemas de p-centro mediante Greedy Randomized Adaptative Search Procedure (GRASP)
 */
public class BasicVNSAlgorithm extends Algorithm{
	final static String salida = "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\src\\vnsoutput";
	
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
		Files.write(Paths.get(salida),("Prueba sobre " + args[0] + " con algoritmo VNS\n").getBytes(), StandardOpenOption.APPEND);
		PCenterProblem pcp = new PCenterProblem(args[0]);
		BasicVNSAlgorithm bvnsa = new BasicVNSAlgorithm(pcp);
		long startTime = System.nanoTime();
		ArrayList<Point> solution = bvnsa.resolve();
		long endTime = System.nanoTime();
		Files.write(Paths.get(salida),("Ha tardado " + (endTime - startTime) + " ns.\n").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("SOLUTION POINTS = " + solution + "\n").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("OBJECTIVE FUNCTION = " + bvnsa.getPcp().funcionObjectivo(solution) + "\n").getBytes(), StandardOpenOption.APPEND);
	   	
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
