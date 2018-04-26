import java.util.ArrayList;

public class GRASPAlgorithm extends Algorithm{

	private int RCLSize;
	
	public GRASPAlgorithm(PCenterProblem pcp, int RCLSize) {
		super(pcp);
		this.RCLSize = RCLSize;
	}
	
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
	
	public ArrayList<Point> nextGRASPSelected(ArrayList<Point> initialSolution) {
		ArrayList<ArrayList<Point>> combinations = this.makeOrderedCombinations(initialSolution);
		int randomSelected = (int) (Math.random() * RCLSize);
		return combinations.get(randomSelected);
	}
	
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		GRASPAlgorithm ga = new GRASPAlgorithm(pcp, 3);
		
		ArrayList<Point> solution = ga.resolve();
		System.out.println(solution);
    	System.out.println(ga.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < ga.getPcp().getSolution().getDots().size(); i++) {
			locations.add(ga.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		ArrayList<ArrayList<Double>> submatrix = ga.getPcp().getValues().getSubmatrix(locations);
		System.out.println("==========================================================================");
		for(int i = 0; i < submatrix.size(); i++) {
			System.out.println(submatrix.get(i));
		}
		System.out.println("==========================================================================");
		System.out.println(ga.getPcp().getValues());
		System.out.println(locations);
	}
}
