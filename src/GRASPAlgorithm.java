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
import java.util.ArrayList;

public class GRASPAlgorithm extends Algorithm{
	/**
	 * Entero que marca el tama�o de la lista de posibles candidatos
	 */
	private int RCLSize;
	/**
	 * M�todo constructor
	 * @param pcp problema del p-centro a resolver
	 * @param RCLSize tama�o del que se quiera hacer la lista de candidatos
	 */
	public GRASPAlgorithm(PCenterProblem pcp, int RCLSize) {
		super(pcp);
		this.RCLSize = RCLSize;
	}
	/**
	 * M�todo para resolver el problema
	 * Hasta que se forme una soluci�n factible:
	 * 		1. construcci�n del siguiente candidato
	 * 		2. Adici�n de ese candidato a la soluci�n
	 * 		3. Mejora de la soluci�n actual mediante b�squeda local exhaustiva
	 * @return solucion final, un ArrayList\<Point\>
	 */
	/*
	 * No consigue el �ptimo ya que la decisi�n por la que se toma el inicial es mala
	 * �Path-relinking? �Elecci�n de soluciones tal y como est�? -> la profesora dice de ir cogiendo el punto que est� m�s alejado del resto
	 * Faltar�a hacer la comparaci�n de las distancia y tomar el siguiente punto
	 */
	public ArrayList<Point> resolve() {
    	ArrayList<Point> solution = new ArrayList<Point>();
    	for (int i = 0; i < getPcp().getSolution().getK(); i++) {
    		solution = new ArrayList<Point>(nextGRASPSelected(solution));
    		solution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(solution);
    	}
    	getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
    	return solution;
    }
	/**
	 * Constructor del siguiente candidato a a�adir a la soluci�n
	 * Dada una lista ordenada de candidatos vecinos, coger de los (RCLSize) mejores, uno aleatorio
	 * @param initialSolution soluci�n inicial de la que se parte para obtener la posible soluci�n siguiente
	 * @return ArrayList\<Point\>, soluci�n con el elemento a�adido
	 */
	public ArrayList<Point> nextGRASPSelected(ArrayList<Point> initialSolution) {
		if(initialSolution.size() < 1) {
			initialSolution.add(getPcp().getValues().getDots().get((int)(Math.random() * getPcp().getValues().getDots().size() ) ) );
			return initialSolution;
		}
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < getPcp().getValues().getDots().size(); i++) {
			distances.add(getServicesDistance(initialSolution, getPcp().getValues().getDots().get(i)));
		}
		ArrayList<Point> RCLList = new ArrayList<Point>();
		for(int j = 0; j < RCLSize; j++) {
			int index = 0;
			double distance = 0.0;
			for(int i = 0; i < distances.size(); i++) {
				if(distance < distances.get(i)) {
					index = i;
					distance = distances.get(i);
					distances.set(i, -1.0);
				}
			}
			RCLList.add(getPcp().getValues().getDots().get(index));
		}
		ArrayList<Point> graspSelectedSolution = new ArrayList<Point>(initialSolution);
		graspSelectedSolution.add(RCLList.get((int) (Math.random() * RCLList.size()) ) );
		return graspSelectedSolution;
	}
	
	public double getServicesDistance(ArrayList<Point> services, Point newService) {
		double distance = 0.0;
		for(int i = 0; i < services.size(); i++) {
			distance += services.get(i).getDistance(newService);
		}
		distance /= services.size();
		return distance;
	}
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		GRASPAlgorithm ga = new GRASPAlgorithm(pcp, 3);
		
		ArrayList<Point> solution = ga.resolve();
		System.out.println("SOLUTION POINTS = " + solution);
    	System.out.println("OBJECTIVE FUNCTION = " + ga.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
}
