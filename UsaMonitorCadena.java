import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * En este programa usa el {@link monitorCadena}, por ello hay que ponerlo en el
 * código de este programa.
 * 
 * @author Pablo Piedad Garrido
 */

public class UsaMonitorCadena implements Runnable {

	double[][] valor = new double[10][10];
	static monitorCadena buffer_1 = new monitorCadena(100);
	static monitorCadena buffer_2 = new monitorCadena(50);
	private int n;
	Random r = new Random(System.nanoTime());

	/**
	 * Constructor de los objetos UsaMonitorCadena
	 * 
	 * @param n
	 */
	public UsaMonitorCadena(int n) {
		this.n = n;
	}

	/**
	 * @override
	 * 
	 *           En este método run sobrescrito utilizamos un switch con tres
	 *           opciones, cada una es una de las etapas de la cadena de
	 *           montaje.
	 */
	public void run() {

		double[][] traspuesta = new double[10][10];
		for (int k = 0; k < 100; k++) {
			double diag = 1;
			switch (n) {
			case 0: // procesoA
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						valor[i][j] = (double) r.nextInt(5);
					}
				}

				buffer_1.insertar(valor);
				break;

			case 1:

				valor = buffer_1.extraer();// procesoB

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						traspuesta[j][i] = valor[i][j];
					}
				}
				buffer_2.insertar(traspuesta);
				break;

			case 2:

				valor = buffer_2.extraer();
				for (int i = 0; i < 10; i++) {
					diag = diag * valor[i][i];
				}

				System.out.println("El resultado es : " + diag);
				break;

			}
		}
	}

	/**
	 * MAIN. Aqui realizamos un pool fijo de numero de hilos igual al numero de
	 * etapas de la cadena de montaje Luego los ejecutamos.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		ExecutorService ex = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; i++) {
			ex.execute(new UsaMonitorCadena(i));
		}

		ex.shutdown();
		while (!ex.isTerminated())
			;

	}

}
