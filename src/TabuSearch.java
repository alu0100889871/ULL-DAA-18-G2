import java.util.ArrayList;

/**
 * Clase donde se realizará la busqueda tabú
 * @author norberto
 *
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
	

	private ArrayList<Integer> tabuServer_= new ArrayList<Integer>(); //lista tabu los servicions
	private ArrayList<Integer> tabuClient_= new ArrayList<Integer>();//lista tabú para los clientes.
	private ArrayList<Integer> frecuencia_= new ArrayList<Integer>(); //frecuencia.
	private int size_ = 0; //tamanyo de los array
	
	
	
	public TabuSearch(PCenterProblem pcp) {
		super(pcp);

		size_ = pcp.getValues().getDots().size(); // esto es el tamaño de los nodos totales ?
		
		
		for(int i = 0; i < size_; i++) //inicializamos todas las tablas con el tamañyo de los nodos
		{
			tabuServer_.add(0);
			tabuClient_.add(0);
			frecuencia_.add(0);
		}
		
	}
	
	
	
	
	
	public void busqueda(ArrayList<Point> initialSolution)
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
		
		double coste= getPcp().funcionObjectivo(initialSolution); //coste con el que comprobaremos el criterio de aspiracion
		
		while(w < FIN) // criterio de parada ?? aver esto con punto . funciona ??
		{
			
			
			exhaustiveSingleLocationChangeSearch(initialSolution); //busqueda greedy con las condiciones de tabu.
			
			
			
			
			w++;
		}
	}
	

	
	
	
	
	/**
	 * funcion que decrementa las listas tabu a 0 ( memoria a corto plazo)
	 * 
	 */
	
	public void decrementar()
	{
		for(int i = 0; i < size_; i++)
		{
			if(getServer(i) > 0)
			{
				setServer(i, getServer(i) - 1);
			}
			
			if(getClient(i) > 0)
			{
				setClient(i, getClient(i) -1);
			}
		}
	}
	
	

	
	/**
	 * busqueda local de un elemento modificada para el tabu search
	 * @param initialSolution
	 * @return
	 */
	
	public ArrayList<Point> exhaustiveSingleLocationChangeSearch(ArrayList<Point> initialSolution){
		boolean upgraded = true;
		double coste = getPcp().funcionObjectivo(initialSolution);
		
		ArrayList<Point> actualSolution = new ArrayList<Point>(initialSolution);
		while(upgraded && initialSolution.size() > 1) {
			upgraded = false;
			ArrayList<Point> root = new ArrayList<Point>(actualSolution);
			root.remove(actualSolution.get(actualSolution.size() - 1));
			ArrayList<ArrayList<Point>> combinations = getKCombinations(root, coste);
			combinations.remove(actualSolution);
			for(int j = 0; j < combinations.size(); j++) {
				if(getPcp().funcionObjectivo(combinations.get(j)) < getPcp().funcionObjectivo(actualSolution)) {
					upgraded = true;
					actualSolution = new ArrayList<Point>(combinations.get(j));
				}
			}
		}
		
		int p  = diferencia(initialSolution, actualSolution); //indice del nodo que añadiremos a tabu. //funcion diferencia
		addTabu(p);
		return actualSolution;
	}
	
	
	/**
	 * metodo para buscar el elemento diferente entre dos arrays.
	 * @param a
	 * @param b
	 * @return
	 */
	
	public Integer diferencia(ArrayList<Point> a, ArrayList<Point> b)
	{
		if(a.size() == b.size())
		{
			
	
		Integer resultado= 0;
		
		ArrayList<Point> aux = a;
		aux.removeAll(b);
		
		resultado = getPcp().getValues().getIndex(aux.get(0));
		
		return resultado;
		}else {
			
			System.err.println("errr"); //error en los tamanyos de los arraylist (no deberia de pasar)
			return -1;
		}
		
	}
	/**
	 * Método para la generación de un array de combinaciones de tamaño n + 1, siendo n
	 * el tamaño del que se compone el array de entrada. En este mismo array se basa la
	 * búsqueda de combinaciones, dando todas las combinaciones entre el array de entrada
	 * y cada uno de los elementos que están fuera del mismo.
	 * @param combis combinación de elementos inicial
	 * @paam coste para comprobar si mejora la solu
	 * @return ArrayList<ArrayList<Point>> array de combinaciones
	 */
	public ArrayList<ArrayList<Point>> getKCombinations(ArrayList<Point> combis , double coste) {
		ArrayList<ArrayList<Point>> aux = new ArrayList<ArrayList<Point>>();
		if(combis.size() < 1) {
			 for(int i = 0; i < getPcp().getSolution().getDots().size(); i++) {
				 ArrayList<Point> tmp = new ArrayList<Point>();
				 tmp.add(getPcp().getSolution().getDots().get(i));
				 aux.add(tmp);
			 }
			 this.setCombinations(aux);
			 return aux;
		}
		ArrayList<ArrayList<Point>> combinations = new ArrayList<ArrayList<Point>>();
		for (int j = 0; j < getPcp().getSolution().getDots().size(); j++) {
			if((!combis.contains(getPcp().getSolution().getDots().get(j))) && !(checkServer(getPcp().getValues().getIndex(getPcp().getSolution().getDots().get(j)), coste)) ) {
				ArrayList<Point> newComb = new ArrayList<Point>(combis);
				newComb.add(getPcp().getSolution().getDots().get(j));
				combinations.add(newComb);
			}
		}
		this.setCombinations(combinations);
		return combinations;
	}
	
	
	
	
	/**
	 * añade a la lista tabú de los clientes y los servidores 
	 * @param servidor
	 * @param cliente
	 */
	public void addTabu(int servidor)
	{
		setServer(servidor, DELAY);
		//setClient(cliente,DELAY);
	}
	
	
	
	/**
	 * checkea que el nodo i no esté tabú
	 * @param i
	 * @param coste compobamos el criterio de aspiracion
	 * @return
	 */
	public boolean checkServer(int i, double coste)
	{
		if(getServer(i) > 0)
		{
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * checkea el nodo i no esté tabú en clientes.
	 * @param i
	 * @return
	 */
	public boolean checkClient(int i)
	{
		if(getClient(i) > 0)
		{
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * incrementa en 1 el nodo seleccionado ( memoria a largo plazo)
	 * @param i
	 */
	
	
	public void Incrementa(int i)
	{
		setFrecuencia(i, getFrecuencia(i) +1);
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
	public Integer getServer(int i)
	{
		return tabuServer_.get(i);
	}
	
	/**
	 * funcion que modifica el nodo servidor tabú
	 * @param i
	 * @param val
	 */
	public void setServer(int i, int val)
	{
		tabuServer_.set(i, val);
	}
	
	
	/**
	 * funcion que devuelve el nodo cliente tabú
	 * @param i
	 * @return
	 */
	
	public Integer getClient(int i)
	{
		return tabuClient_.get(i);
	}
	
	/**
	 * funcion que modifica el nodo cliente tabú
	 * @param i
	 * @param val
	 */
	public void setClient(int i, int val)
	{
		tabuClient_.set(i, val);
	}
	
	/**
	 * funcion que devuleve la frecuencia del nodo
	 * @param i
	 * @return
	 */
	public Integer getFrecuencia(int i)
	{
		return frecuencia_.get(i);
	}
	
	/**
	 * funcion que modifica la frecuencia del nodo 
	 * @param i
	 * @param val
	 */
	public void setFrecuencia(int i, int val)
	{
		frecuencia_.set(i, val);
	}
	


}
