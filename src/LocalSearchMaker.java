import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Clase base para todos los algoritmos de b�squeda local para el problema del p-centro
 * 
 * formato de las funciones:
 * public [ArrayList de Point] funcion (pcp, solucionInicial)
 */
public class LocalSearchMaker extends Algorithm {
	
	public LocalSearchMaker(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * B�squeda exhaustiva de la mejor soluci�n vecina a la soluci�n inicial.
	 * Al haber una mejora, esta se vuelve la soluci�n inicial y se comienza
	 * el proceso de nuevo. As� hasta que sea la mejor soluci�n de su entorno.
	 * 
	 * @param initialSolution soluci�n inicial dispensada como punto de partida de la b�squeda
	 * @return nueva soluci�n mejorada, en caso de haberla
	 */
	public ArrayList<Point> exhaustiveSingleLocationChangeSearch(ArrayList<Point> initialSolution){
		boolean upgraded = true;
		ArrayList<Point> actualSolution = new ArrayList<Point>(initialSolution);
		while(upgraded && initialSolution.size() > 1) {
			upgraded = false;
			ArrayList<Point> root = new ArrayList<Point>(actualSolution);
			root.remove(actualSolution.get(actualSolution.size() - 1));
			ArrayList<ArrayList<Point>> combinations = getKCombinations(root);
			combinations.remove(actualSolution);
			for(int j = 0; j < combinations.size(); j++) {
				if(getPcp().funcionObjectivo(combinations.get(j)) < getPcp().funcionObjectivo(actualSolution)) {
					upgraded = true;
					actualSolution = new ArrayList<Point>(combinations.get(j));
				}
			}
		} 
		return actualSolution;
	}
	
	public ArrayList<Point> randomKLocationChangeSearch(ArrayList<Point> initialSolution, int maxK){
		//LIMITA LA RANDOMIZACION AL NUMERO DE ELEMENTOS DEL VECTOR
		if(initialSolution.size() < maxK) {
			maxK = initialSolution.size();
		}
		//CONSIGUE LAS POSICIONES ALEATORIAS
		Set<Integer> positionsToRandomize = new HashSet<Integer>();
		while(positionsToRandomize.size() < maxK) {
			positionsToRandomize.add((int) (Math.random() * initialSolution.size()));
		}
		List<Integer> positions = new ArrayList<Integer>(positionsToRandomize);
		
		for (int i = 0; i < positions.size(); i++) {
			ArrayList<Point> newSolution = new ArrayList<Point>(initialSolution);
			Point p = newSolution.get(positions.get(i));
			
			newSolution.remove((int) positions.get(i));
			ArrayList<ArrayList<Point>> kCombinations = getKCombinations(newSolution);
			newSolution.add(p);
			kCombinations.remove(newSolution);
			
			for (int j = 0; j < kCombinations.size(); j++) {
				if (getPcp().funcionObjectivo(kCombinations.get(j)) < getPcp().funcionObjectivo(initialSolution)) {
					initialSolution = getFixedSolution(kCombinations.get(j), newSolution, positions.get(i));
					j = kCombinations.size();
				}
			}
		}
		return initialSolution;
	}
	
	public ArrayList<Point> getFixedSolution(ArrayList<Point> completeSolution, ArrayList<Point> partialSolution, int index){
		ArrayList<Point> newP = new ArrayList<Point>(completeSolution); 
		newP.removeAll(partialSolution);
		ArrayList<Point> fixedSolution = new ArrayList<Point>(partialSolution);
		fixedSolution.add(index, newP.get(0));
		return fixedSolution;
	}
}
