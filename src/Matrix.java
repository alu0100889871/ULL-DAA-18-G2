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
 * Class to store the distances between every two nodes.
 *
 */
public class Matrix {
	ArrayList<Point> dots;
	ArrayList<ArrayList<Double> > distances; 
	
	/**
	 * Matrix constructor.
	 * @param dots ArrayList<Point> All the nodes as an array.
	 */
	public Matrix(ArrayList<Point> dots) {
		this.dots = new ArrayList<Point>(dots);
		this.distances = new ArrayList<ArrayList<Double> >();
		for(int i = 1; i < dots.size(); i++) {
			ArrayList<Double> iDistances = new ArrayList<Double>();
			for (int j = 0; j < i; j++) {
				iDistances.add(dots.get(i).getDistance(dots.get(j)));
			}
			this.distances.add(iDistances);
		}
	}

	/**
	 * Getter of dots.
	 * @return ArrayList<Point> All the nodes as an array.
	 */
	public ArrayList<Point> getDots() { 
		return this.dots; 
	}
	/**
	 * Setter of dots.
	 * @param dots ArrayList<Point> All the nodes as an array.
	 */
	public void setDots(ArrayList<Point> dots) { 
		this.dots = dots; 
	}
	/**
	 * Getter of distances.
	 * @return ArrayList<ArrayList<Double>> Distances matrix.
	 */
	public ArrayList<ArrayList<Double> > getDistances() { 
		return this.distances; 
	}
	/**
	 * Setter of distances,
	 * @param distances ArrayList<ArrayList<Double>> Distances matrix.
	 */
	public void setDistances(ArrayList<ArrayList<Double> > distances) { 
		this.distances = distances; 
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double getDistance(int x, int y) {
		if (x == y)
			return 0.0;	
		if(x > y) {
			x -= 1;
		} else {
			y -= 1;
		}
		if( x < y) {
			int t = x;
			x = y;
			y = t;
		}
		return getDistances().get(x).get(y);
	}
	
	/**
	 * 
	 * @param locationIndexes
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getSubmatrix(ArrayList<Integer> locationIndexes){
		ArrayList<ArrayList<Double>> submatrix = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < locationIndexes.size(); i++) {
			ArrayList<Double> aux = new ArrayList<Double>();
			for(int j = 0; j < getDots().size(); j++) {
				aux.add(getDistance(locationIndexes.get(i), j));
			}
			submatrix.add(aux);
		}
		return submatrix;
	}
	
	/**
	 * toString method.
	 * @return string Matrix as a string.
	 */
	@Override
	public String toString() {
		String print = "dots = " + getDots() + "\n";
		for (int i = 0; i < getDots().size(); i++) {
			for(int j = 0; j < getDots().size(); j++) {
				print += "   ".substring ( ("" + Math.round (getDistance(i, j)) ).length() )  + String.format("%.4f", getDistance(i, j)) + " ";
			}
			print += "\n";
		}
		return print;
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
		
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(a);
		dots.add(b);
		dots.add(c);
		dots.add(d);
		dots.add(e);
		dots.add(f);
		
		Matrix m = new Matrix(dots);
		System.out.println(m);
		ArrayList<Integer> subs = new ArrayList<Integer>();
		subs.add(1);
		subs.add(3);
		subs.add(5);
		
		ArrayList<ArrayList<Double>> submatrix = m.getSubmatrix(subs);
		System.out.println(submatrix);
	}
}
