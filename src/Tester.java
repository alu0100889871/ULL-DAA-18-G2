
public class Tester {
	public static void main(String[] args) {
		String[] rutas = {"C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test10p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test10p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test10p5",
						  "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test20p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test20p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test20p5",
						  "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test30p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test30p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test30p5",
						  "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test50p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test50p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test50p5",
						  "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test70p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test70p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test70p5",
						  "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test100p3", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test100p4", "C:\\Users\\gamea\\git\\ULL-DAA-18-G2\\test\\test100p5"};
			for(String ruta:rutas) {
				String[] gaArgs = {ruta};
				GreedyAlgorithm.main(gaArgs);
				String[] gArgs = {ruta, "2"};
				GRASPAlgorithm.main(gArgs);
				String[] gArgs2 = {ruta, "3"};
				GRASPAlgorithm.main(gArgs2);
				String[] mbArgs = {ruta, "2", "5"};
				MultibootAlgorithm.main(mbArgs);
				String[] mbArgs2 = {ruta, "2", "10"};
				MultibootAlgorithm.main(mbArgs2);
				String[] mbArgs3 = {ruta, "2", "20"};
				MultibootAlgorithm.main(mbArgs3);
				String[] mbArgs4 = {ruta, "3", "5"};
				MultibootAlgorithm.main(mbArgs4);
				String[] mbArgs5 = {ruta, "3", "10"};
				MultibootAlgorithm.main(mbArgs5);
				String[] mbArgs6 = {ruta, "3", "20"};
				MultibootAlgorithm.main(mbArgs6);
				String[] vnsArgs = {ruta};
				BasicVNSAlgorithm.main(vnsArgs);
				String[] tsArgs = {ruta};
				TabuSearch.main(tsArgs);
				String[] lnsArgs = {ruta};
				LNSAlgorithm.main(lnsArgs);
		}
	}
}
