import java.util.ArrayList;
/**
 * Clase base para todos los algoritmos de resoluci�n de problemas de p-centro
 * @author Sebasti�n Jos� D�az Rodr�guez
 * @author Ernesto Echeverr�a Gonz�lez
 * @author Norberto Garc�a Gaspar
 * @author Victoria Quintana Mart�
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
