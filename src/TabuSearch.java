import java.util.ArrayList;

/**
 * Clase donde se realizar� la busqueda tab�
 * @author norberto
 *
 */
public class TabuSearch extends Algorithm {

	
	/*
	 * La busqueda tabu es un m�todo de busqueda local que utiliza la memoria para
	 * as� evitar la repetici�n de soluciones que ya hay�n sido testeada
	 * el criterio de movimiento que pensamos emplear para determinar la vecindad
	 * es el intercambio (swap) que para nuestro caso ser� el intercambio de un nodo 
	 * servicio de la soluci�n inicial por la de un nodo candidato.  
	 * 
	 * Lista tab�: contendr� el cambio de nodo servicio que ha sido reemplazado.
	 * criterio de aspiraci�n: si minimisa el coste de nodo m�ximo. 
	 *
	 */
	
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
	
	
	
	/**
	 * M�todo para obtener a la frecuencia ( memoria a largo plazo) de tabuList
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
	
	
	public void  setTabu(int i,int j)
	{
		
		
		
		if(i > j)
		{
			tabuList_.get(j).set(i, tabuList_.get(j).get(i) + 3);
		}else
		{
			tabuList_.get(i).set(j, tabuList_.get(i).get(j) + 3);
		}
	}
	
	
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
