import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * 
 * @author Sebasti�n Jos� D�az Rodr�guez
 * @author Ernesto Echeverr�a Gonz�lez
 * @author Norberto Garc�a Gaspar
 * @author Victoria Quintana Mart�
 * 
 * @version 1.0.0
 * @since 17/04/2018
 *
 * Reader for a p-center problem.
 *
 */
public class PCenterProblemReader {
	String path;
	
	/**
	 * Reader constructor.
	 * @param path string Path of the file.
	 */
	public PCenterProblemReader(String path) {
		this.path = path;
	}
	/**
	 * Generates the p-centerProblem.
	 * @return PCenterProblem p-center Problem generated.
	 */
	public PCenterProblem generate() {
		int k = 0;
		ArrayList<Point> dots = new ArrayList<Point>();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = read();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		k = Integer.parseInt(lines.get(0));
		lines.remove(0);
		while(lines.size() > 0) {
			StringTokenizer stringTokenizer = new StringTokenizer(lines.get(0));
			lines.remove(0);
			dots.add(new Point(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken())));
		}
		return new PCenterProblem(k, dots);
	}
	/**
	 * 
	 * @return Reads the file and stores it in memory.
	 * @throws IOException exception thrown if the file cannot be accessed.
	 */
	public ArrayList<String> read() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path));
		String line;
		ArrayList<String> pCenterProblem = new ArrayList<String>();
		while((line = bufferedReader.readLine()) != null) {
			pCenterProblem.add(line);
		}
		bufferedReader.close();
		return pCenterProblem;
	}
}
