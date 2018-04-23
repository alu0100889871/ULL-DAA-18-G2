import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PCenterProblemReader {
	String path;
	
	public PCenterProblemReader(String path) {
		this.path = path;
	}
	
	public PCenterProblem generate() {
		int k = 0;
		ArrayList<Point> dots = new ArrayList<Point>();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		k = Integer.parseInt(lines.get(0));
		lines.remove(0);
		while(lines.size() > 0) {
			StringTokenizer st = new StringTokenizer(lines.get(0));
			lines.remove(0);
			dots.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) ));
		}
		return new PCenterProblem(k, dots);
	}
	
	public ArrayList<String> read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.path));
		String line;
		ArrayList<String> pcp = new ArrayList<String>();
		while((line = br.readLine()) != null) {
			pcp.add(line);
		}
		br.close();
		return pcp;
	}
}
