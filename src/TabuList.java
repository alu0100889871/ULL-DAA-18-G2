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
 * Clase para la resolución de problemas de p-centro mediante Greedy Randomized Adaptative Search Procedure (GRASP)
 */
public class TabuList {


	private Point tabu_; //lista que quiero en tabu
	private int contador_; //list
	
	
	
	public TabuList(Point tabu, int i)
	{
		tabu_ = tabu;
		contador_ = i;
	}

	public void decrementar()
	{
		contador_--; //decrementamos hasta llegar a 0
	}
	
	
	public void incrementar()
	{
		contador_++; 
		
	}
	/*
	 *
	 *		### GETTERS & SETTERS
	 *
	 */
	
	/**
	 * metodo para obtener el punto
	 * @return
	 */
	public Point getPoint()
	{
		return tabu_; 
	}
	
	
	
	public void setPoint(Point tabu)
	{
		tabu_ = tabu;
	}
	
	public int getCont()
	{
		return contador_;
	}
}
