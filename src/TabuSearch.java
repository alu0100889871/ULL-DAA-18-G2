import java.util.ArrayList;

/**
 * Clase donde se realizará la busqueda tabú
 * @author norberto
 *
 */
public class TabuSearch extends Algorithm {

	
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
	
	private static final int DELAY = 4; //el tiempo que permanecen en tabú el contenido.
	private	ArrayList<ArrayList<Integer>> tabuList_ = new ArrayList<ArrayList<Integer>>(); //lista para marcar los programas tabu
	private int k_ = 0; //tamanyo de los numeros de servicios.
	
	
	public TabuSearch(PCenterProblem pcp) {
		super(pcp);

		k_ = this.getPcp().getSolution().getK();
		
		for(int i = 0; i < k_ ; i++)
		{
			ArrayList<Integer> aux = new ArrayList<Integer>();
			tabuList_.add(aux);
			for(int j = 0; j < k_ ; j++)
			{
				Integer tmp = 0;
				tabuList_.get(i).add(tmp);
			}
		}
		
	}
	
	
	
	
	
	public void busqueda(ArrayList<Point> initialSolution)
	{
		/* 
		 * 
		 * hasta que (100) iteraciones ??:
		 * 
		 * 1. solucion inicial
		 * 2. lista de candidatos (a lo sumo 10 candidatos por iteración escogidos aleatoriamente)
		 * 3. iteracion con los nodos de la solucion inicial con los candidatos para hallar el mejor coste
		 * 4. poner en tabu la modificación && amuentar en la memoria a largo plazo a uno.
		 * 5. decrementar tabla tabu en uno en cada iteracion.
		 * 6. 
		 * 
		 */
		
		int w = 0;
		
		while(w < 100) // criterio de parada ?? aver esto con punto . funciona ??
		{
			
		}
	}
	
	/**
	 * Método para obtener a la frecuencia ( memoria a largo plazo) de tabuList
	 * @param i
	 * @param j
	 * @return
	 */
	
	public Integer getFrequency(int i, int j)
	{
		assert(i > tabuList_.size());
		assert(j > tabuList_.size());
		
		if(i < j)
		{
			return tabuList_.get(j).get(i);
		}else
		{
			return tabuList_.get(i).get(j);
		}
	}
	
	
	/** 
	 * metodo para obtener las frecuencia de las modificaciones
	 * @param i
	 * @param j
	 */
	public void  setFrequency(int i,int j)
	{
		
		
		
		if(i < j)
		{
			tabuList_.get(j).set(i, tabuList_.get(j).get(i) + 1);
		}else
		{
			tabuList_.get(i).set(j, tabuList_.get(i).get(j) + 1);
		}
	}
	
	/**
	 * método para obtener el valor de los valores tabú
	 * @param i
	 * @param j
	 * @return
	 */
	public Integer getTabu(int i, int j)
	{
		if(i > j)
		{
			return tabuList_.get(j).get(i);
		}else
		{
			return tabuList_.get(i).get(j);
		}
	}
	
	/**
	 * método por el cual añado a la lista tabu la modificacion.
	 * @param i
	 * @param j
	 */
	public void  setTabu(int i,int j)
	{
		
		
		
		if(i > j)
		{
			tabuList_.get(j).set(i, tabuList_.get(j).get(i) + DELAY);
		}else
		{
			tabuList_.get(i).set(j, tabuList_.get(i).get(j) + DELAY);
		}
	}
	
	/**
	 * proceso para decrementar las listas tabu en uno, lo usaremos en cada iteracion de la búsqueda
	 */
	public void decrementarTabu()
	{
		for(int i = 0 ; i < k_ ; i++)
		{
			for(int j = i +1; j < k_; j++)
			{
				if( tabuList_.get(i).get(j) > 0)
				{
					tabuList_.get(i).set(j, tabuList_.get(i).get(j) -1);
				}

			}
		}
	}
	
	/*
	 * 
	 * 	GETTERS & SETTERS
	 * 
	 * 
	 */
	
	
		public int getK()
		{
			return k_;
		}
		
		public ArrayList<ArrayList<Integer>> getTabuList()
		{
			return tabuList_;
		}

}
