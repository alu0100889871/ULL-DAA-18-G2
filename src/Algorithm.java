import java.util.ArrayList;
/**
 * Clase base para todos los algoritmos de resolución de problemas de p-centro
 * @author Sebastián José Díaz Rodríguez
 * @author Ernesto Echeverría González
 * @author Norberto García Gaspar
 * @author Victoria Quintana Martí
 * 
 * @version 1.0.0
 * @since 17/04/2018
 * 
 */
public class Algorithm {
	private PCenterProblem pcp;
	private ArrayList<Integer> facilities; //LOCALIZACIONES O SERVICIOS
	private ArrayList<Integer> clients; // DEMANDANTES O CLIENTES
	
	public Algorithm(PCenterProblem pcp, ArrayList<Integer> facilities, ArrayList<Integer> clients) {
		this.pcp = new PCenterProblem(pcp.getSolution().getK(), pcp.getValues().getDots());
		this.facilities = new ArrayList<Integer>(facilities);
		this.clients = new ArrayList<Integer>(clients);
	}
	
	public PCenterProblem getPcp() {
		return pcp;
	}
	public void setPcp(PCenterProblem pcp) {
		this.pcp = pcp;
	}
	public ArrayList<Integer> getFacilities() {
		return facilities;
	}
	public void setFacilities(ArrayList<Integer> facilities) {
		this.facilities = facilities;
	}
	public ArrayList<Integer> getClients() {
		return clients;
	}
	public void setClients(ArrayList<Integer> clients) {
		this.clients = clients;
	}
	
}
