/**
 * 
 * @author Ernesto Echeverría González
 * @since 17/04/2018
 *
 */
public class Point {
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double getDistance(Point p) {
		return Math.sqrt(Math.pow(Math.abs((getX() - p.getX())), 2) + Math.pow(Math.abs((getY() - p.getY())), 2));
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

}
