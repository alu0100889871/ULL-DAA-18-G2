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
