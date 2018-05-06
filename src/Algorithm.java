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
 * Clase base para todos los algoritmos de resolución de problemas de p-centro
 */
public class Algorithm {
	private PCenterProblem pcp;
	private ArrayList<ArrayList<Point>> combinations;
	private ArrayList<ArrayList<Point>> orderedCombinations;
	/**
	 * Método constructor
	 * @param pcp problema del p-centro a resolver
	 */
	public Algorithm(PCenterProblem pcp) {
		this.pcp = new PCenterProblem(pcp.getSolution().getK(), pcp.getValues().getDots());
		combinations = new ArrayList< ArrayList<Point>>();
		orderedCombinations = new ArrayList< ArrayList<Point>>();
	}
	
	public PCenterProblem getPcp() {
		return pcp;
	}
	public void setPcp(PCenterProblem pcp) {
		this.pcp = pcp;
	}
	
	protected ArrayList<ArrayList<Point>> getCombinations() {
		return combinations;
	}
	protected void setCombinations(ArrayList<ArrayList<Point>> combinations) {
		this.combinations = combinations;
	}

	protected void addToCombinations(ArrayList<Point> combination) {
		this.combinations.add(combination);
	}
	
	protected ArrayList<ArrayList<Point>> getOrderedCombinations() {
		return orderedCombinations;
	}
	protected void setOrderedCombinations(ArrayList<ArrayList<Point>> orderedCombinations) {
		this.orderedCombinations = orderedCombinations;
	}
	
	protected ArrayList<ArrayList<Point> > makeOrderedCombinations(ArrayList<Point> combis){
		getKCombinations(combis);
		return sortCombinations();
	}
	
	protected ArrayList<ArrayList<Point>> makeOrderedCombinations(int k){
		getCombinations(k);
		return sortCombinations();
	}
	
	protected ArrayList<ArrayList<Point>> getCombinations(int k) {
		ArrayList<ArrayList<Point>> combinations = new ArrayList<ArrayList<Point>>();
		for(int i = 0; i < getPcp().getSolution().getDots().size(); i++) {
			ArrayList<Point> newP = new ArrayList<Point>();
			newP.add(getPcp().getValues().getDots().get(i));
			combinations.add(newP);
		}
		while(combinations.get(0).size() < k) {
			ArrayList<ArrayList<Point>> newCombs = new ArrayList<ArrayList<Point>>();
			for(int i = 0; i < combinations.size(); i++) {
				for (int j = 0; j < getPcp().getSolution().getDots().size(); j++) {
					if(!combinations.get(i).contains(getPcp().getSolution().getDots().get(j))) {
						ArrayList<Point> newComb = new ArrayList<Point>(combinations.get(i));
						newComb.add(getPcp().getSolution().getDots().get(j));
						newCombs.add(newComb);
					}
				}
			}
			combinations = new ArrayList<ArrayList<Point>>(newCombs);
		}
		
		this.setCombinations(combinations);
		return combinations;
	}
	/**
	 * Método para la ordenación del array de combinaciones interno
	 * @return array de combinaciones interno
	 */
	public ArrayList<ArrayList<Point>> sortCombinations(){
		ArrayList<ArrayList<Point>> temp = new ArrayList<ArrayList<Point>>(getOrderedCombinations());
		setOrderedCombinations(new ArrayList<ArrayList<Point>>(getCombinations()));
        int n = getOrderedCombinations().size();
        quicksort(0, n - 1);
        if(getOrderedCombinations().isEmpty()) {
        	System.out.println("K INTRODUCIDA MAYOR AL NUMERO DE ELEMENTOS INTRODUCIBLES");
        	setOrderedCombinations(temp);
        }
		return getOrderedCombinations();
	}
	/**
	 * Método para la generación de un array de combinaciones de tamaño n + 1, siendo n
	 * el tamaño del que se compone el array de entrada. En este mismo array se basa la
	 * búsqueda de combinaciones, dando todas las combinaciones entre el array de entrada
	 * y cada uno de los elementos que están fuera del mismo.
	 * @param combis combinación de elementos inicial
	 * @return ArrayList<ArrayList<Point>> array de combinaciones
	 */
	public ArrayList<ArrayList<Point>> getKCombinations(ArrayList<Point> combis) {
		ArrayList<ArrayList<Point>> aux = new ArrayList<ArrayList<Point>>();
		if(combis.size() < 1) {
			 for(int i = 0; i < getPcp().getSolution().getDots().size(); i++) {
				 ArrayList<Point> tmp = new ArrayList<Point>();
				 tmp.add(getPcp().getSolution().getDots().get(i));
				 aux.add(tmp);
			 }
			 this.setCombinations(aux);
			 return aux;
		}
		ArrayList<ArrayList<Point>> combinations = new ArrayList<ArrayList<Point>>();
		for (int j = 0; j < getPcp().getSolution().getDots().size(); j++) {
			if(!combis.contains(getPcp().getSolution().getDots().get(j))) {
				ArrayList<Point> newComb = new ArrayList<Point>(combis);
				newComb.add(getPcp().getSolution().getDots().get(j));
				combinations.add(newComb);
			}
		}
		this.setCombinations(combinations);
		return combinations;
	}
	
	
	
	
	/**
	 * Método aplicado para la ordenación recursiva de los elementos del array de combinaciones
	 * @param low índice menor de la zona del array a considerar para la ordenación
	 * @param high índice mayor de la zona del array a considerar para la ordenación
	 */
	private void quicksort(int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        if(low + (high-low)/2 >= getOrderedCombinations().size())
        	return;
        
        ArrayList<Point> pivotPoints = new ArrayList<Point> (getOrderedCombinations().get(low + (high-low)/2 ));
        double pivot = getPcp().funcionObjectivo(pivotPoints);
        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (getPcp().funcionObjectivo(getOrderedCombinations().get(i)) < pivot) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (getPcp().funcionObjectivo(getOrderedCombinations().get(j)) > pivot) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }
	/**
	 * Método para el intercambio de combinaciones dentro del array ordenado
	 * @param i índice de posición a intercambiar desde la izquierda a la derecha
	 * @param j índice de posición a intercambiar desde la derecha a la izquierda
	 */
    private void exchange(int i, int j) {
        ArrayList<Point> temp = new ArrayList<Point>(getOrderedCombinations().get(i));
        getOrderedCombinations().set(i, getOrderedCombinations().get(j));
        getOrderedCombinations().set(j, temp);
    }
	
}
