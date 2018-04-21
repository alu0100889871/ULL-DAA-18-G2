package pcenterproblem;
import java.util.ArrayList;

public class Matrix {
	ArrayList<Point> dots;
	ArrayList<ArrayList<Double>> distances; 
	
	public Matrix(ArrayList<Point> dots) {
		this.dots = new ArrayList<Point>(dots);
		this.distances = new ArrayList<ArrayList<Double>>();
		for(int i = 1; i < dots.size(); i++) {
			ArrayList<Double> iDistances = new ArrayList<Double>();
			for (int j = 0; j < i; j++) {
				iDistances.add(dots.get(i).getDistance(dots.get(j)));
			}
			this.distances.add(iDistances);
		}
	}

	public ArrayList<Point> getDots() { return dots; }
	public void setDots(ArrayList<Point> dots) { this.dots = dots; }
	
	public ArrayList<ArrayList<Double>> getDistances() { return distances; }
	public void setDistances(ArrayList<ArrayList<Double>> distances) { this.distances = distances; }
	
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
		return distances.get(x).get(y);
	}
	
	public ArrayList<ArrayList<Double>> getSubmatrix(ArrayList<Integer> locationIndexes){
		ArrayList<ArrayList<Double>> submatrix = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < locationIndexes.size(); i++) {
			ArrayList<Double> aux = new ArrayList<Double>();
			for(int j = 0; j < dots.size(); j++) {
				aux.add(getDistance(locationIndexes.get(i), j));
			}
			submatrix.add(aux);
		}
		return submatrix;
	}
	
	@Override
	public String toString() {
		String print = "dots=" + dots + "\n";
		for (int i = 0; i < dots.size(); i++) {
			for(int j = 0; j < dots.size(); j++) {
				print += "   ".substring ( ("" + Math.round (getDistance(i, j)) ).length() )  + String.format("%.4f", getDistance(i, j)) + " ";
			}
			print += "\n";
		}
		return print;
	}
	
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
