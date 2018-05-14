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

	private static final int DELAY = 10; //el tiempo que permanecen en tabú el contenido.
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
	public double c_ = Double.MAX_VALUE; // coste optimo global (usado para el criterio de aspiracion).
	private static int f_ = 0; // valor para comprobar si la funcion objetivo no mejora.
	
	
	
	public TabuSearch(PCenterProblem pcp) {
		super(pcp);

		size_ = pcp.getValues().getDots().size(); // esto es el tamaño de los nodos totales ?
		
		for(int i = 0; i < size_; i++) //generamos la tabla a largo plazo 
		{
			 
			Point aux = pcp.getValues().getDots().get(i);
			setFrecuencia(aux);
		}
		
		//System.out.println("tama " + size_);
		
		//System.out.println("YOLO " + frecuencia_.toString());
		
		

		
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

		
		//System.out.println("tama " + resultado.size());

		getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(resultado));
    	getPcp().getSolution().setDots(resultado);
		//coste con el que comprobaremos el criterio de aspiracion
		
		while(w < FIN) // criterio de parada ?? aver esto con punto . funciona ??
		{
			
			if(f_ == BEST)
			{
				resultado = solucionFrecuente(getPcp().getSolution().getK());
				resultado = LocalSearchTabu(resultado);
				getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(resultado));
		    	getPcp().getSolution().setDots(resultado);
			}else
			{
				resultado = LocalSearchTabu(resultado);
				getPcp().getSolution().setBestFObj(getPcp().funcionObjectivo(resultado));
		    	getPcp().getSolution().setDots(resultado);
			}
			
			

	    	
	    	
			
			w++;
		}
		

		return resultado;
	}
	

	/**
	 * metodo que nos dara la solucion con los nodos mas frecuentes independientemente de si existen en tabu o no (intesificacion)
	 * @param k
	 * @return
	 */
	
	private ArrayList<Point> solucionFrecuente(int k) {
		ArrayList<Point> aux = new ArrayList<Point>(); //array resultado a devolver
		ArrayList<TabuList> tmp = new ArrayList<TabuList>(); //arrayList que copiara a las frecuencias.
		tmp.addAll(getFrecuencia()); //compiamos la frecuencia tal cual
		int val = Integer.MIN_VALUE; //valor para comparar siempre el nodo mas frecuente
		int index= 0; //indice del nodo mas frecuente 
		
		
		while(aux.size() < k) //bucle que no para hasta que sea aux igual a k
		{
		
			for(int i = 0; i < tmp.size(); i++)
			{
				if(val < tmp.get(i).getCont())
				{
					index = i;
					val = tmp.get(i).getCont();
				}
			}
			
			aux.add(tmp.get(index).getPoint());  // colocamos el nodo con mas frecuencia
			tmp.remove(index); //removemos el nodo de mas valor de la tabla de frecuencia (de la copia) para seguir iterando
			val = Integer.MIN_VALUE; //inicializamos val para la siguiente comprobacion.
		
		}
		return aux;
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
			
		//System.out.println("matriz dentro " + getPcp().getValues().getDots());
			
			return tmp;
	}
	
	
	
	/**
	 * metodo de busqueda local greedy con los añadidos del tabu
	 * @param initialSolution
	 * @return
	 */
	
	
	
	public ArrayList<Point> LocalSearchTabu(ArrayList<Point> initialSolution)
	{
		ArrayList<Point> client = new ArrayList<Point>();
		client.addAll( getPcp().getValues().getDots());

		client.removeAll(initialSolution); //dejamos los clientes para compararlos.
		
		client = chooseRandomClient(client);
		
		//System.out.println("#################################");
		//System.out.println("initialSolved-> " + initialSolution.toString());
		//System.out.println("Tabu server-> " + getServer().toString());
		//System.out.println("tabla frecuencia-> " + getFrecuencia().toString());
		//System.out.println("#################################");
		//System.out.println("client " + client.toString());
		
		
		
		ArrayList<Point> actualSolution = new ArrayList<Point>();
			actualSolution.addAll(initialSolution); //inicializo la solucion actual con la funcion la que entra
		
			
			ArrayList<Point> improvedSolution = new ArrayList<Point>();
		
		for(int i = 0; i < actualSolution.size(); i++ )
		{
			
			//improvedSolution.addAll(actualSolution);

			actualSolution = new ArrayList<Point>();
			actualSolution.addAll(initialSolution);
			
			for(int j = 0; j < client.size(); j++) //aqui comparamos cada nodo cliente con cada nodo servicio.
			{
				//System.out.println("ClientIndex " + client.get(j));

				if(!checkServer(client.get(j), actualSolution, i)) //comprobamos si el nodo que queremos añadir existe en tabu
				{
	
				actualSolution.remove(i);
				actualSolution.add(i, client.get(j));
				
				//System.out.println("improved-> " + improvedSolution.toString());
				
				if(improvedSolution.size()== 0) //si no existe nueva solucion, la creo sin mas.
				{
					improvedSolution.addAll(actualSolution);
				}else if(getPcp().funcionObjectivo(actualSolution) < getPcp().funcionObjectivo(improvedSolution)) //checkamos
				{
					improvedSolution = new ArrayList<Point>(); //cambiamos el valor de la solucion por la solucion objetivo
					improvedSolution.addAll(actualSolution);
				
				}
				
				}

			}
		}//end for
		
		
		//System.out.println("#Solucion inicial-> " + initialSolution.toString());
		//System.out.println("#solucion improvisada-> " + actualSolution.toString());
		decrementar();

		if(c_ < getPcp().funcionObjectivo(actualSolution))
		{
			f_++;//si no mejora iteramos uno en ese momento
		}
		if(actualSolution.toString().equals(initialSolution.toString()))
		{
			//System.out.println("no mejora ");
			 
			return initialSolution;
		}else
		{
			addTabu(improvedSolution,initialSolution);
			return improvedSolution;
		}
		
	
	}
	
	
	/**
	 * metodo para checkar y meter el nodo saliente en la lista tabu
	 * @param actualSolution
	 * @param initialSolution
	 */
	private void addTabu(ArrayList<Point> actualSolution, ArrayList<Point> initialSolution) {
		
		ArrayList<Point> aux = new ArrayList<Point>();
				aux.addAll(initialSolution);
		
		//System.out.println("initial tabu-> " + initialSolution.toString());
		//System.out.println("actual tabu-> " + actualSolution.toString());
	
		
		aux.removeAll(actualSolution);
		//System.out.println("auxiliar tabu-> " + aux.toString());
		
		if(aux.size() == 1)
		{
			addTabu(aux.get(0)); //añadimos el nodo diferente de la solucion inicial
		}else
		{
			System.err.println("err-> " + aux.toString());
		}
		
	}


	/**
	 * metodo para devolver un porcentaje de los nodos clientes (conseguiremos randomizar )
	 * @param aux
	 */
	public ArrayList<Point> chooseRandomClient(ArrayList<Point> aux)
	{
		/*
		 * la idea es que coja un 50% de los nodos clientes y que priorize los que menos frecuencia tienen
		 */
		
		ArrayList<Point> tmp = new ArrayList<Point>(); //generamos la particion 

		
		
		
	  int k = aux.size(); //tamanyo de los clientes
	  
	  k = k /2; // cogemos el 50% de los datos
	 int c = k;// no puede valer 0 (cogemos 25% de los datos los menos frecuentes y 25% de los clientes random)
	 
	  int val = Integer.MAX_VALUE;
	  int index = 0;
	 
	  while(tmp.size() < c)
	  {
	  
	 for(int i = 0; i < aux.size() ; i++)
	 {


		if(val > obtenerFrecuencia(aux.get(i)))
		{
			val = obtenerFrecuencia(aux.get(i)); //ponemos el valor del contador para seguir comparando
			index = i; //indice que add al final del for
		}
	 }
	 
	 tmp.add(aux.get(index));
	 aux.remove(index);
	 index = 0;
	 
	  }
	 
	 
	 
	 
	 
	 
	 return tmp;

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
		aumFrecuencia(servidor);
	   //setClient(cliente);
	}
	
	
	/**
	 * metodo para aumentar frecuencia en uno
	 * @param servidor
	 */
	private void aumFrecuencia(Point servidor) {
		
		int index = getPcp().getValues().getDots().indexOf(servidor);
		
		getFrecuencia().get(index).incrementar();
		
		
	}





	/**
	 * checkea que el nodo i no esté tabú , a no ser que mejore el optimo global
	 * @param i
	 * @param coste compobamos el criterio de aspiracion
	 * @return
	 */
	public boolean checkServer(Point tabu, ArrayList<Point> actualSolution, int index)
	{
		
		for(int i= 0; i < getServer().size(); i++)
		{
			//System.out.println("tabu point-> " + getServer().get(i).toString());
			//System.out.println("punto compro-> " + tabu.toString());
			if((getServer().get(i).getPoint().getX() == tabu.getX()) && (getServer().get(i).getPoint().getY() == tabu.getY()))
			{
				
				/*
				 * si entra, tendremos que crear una solucion auxiliar para saber si mejora el coste optimo global
				 * entonces, pese a estar en tabu lo añadimos
				 * 
				 */
				ArrayList<Point> aux = new ArrayList<Point>(); 
				aux.addAll(actualSolution);
				aux.remove(index);
				aux.add(index, tabu);
				
				
				if( c_ <= getPcp().funcionObjectivo(aux))
				{
					return true;
				}else
				{
					c_ = getPcp().funcionObjectivo(aux); //criterio de aspiracion.
					return false;
				}
				
			}
		}
		
		return false;
		
		/*if(getServer().contains(tabu))
		{
			return true;
		}else {
			return false;
		}*/
		
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
			if( !existServer(val))
			{

			tabuServer_.add(aux);
			}else
			{
				for(int i = 0; i < getServer().size(); i++)
				{
					if((getServer().get(i).getPoint().getX() == val.getX()) && (getServer().get(i).getPoint().getY() == val.getY()))
					{
						
						tabuServer_.set(i, aux);
					}
				}
			}
	}
	
	
	private boolean existServer(Point val) {

		for(int i = 0; i < getServer().size(); i++)
		{
			if((getServer().get(i).getPoint().getX() == val.getX()) && (getServer().get(i).getPoint().getY() == val.getY()))
			{
				return true;
			}
		}
		return false;
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
		frecuencia_.add(aux);
	}
	
	/**
	 * metodo para
	 * @param args
	 */
	
	public Integer obtenerFrecuencia(Point val)
	{
		for(int i = 0; i < frecuencia_.size(); i++)
		{
			if((frecuencia_.get(i).getPoint().getX() == val.getX()) &&  (frecuencia_.get(i).getPoint().getY() == val.getY()))
			{
				return frecuencia_.get(i).getCont();
			}
		}
		
		System.err.println("no existe");
		return -1;
	}
	
	//------------------------------------------------------------------------------------------//
	//--------------------------------------TEST SECTION----------------------------------------//
	//------------------------------------------------------------------------------------------//
	public static void main(String args[]) {
		PCenterProblem pcp = new PCenterProblem("C:\\Users\\norberto\\git\\ULL-DAA-18-G2\\test\\prueba3.txt");
		TabuSearch lns = new TabuSearch(pcp);
		
		System.out.println("puntos matrix " + pcp.getValues().getDots().toString());
		//System.out.println("puntos solution " + pcp.getSolution().getDots().toString());
		ArrayList<Point> solution = lns.busqueda(pcp.getSolution().getDots());
		System.out.println("SOLUTION POINTS = " + solution);
		System.out.println("OBJECTIVE FUNCTION = " + lns.getPcp().funcionObjectivo(solution));
		System.out.println("factor f_ " + f_ );
		
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < lns.getPcp().getSolution().getDots().size(); i++) {
			locations.add(lns.getPcp().getValues().getDots().indexOf(solution.get(i)));
		}
		System.out.println("INDEXES = " + locations);
	}
	


}
