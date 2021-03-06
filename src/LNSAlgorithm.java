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
 * Clase para la resoluci�n de problemas de p-centro mediante LNS
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class LNSAlgorithm extends Algorithm{
	final static String salida = "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\src\\lnsoutput";
	/**
	 * M�todo constructor
	 * @param pcp problema del p-centro a resolver
	 */
	public LNSAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * Metodo para destruir la solucion
	 * Para ello tenemos que eliminar un porcentaje de la solución dependiendo 
	 * de lo que valga k. Por ejemplo: cuando k=1 eliminamos el 10% de los puntos 
	 * de la solución
	 * @return solucion quitando los puntos que hemos destruído, un ArrayList\<Point\>
	 * @param ArrayList<Point> solucion con la solución que creamos con grasp
	 * @param int k el entorno en el que estamos
	 */
	public ArrayList<Point> destroy(ArrayList<Point> solution,int k){
		
		ArrayList<Point> aux = new ArrayList<Point>();
		int destroy_num = solution.size()*(k*10)/100;
		if (destroy_num < 0) {
			destroy_num = 1;
		}
		for (int i = 0; i < destroy_num; i++) {
			int random = (int) (Math.random() * solution.size());
			aux.add(solution.get(random));
		}
		solution.removeAll(aux);
		return solution;
	}	
	
	/*
	 * Metodo para resolver por LNS
	 * 1. Creamos la solucion usando GRASP
	 * 2. Destruimos
	 * 3. Reconstruimos llamando a la lista restringida de candidatos con la solución
	 * que tenemos actualmente
	 * 4. Mejoramos usando LocalSearch
	 * @return solucion ArrayList\<Point\>
	 * @param int RCLSize Tamaño de la lista restringida de candidatos
	 */	
	public ArrayList<Point> resolve(int RCLSize){
		GRASPAlgorithm ga = new GRASPAlgorithm(this.getPcp(), RCLSize);
		ArrayList<Point> firstSolution = ga.resolve();
		ArrayList<Point> actualSolution = new ArrayList<Point>(firstSolution);
		do {
			int k = 1;
			do {
				actualSolution=destroy(actualSolution,k);
				for (int i = 0; i < (firstSolution.size()-actualSolution.size()); i++) {
					actualSolution=ga.nextGRASPSelected(actualSolution);
				}
				ArrayList<Point> improvedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(actualSolution);
				if(getPcp().funcionObjectivo(improvedSolution) < getPcp().funcionObjectivo(actualSolution)) {
						actualSolution = new ArrayList<Point>(improvedSolution);
						k = 1;
				} else {
					k = k + 1;
				}
			} while(k < 5 && actualSolution.size() < getPcp().getSolution().getK());
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
		Files.write(Paths.get(salida),("Prueba sobre " + args[0] + " con algoritmo LNS\n").getBytes(), StandardOpenOption.APPEND);
		PCenterProblem pcp = new PCenterProblem(args[0]);
		LNSAlgorithm lns = new LNSAlgorithm(pcp);
		long startTime = System.nanoTime();
		ArrayList<Point> solution = lns.resolve(3);
		long endTime = System.nanoTime();
		Files.write(Paths.get(salida),("Ha tardado " + (endTime - startTime) + " ns.\n").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("SOLUTION POINTS = " + solution + "\n").getBytes(), StandardOpenOption.APPEND);
		Files.write(Paths.get(salida),("OBJECTIVE FUNCTION = " + lns.getPcp().funcionObjectivo(solution) + "\n").getBytes(), StandardOpenOption.APPEND);
		
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < lns.getPcp().getSolution().getDots().size(); i++) {
			locations.add(lns.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		Files.write(Paths.get(salida),("INDEXES = " + locations).getBytes(), StandardOpenOption.APPEND);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
