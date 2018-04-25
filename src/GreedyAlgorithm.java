import java.util.ArrayList;

public class GreedyAlgorithm extends Algorithm{
	
	public GreedyAlgorithm(PCenterProblem pcp) {
		super(pcp);
	}
	
    public ArrayList<Point> resolve() {
    	ArrayList<Point> solution = new ArrayList<Point>();
    	for (int i = 0; i < getPcp().getSolution().getK(); i++) {
    		solution = new ArrayList<Point>(this.makeOrderedCombinations(solution).get(0));
    	}
    	getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
    	return solution;
    }
    
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		GreedyAlgorithm ga = new GreedyAlgorithm(pcp);
		/*
		System.out.println(ga.makeOrderedCombinations(new ArrayList<Point>()));
		while(ga.getOrderedCombinations().get(0).size() < ga.getPcp().getSolution().getK()) {
			System.out.println(ga.makeOrderedCombinations(ga.getOrderedCombinations().get(0)));
		}
		*/
		ArrayList<Point> solution = ga.resolve();
		System.out.println("Solucion = " + solution);
		System.out.println("Funcion Objetivo = " + ga.getPcp().getSolution().getBestFObj());
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		ArrayList<ArrayList<Double>> submatrix = ga.getPcp().getValues().getSubmatrix(locations);
		System.out.println("==========================================================================");
		for(int i = 0; i < submatrix.size(); i++) {
			System.out.println(submatrix.get(i));
		}
	}
}
