import java.util.ArrayList;

public class MultibootAlgorithm extends Algorithm {
	
	private int bootTimes;
	private final int GRASP_RCL = 3;
			
	public MultibootAlgorithm(PCenterProblem pcp, int bootTimes) {
		super(pcp);
		this.bootTimes = bootTimes;
	}
	
	public ArrayList<Point> resolve(){
		ArrayList<Point> solution = new ArrayList<Point>();
		for (int i = 0; i < bootTimes; i++) {
			ArrayList<Point> actualSolution = new GRASPAlgorithm(getPcp(), GRASP_RCL).resolve();
			ArrayList<Point> upgradedSolution = new LocalSearchMaker(getPcp()).exhaustiveSingleLocationChangeSearch(actualSolution);
			if(getPcp().funcionObjectivo(actualSolution) > getPcp().funcionObjectivo(upgradedSolution)) {
				solution = new ArrayList<Point>(upgradedSolution);
			} else {
				solution = (actualSolution);
			}
		}
		getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(solution));
    	getPcp().getSolution().setDots(solution);
		return solution;
	}
	
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem(args[0]);
		MultibootAlgorithm mba = new MultibootAlgorithm(pcp, 10);
		
		ArrayList<Point> solution = mba.resolve();
		System.out.println("SOLUTION POINTS = " + solution);
    	System.out.println("OBJECTIVE FUNCTION = " + mba.getPcp().funcionObjectivo(solution));
    	
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < mba.getPcp().getSolution().getDots().size(); i++) {
			locations.add(mba.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}

}
