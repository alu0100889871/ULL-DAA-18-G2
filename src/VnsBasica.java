import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;



/**
 * 
 * @author Victoria Quintana Martín.
 * @author Ernesto Echevarria Gonzalez. 
 * @author Sebastian Diaz Rodriguez.
 * @author Norberto Garcia Gaspar.
 *
 */



public class VnsBasica {

	
	private ArrayList<ArrayList<Integer>> conjunto_ = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> servicios_ = new ArrayList<Integer>();
	private ArrayList<Integer> cliente_ = new ArrayList<Integer>();
	
	private ArrayList<String> Solucion = new ArrayList<String>();
	private int size_ ;
	
	private int coste; // variable coste para la iteracion del vns
	
	public VnsBasica(String path)
	{
	      File file = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      
	      
	      /*
	       *  ##Formato delfichero
	       *  k -> nº máximo de loclizaciones
	       *  NL -> nº nodos de las localizaciones
	       *  X Y -> coordenadas de localizaciones
	       *  ND -> nº de clientes de D.
	       *  X Y -> coordenadas de demandantes ( clientes)
	       * 
	       * 
	       */
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         file = new File (path);
	         fr = new FileReader (file);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         
	         
	         String linea;
	         StringTokenizer token;

	         
	         //cogemos el tamanyo
	         linea = br.readLine();
	         token = new StringTokenizer(linea);
	        
	        size_ = Integer.parseInt(token.nextToken());
	        
	     
	        //asignamos los clientes
	         linea = br.readLine();
	         token = new StringTokenizer(linea);
	         
	         while(token.hasMoreTokens())
	         {
	        	 cliente_.add(Integer.parseInt(token.nextToken()));
	         }
	       
	         
	         //asignamos los nodos servicios
	         
	         linea = br.readLine();
	         token = new StringTokenizer(linea);
	         
	         while(token.hasMoreTokens())
	         {
	        	 servicios_.add(Integer.parseInt(token.nextToken()));
	         }
	         //generamos la matriz de coste

	        

	         for(int i = 0; i< size_ ; i++)
	         {
		         linea = br.readLine();
		         token = new StringTokenizer(linea);
		         ArrayList<Integer> aux = new ArrayList<Integer>();
		         for(int j = 0; j < size_; j++)
		         {
		        	 aux.add(Integer.parseInt(token.nextToken()));
		         }
		         conjunto_.add(aux);
	         }
	        
	        
	        
	      } catch(Exception e){
	         e.printStackTrace();
	      }


	}
	
	
	
	public	void Vns() // como parametros pondremos la solucion
	{
		
		
		/*
		 * sea N sub k, tal que K = 1,...,k un conjunto de estructura de vecindad a usar en la busqueda.
		 * 
		 * Criterio de paro = Cuando todos los clientes llegue al ultimo vector servicios ??
		 * 
		 * Repeat Until Criterio de paro 
		 * 
		 * Sea k <- 1
		 * 
		 * repeat until k = k max
		 * 
		 * genera un punto aleatorio x'  vecina de x.
		 * 
		 * Busqueda local sobre x' como referencia encontramos x''
		 * 
		 * si x'' < x 
		 * entonces = set x <- x'' y k<- 1
		 * else k <- k+1
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 *  4 niveles de k:
		 *  la primera -> anyadiir un elemento ( si se puede)
		 *  la segunda -> eliminar un elemento (si se puede)
		 *  Intercambiar -> intecambiar un elmento.
		 *  Intecambiar -> intercambiar k elementos.(si se puede) 
		 *  
		 */
		
		
		
		
	}
	
	public void mostrar()
	{
		for(int i = 0; i < conjunto_.size(); i++)
		{
			for(int j = 0; j < conjunto_.size(); j++)
			{
				System.out.print(conjunto_.get(i).get(j) + "  ");
			}
			
			System.out.println("//");
		}
	}
	
	
	

}
