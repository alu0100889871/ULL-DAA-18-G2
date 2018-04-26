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
 * Clase base para todos los algoritmos de búsqueda local para el problema del p-centro
 * 
 * formato de las funciones:
 * public [ArrayList de Point] funcion (pcp, solucionInicial)
 */
public class LocalSearchMaker extends Algorithm {
	
	public LocalSearchMaker(PCenterProblem pcp) {
		super(pcp);
	}
	/**
	 * Búsqueda exhaustiva de la mejor solución vecina a la solución inicial.
	 * Al haber una mejora, esta se vuelve la solución inicial y se comienza
	 * el proceso de nuevo. Así hasta que sea la mejor solución de su entorno.
	 * 
	 * @param initialSolution solución inicial dispensada como punto de partida de la búsqueda
	 * @return nueva solución mejorada, en caso de haberla
	 */
	public ArrayList<Point> exhaustiveSingleLocationChangeSearch(ArrayList<Point> initialSolution){
		boolean upgraded = true;
		ArrayList<Point> actualSolution = new ArrayList<Point>(initialSolution);
		while(upgraded) {
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
}
