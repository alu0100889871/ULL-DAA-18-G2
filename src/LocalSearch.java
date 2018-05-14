import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Victoria Quintana Martín.
 * @author Ernesto Echevarria Gonzalez. 
 * @author Sebastian Diaz Rodriguez.
 * @author Norberto Garcia Gaspar.
 *
 */


public class LocalSearch{

	/**
	 * method to intersec two ArrayList
	 * @param a
	 * @param b
	 * @return
	 */
	
    private static ArrayList<Integer> diferencia(ArrayList<Integer> a, ArrayList<Integer> b){
	ArrayList<Integer> c = new ArrayList<Integer>();
	for(Integer elem: a)
		if(!b.contains(elem)){c.add(elem);}
	return c;
    }
	/**
	 * Method to switch one node in de solution vector for another facilitie´s node
	 * @param solucion
	 * @param service
	 * @param nCam
	 */
	public static void move(ArrayList<Integer> solucion, ArrayList<Integer> service, int nCam)
	{
		
		Random r = new Random();
		int i = 0; //indice del bucle
		ArrayList<Integer> candidatos = new ArrayList<Integer>(); // nodos candidatos
		//ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		candidatos = diferencia(service,solucion);

		

		/*
		 * loop to swap de numbers
		 */
		
		while((i < nCam) && (candidatos.size() != 0) )
		{
			//System.out.println("sol -> " + solucion.size());
			//System.out.println("cand-> " + candidatos.toString());
			int k = r.nextInt(solucion.size()); //numero aleatorio para solucion
			int a = r.nextInt(candidatos.size()); // numero aleatorio para los nodos candidatos
			
			
			solucion.set(k, candidatos.get(a));
			candidatos.remove(a);
		}
		
		
	}
}
