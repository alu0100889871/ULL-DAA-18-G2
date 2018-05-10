import java.util.ArrayList;
import java.util.Random;

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
public class TabuSearch extends Algorithm {

	private static final int DELAY = 4; //el tiempo que permanecen en tabú el contenido.
	private static final int FIN = 1000; //constante que finaliza tabu
	private static final int BEST = 500; //si lleva 500 iteraciones sin mejorar el coste global coge los nodos mas frecuentes (Intesificacion)
	private static final int DIVER =200; //a los 200 no empezamos a penalizar los más frecuentes.
	
	/*
	 * La busqueda tabu es un método de busqueda local que utiliza la memoria para
	 * así evitar la repetición de soluciones que ya hayán sido testeada
	 * el criterio de movimiento que pensamos emplear para determinar la vecindad
	 * es el intercambio (swap) que para nuestro caso será el intercambio de un nodo 
	 * servicio de la solución inicial por la de un nodo candidato.  
	 * 
	 * Lista tabú: contendrá el cambio de nodo servicio que ha sido reemplazado.
	 * criterio de aspiración: si minimisa el coste de nodo máximo. 
	 *
	 */
	

	private ArrayList<TabuList> tabuServer_= new ArrayList<TabuList>(); //lista tabu los servicions
	private ArrayList<TabuList> tabuClient_= new ArrayList<TabuList>();//lista tabú para los clientes.
	private ArrayList<TabuList> frecuencia_= new ArrayList<TabuList>(); //frecuencia.
	private int k_ = 0; //los k elementos de la solucion
	private int size_ = 0; //tamanyo de los array
	
	
	
	public TabuSearch(PCenterProblem pcp) {
		super(pcp);

		size_ = pcp.getValues().getDots().size(); // esto es el tamaño de los nodos totales ?
		
		

		
	}
	
	
	
	
	
	public ArrayList<Point> busqueda(ArrayList<Point> initialSolution)
	{
		/* 
		 * 
		 * hasta que (100) iteraciones ??:
		 * 
		 * 1. solucion inicial
		 * 2. Busqueda local ( BL1 modificada con las listas tabu para tener menos que iterar ?? )
		 * 3. poner en tabu la modificación && amuentar en la memoria a largo plazo a uno.
		 * 4. decrementar tabla tabu en uno en cada iteracion.
		 *  
		 * 
		 */
		
		int w = 0;
		ArrayList<Point> resultado = randomSolution(getPcp().getSolution().getK());

		System.out.println("resultado  " + resultado.toString());
		System.out.println("tama " + resultado.size());

		getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(resultado));
    	getPcp().getSolution().setDots(resultado);
		//coste con el que comprobaremos el criterio de aspiracion
		
		while(w < FIN) // criterio de parada ?? aver esto con punto . funciona ??
		{
			
			
			//busqueda greedy con las condiciones de tabu.
			
			
			
			
			w++;
		}
		

		return resultado;
	}
	

	
	/**
	 * funcion que devuelve una solucion aleatoria de tamanyo k
	 * @param k
	 * @return
	 */
	
	public ArrayList<Point> randomSolution(int k)
	{
		Random r1 = new Random(700);
		ArrayList<Point> aux = new ArrayList<Point>();
		aux.addAll(getPcp().getValues().getDots());
		ArrayList<Point> tmp  = new ArrayList<Point>();
		
		while(tmp.size() < k)
		{
			int val = r1.nextInt(aux.size());
			
			tmp.add(aux.get(val));
			
			aux.remove(val);
		}
			
		System.out.println("matriz dentro " + getPcp().getValues().getDots());
			
			return tmp;
	}
	
	
	
	/**
	 * metodo de busqueda local greedy con los añadidos del tabu
	 * @param initialSolution
	 * @return
	 */
	
	
	
	public ArrayList<Point> LocalSearchTabu(ArrayList<Point> initialSolution)
	{
		ArrayList<Point> client = getPcp().getSolution().getDots();
		client.removeAll(initialSolution); //dejamos los clientes para compararlos.
		
		
		
		
		ArrayList<Point> actualSolution = initialSolution; //inicializo la solucion actual con la funcion la que entra
		
		
		
		for(int i = 0; i < actualSolution.size(); i++ )
		{
			ArrayList<Point> improvedSolution = actualSolution;
			
			for(int j = 0; j < client.size(); j++)
			{
				if(checkServer(client.get(j))) //comprobamos si el nodo que queremos añadir existe en tabu
				{
				improvedSolution.remove(i);
				improvedSolution.add(client.get(j));
				
				if(getPcp().funcionObjectivo(improvedSolution) < getPcp().funcionObjectivo(actualSolution)) //checkamos
				{
					actualSolution = improvedSolution; //cambiamos el valor de la solucion por la solucion objetivo
				}
				
				}
			}//end for
		}//end for
		
		
		addTabu(actualSolution, initialSolution);
		
		
		return actualSolution;
	}
	
	
	/**
	 * metodo para checkar y meter el nodo saliente en la lista tabu
	 * @param actualSolution
	 * @param initialSolution
	 */
	private void addTabu(ArrayList<Point> actualSolution, ArrayList<Point> initialSolution) {
		
		ArrayList<Point> aux = initialSolution;
		
		aux.removeAll(actualSolution);
		
		if(aux.size() == 1)
		{
			addTabu(aux.get(0)); //añadimos el nodo diferente de la solucion inicial
		}else
		{
			System.err.println("err-> " + aux.toString());
		}
		
	}





	/**
	 * funcion que decrementa las listas tabu a 0 ( memoria a corto plazo)
	 * 
	 */
	
	public void decrementar()
	{
		ArrayList<TabuList> aux = new ArrayList<TabuList>();
		for(int i = 0; i < getServer().size(); i++)
		{
			getServer().get(i).decrementar();
			if(getServer().get(i).getCont() == 0)
			{
				aux.add(getServer().get(i));
			}
		}
		
		getServer().removeAll(aux);
		
		/*
		 * lo mismo para el cliente
		 */
	
	}
	
	
	

	
	

	

	
	


	
	
	
	/**
	 * añade a la lista tabú de los clientes y los servidores 
	 * @param servidor
	 * @param cliente
	 */
	public void addTabu(Point servidor)
	{
		setServer(servidor);
	   //setClient(cliente);
	}
	
	
	
	/**
	 * checkea que el nodo i no esté tabú
	 * @param i
	 * @param coste compobamos el criterio de aspiracion
	 * @return
	 */
	public boolean checkServer(Point tabu)
	{
		if(getServer().contains(tabu))
		{
			return true;
		}else {
			return false;
		}
		
	}
	
	
	/**
	 * checkea el nodo i no esté tabú en clientes.
	 * @param i
	 * @return
	 */
	public boolean checkClient(Point tabu)
	{
		if(getClient().contains(tabu))
		{
			return true;
		}else {
			return false;
		}
	}
	
	

	
	/*
	 * 
	 * 	GETTERS & SETTERS
	 * 
	 * 
	 */
	
	/**
	 * funcion que devuelve el nodo servidor tabu
	 * @param i
	 * @return
	 */
	public ArrayList<TabuList> getServer()
	{
		return tabuServer_;
	}
	
	
	

	/**
	 * funcion que modifica el nodo servidor tabú
	 * @param i
	 * @param val
	 */
	public void setServer( Point val)
	{
			TabuList aux = new TabuList(val,DELAY);
			tabuServer_.add(aux);
	}
	
	
	/**
	 * funcion que devuelve el nodo cliente tabú
	 * @param iS
	 * @return
	 */
	
	public ArrayList<TabuList> getClient()
	{
		return tabuClient_;
	}
	
	/**
	 * funcion que modifica el nodo cliente tabú
	 * @param i
	 * @param val
	 */
	public void setClient(int i, Point val)
	{
		TabuList aux = new TabuList(val,DELAY);
		tabuClient_.add(aux);
	}
	
	/**
	 * funcion que devuleve la frecuencia del nodo
	 * @param i
	 * @return
	 */
	public ArrayList<TabuList> getFrecuencia()
	{
		return frecuencia_;
	}
	
	/**
	 * funcion que modifica la frecuencia del nodo 
	 * @param i
	 * @param val
	 */
	public void setFrecuencia( Point val)
	{
		TabuList aux = new TabuList(val,0);
		tabuClient_.add(aux);
	}
	
	
	
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem("C:\\Users\\norberto\\git\\ULL-DAA-18-G2\\test\\prueba.txt");
		TabuSearch lns = new TabuSearch(pcp);
		
		System.out.println("puntos matrix " + pcp.getValues().getDots().toString());
		System.out.println("puntos solution " + pcp.getSolution().getDots().toString());
		ArrayList<Point> solution = lns.busqueda(pcp.getSolution().getDots());
		System.out.println("SOLUTION POINTS = " + solution);
		System.out.println("OBJECTIVE FUNCTION = " + lns.getPcp().funcionObjectivo(solution));
		
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < lns.getPcp().getSolution().getDots().size(); i++) {
			locations.add(lns.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
	


}
