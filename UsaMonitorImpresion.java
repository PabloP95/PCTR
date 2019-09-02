import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * En este programa usaremos el monitor de impresion el cual se encuentra en
 * {@link monitorImpresion}
 * 
 * @author Pablo Piedad Garrido
 * 
 */

public class UsaMonitorImpresion implements Runnable {
	private static monitorImpresion m = new monitorImpresion();
	private static int N = Runtime.getRuntime().availableProcessors();
	private int i;

	/**
	 * Constructor de la clase UsaMonitorImpresion
	 * 
	 * @param i
	 */
	public UsaMonitorImpresion(int i) {
		this.i = i;
	}

	/**
	 * @override
	 * 
	 *           En el metodo run sobreescrito se utilizan los metodos del
	 *           monitor usa_imp() y dejar_imp(n)
	 */
	public void run() {
		int n = m.usa_imp();
		System.out.println("La impresora numero " + (n)
				+ " esta ocupada con el hilo " + i);
		m.dejar_imp(n);
		System.out.println("La impresora numero " + (n) + " esta libre");
	}

	/**
	 * Con este metodo utilizamos la formula de Subramanian
	 * 
	 * @param N
	 *            = Numero de procesadores logicos de la maquina
	 * @return Nt = numero de hilos del pool de hilos
	 */
	public static double subramanian(int N) {
		System.out
				.println("Siendo el coeficiente de bloqueo = 0.5\nSe van a formar : ");
		double Nt = N / (1 - 0.5);
		System.out.println(Nt + " hilos");
		return Nt;
	}

	/**
	 * MAIN : Aqui se crea un pool dinamico cuyo numero de hilos viene de la
	 * funcion subramanian. Luego, creamos objetos de la clase
	 * UsaMonitorImpresion utilizando el constructor, para luego ejecutar los
	 * hilos con dichos objetos.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		double Nt = subramanian(N);
		UsaMonitorImpresion[] umi = new UsaMonitorImpresion[(int) Nt];
		ExecutorService ex = Executors.newCachedThreadPool();

		for (int i = 0; i < umi.length; i++) {
			umi[i] = new UsaMonitorImpresion(i);
			ex.execute(umi[i]);
		}

		ex.shutdown();

		while (!ex.isTerminated()) {
		}

		System.out.println("Fin del programa");

	}

}
