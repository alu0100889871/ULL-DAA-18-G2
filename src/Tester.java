
public class Tester {
	public static void main(String[] args) {
		String[] rutas = {"../test/prueba10p3", "../test/prueba10p4", "../test/prueba10p5",
						  "../test/prueba20p3", "../test/prueba20p4", "../test/prueba20p5",
						  "../test/prueba30p3", "../test/prueba30p4", "../test/prueba30p5",
						  "../test/prueba50p3", "../test/prueba50p4", "../test/prueba50p5",
						  "../test/prueba70p3", "../test/prueba70p4", "../test/prueba70p5",
						  "../test/prueba100p3", "../test/prueba100p4", "../test/prueba100p5"};
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
