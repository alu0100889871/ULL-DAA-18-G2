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
	 * Getter of an element of the matrix half stored in matrix
	 * x == y states the diagonal of the matrix. Zero in every case.
	 * x > y states that the diagonal hasn't been trespassed
	 * x < y states that the diagonal has been trespassed and therefore consider the zero of the diagonal
	 * After the correction of the space, if x < y, they are inverted in order to search for the element
	 * in the stored matrix half.
	 * @param x index of the service
	 * @param y index of the client
	 * @return distance between the service and the client dispensed
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
	 * Getter of the submatrix formed by the possible locations in order
	 * to set the problem input.
	 * @param locationIndexes indexes of the possible locations in the matrix
	 * @return a submatrix of the arrays of distances between the service nodes
	 *  selected with the locationIndexes and their distances with the rest of the points
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
	public ArrayList<ArrayList<Double>> getSubmatrixClientLocalizations(ArrayList<ArrayList<Double>> submatrix, ArrayList<Integer> localizations){
		ArrayList<ArrayList<Double>> aux = new ArrayList<ArrayList<Double>>();
		boolean take_number = true;
		for(int i = 0; i < submatrix.size(); i++) {
			ArrayList<Double> aux2 = new ArrayList<Double>();
			for(int j = 0; j < getDots().size(); j++) {
				for(int k = 0; k < localizations.size(); k++) {
					if(localizations.get(k) == j) {
						take_number = false;
					}
				}
				if(take_number) {
					aux2.add(submatrix.get(i).get(j));
				}
				take_number = true;
			}

		aux.add(aux2);
		}

		return aux;
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
