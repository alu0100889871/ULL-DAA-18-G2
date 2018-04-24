import java.util.Random;
import java.util.Arrays;
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
 * Class GRASP.
 *
 */

public class GRASP {
	
   public static Solution permutation(Solution best_sol, ArrayList<Integer> localizations, Matrix m, int max_localizations) {
	   ArrayList<Integer> aux_best_sol = best_sol.getSolution();
	   Solution sol = new Solution(max_localizations,new ArrayList<Integer>());
	   //We choose a random localization not chosen before
	   ArrayList<Integer> new_localization = new ArrayList<Integer>();
	   boolean take_number = true;
	   for(int i = 0; i < localizations.size(); i++) {
		   for(int j = 0; j < aux_best_sol.size(); j++) {
			   if(localizations.get(i) == aux_best_sol.get(j)) {
				   take_number = false;
			   } 
		   }
			if(take_number) {
				new_localization.add(localizations.get(i));
			}
			take_number = true;
		   
   	   } 
	   int rnd = new Random().nextInt(new_localization.size());
	   int random = new_localization.get(rnd);
	   //We choose a random localization chosen before
	   ArrayList<Integer> best_solution = aux_best_sol;
	   int rnd2 = new Random().nextInt(best_solution.size());
	   int random2 = best_solution.get(rnd2);
	   //We remove a random localization we have in the solution and we add the new one
	   ArrayList<Integer> remove_localization = new ArrayList<Integer>();
	   remove_localization.add(random2);
	   best_solution.removeAll(remove_localization);
	   best_solution.add(random);	
	   ArrayList<Double> min_costs = ArrayCostsMin(m,localizations, best_solution);
	   double c_max = MaxCost(min_costs);
	   sol.setSolution(best_solution);
	   sol.setCostSolution(c_max);
	   return sol;
    }	

   	public static Solution localSearch(Solution best_sol, Matrix m, ArrayList<Integer> localizations, int max_localizations, int max_no_improv) {
    	int aux = 0;
        do {
        	Solution candidate_sol = permutation(best_sol,localizations,m,max_localizations);
            aux = (candidate_sol.getCostSolution()) < best_sol.getCostSolution() ? 0 : aux + 1;

            if (candidate_sol.getCostSolution() < best_sol.getCostSolution()) {
                best_sol = candidate_sol;
            }
        } while (aux < max_no_improv);

        return best_sol;
    }
	/**
	 * MinCost method 
	 * @return double min cost 
	 * @param ArrayList<Double> min_costs 
	 */		

    public static double MinCost(ArrayList<Double> min_costs){
    	double c_min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < min_costs.size(); i++) {
			if (c_min > min_costs.get(i)) {
				c_min = min_costs.get(i);
			}
		}	
		return c_min;
    }
    public static double MaxCost(ArrayList<Double> min_costs){
    	double c_max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < min_costs.size(); i++) {
			if (c_max < min_costs.get(i)) {
				c_max = min_costs.get(i);
			}
		}
		return c_max;
    }    
    public static ArrayList<Double> ArrayCostsMin(Matrix m,ArrayList<Integer> localizations, ArrayList<Integer> candidate_vector){
    	double c_min = Double.POSITIVE_INFINITY;
    	ArrayList<Double> min_costs = new ArrayList<Double>();
    	ArrayList<ArrayList<Double>> submatrix = new ArrayList<ArrayList<Double>>();
    	submatrix = m.getSubmatrix(candidate_vector);
    	ArrayList<ArrayList<Double>> submatrix2 = m.getSubmatrixClientLocalizations(submatrix,candidate_vector);
    	for (int i = 0; i < (m.getDots().size() - candidate_vector.size()); i++) {
    		for (int j = 0; j < submatrix2.size(); j++) {
    			if(submatrix2.get(j).get(i) < c_min) {
    				c_min = submatrix2.get(j).get(i);
    			}
    		}
    		min_costs.add(c_min);
    		c_min = Double.POSITIVE_INFINITY;
    	}
		return min_costs;
    }

	public static Solution GreedyRandomizedConstruction(Matrix m, ArrayList<Integer> localizations, int max_localizations, int max_iterations, int max_no_improv, double greediness_factor) {
		//solution empty
		Solution sol = new Solution(max_localizations,new ArrayList<Integer>());
		//select a random point from the localizations
		int rnd = new Random().nextInt(localizations.size());
        int random_point = localizations.get(rnd);
        //vector with the candidate localizations, first we add a random point
        ArrayList<Integer> candidate_vector = new ArrayList<Integer>();
        candidate_vector.add(random_point);
        //vector with all possible localizations
        ArrayList<Integer> all_points = new ArrayList<Integer>();
		for (int i = 0; i < localizations.size(); i++) {
			all_points.add(localizations.get(i));
		}
        //cost min and cost max
        double c_min = Double.POSITIVE_INFINITY;
        double c_max = Double.NEGATIVE_INFINITY;
        //array with the min costs
        ArrayList<Double> min_costs = new ArrayList<Double>();
        //RCL
        ArrayList<Integer> RCL = new ArrayList<Integer>();
        //submatrix
        ArrayList<ArrayList<Double>> submatrix = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> submatrix2 = new ArrayList<ArrayList<Double>>();
        //list of candidates
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        //we add all possible localizations to the candidates vector
        candidates.addAll(all_points);
        //we remove the candidate_vector we choose randomly
        candidates.removeAll(candidate_vector);
        while(candidate_vector.size() < max_localizations){
        	
        	//we create the submatrix of the candidate vector
        	
        	submatrix = m.getSubmatrix(candidate_vector);
        	submatrix2 = m.getSubmatrixClientLocalizations(submatrix,candidate_vector);
        	
        	//min cost
        	
        	min_costs = ArrayCostsMin(m,localizations,candidate_vector);
    		c_min = MinCost(min_costs);

    		//max costs
    		
    		c_max = MaxCost(min_costs);

    		//We create the RCL
    		
    		for (int i = 0; i < (m.getDots().size() - candidate_vector.size()); i++) {
        		for (int j = 0; j < submatrix2.size(); j++) {
        			if(submatrix2.get(j).get(i) <= (c_min + (greediness_factor * (c_max - c_min))) ){
        				RCL.add(candidates.get(j));
        			}
        		}
    		}
    		
    		//We choose randomly one point of the RCL
    		
			int random_rcl= RCL.get((int) (Math.random() * RCL.size()));
			candidate_vector.add(random_rcl);
			ArrayList<Integer> RCL_aux = RCL;
			RCL.clear();
			
			//We check out if its not a chosen localization
			
    		for (int i = 0; i < RCL_aux.size(); i++) {
    			if(random_rcl != RCL_aux.get(i)) {
    				RCL.add(RCL_aux.get(i));
    			}
    		}
    		
    		//We remove the candidate vector from the possible localization because we choose it before
    		
    		candidates.removeAll(candidate_vector);
        }
		sol.setCostSolution(c_max);
		sol.setSolution(candidate_vector);
		return sol;
	}
	public static Solution Grasp(Matrix m, ArrayList<Integer> localizations, int max_localizations, int max_iterations, int max_no_improv, double greediness_factor) {
		Solution best_sol = new Solution(max_localizations,new ArrayList<Integer>());
		for (int i = 0; i < max_iterations; i++) {
			Solution candidate_sol = GreedyRandomizedConstruction(m,localizations,max_localizations, max_iterations, max_no_improv, greediness_factor);
			candidate_sol = localSearch(candidate_sol, m, localizations, max_localizations, max_no_improv);
	        if ((best_sol.getCostSolution() == 0) || (candidate_sol.getCostSolution() < best_sol.getCostSolution())) {
	        	best_sol = candidate_sol;
	        }
		}

		return best_sol;
	  }
	
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		Point a = new Point(2, 5);
		Point b = new Point(9, 6);
		Point c = new Point(14, 7);
		Point d = new Point(4, 1);
		Point e = new Point(8, 3);
		Point f = new Point(13, 1);
		Point g = new Point(3, 1);
		Point h = new Point(12, 5);
		
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(a);
		dots.add(b);
		dots.add(c);
		dots.add(d);
		dots.add(e);
		dots.add(f);
		dots.add(g);
		dots.add(h);
		
		Matrix m = new Matrix(dots);
		System.out.println(m);
		
        int max_iter = 30;
        int max_no_improv = 30;
        double greediness_factor = 0.3;
        int max_localizations = 3;
        ArrayList<Integer> localizations= new ArrayList<Integer>();
        localizations.add(1);
        localizations.add(4);
        localizations.add(5);
        localizations.add(3);
        localizations.add(0);
        Solution best = Grasp(m, localizations, max_localizations, max_iter,max_no_improv,greediness_factor);
        System.out.println("The best solution is: c= " + best.getCostSolution() + ", v= " + best.getSolution());

	}
	

}
