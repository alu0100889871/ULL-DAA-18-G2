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
 * Class to create a 2D point.
 *
 */
public class Point {
	private int x;
	private int y;
	
	/**
	 * Point constructor.
	 * @param x X Axis.
	 * @param y Y Axis.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method to calculate the distance between two points.
	 * @param p Point to calculate the distance to.
	 * @return int The distance between two points.
	 */
	public double getDistance(Point p) {
		return Math.sqrt(Math.pow(Math.abs((getX() - p.getX())), 2) + Math.pow(Math.abs((getY() - p.getY())), 2));
	}
	
	/**
	 * Getter of the X Axis.
	 * @return int X Axis.
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Getter of the Y Axis.
	 * @return int Y Axis.
	 */
	public int getY() {
		return this.y;
	}
	/**
	 * Setter of the X Axis.
	 * @param x int X Axis.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Setter of the Y Axis.
	 * @param y int Y Axis.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * toString method.
	 * @return string Point as a string.
	 */
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}

}
