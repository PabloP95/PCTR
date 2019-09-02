/**
 * @author Pablo Piedad Garrido
 */
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class pcHebras implements Runnable {
	private static pcMonitor p = new pcMonitor(100);
	private static double j = 0;
	private static int n;
	private int nThreads;

	/**
	 * Constructor de pcHebras. En este se le pasa el numero de hilos
	 * 
	 * @param nThreads
	 */
	public pcHebras(int nThreads) {
		this.nThreads = nThreads;
	}

	/**
	 * Segun el numero de hilos puede insertar un valor j, el cual valdrá 0, y
	 * se irá incrementando, o extraer un valor
	 */
	public void run() {
		switch (nThreads) {

		case 0:
			for (int i = 0; i < n; i++) {
				p.insertar(j);
				j++;
			}
			break;

		case 1:
			for (int i = 0; i < n; i++) {
				p.extraer();
			}
			break;
		}

	}

	/**
	 * MAIN : Aqui se lanzan los hilos, 10 para ser exactos. 5 de ellos
	 * insertarán datos, mientras que los otros 5 extraerán dichos datos
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		System.out.println("Introduzca el numero de elementos : ");
		n = S.nextInt();
		ExecutorService ex = Executors.newFixedThreadPool(10);
		long inicio = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			ex.execute(new pcHebras(i % 2));
		}

		ex.shutdown();
		while (!ex.isTerminated()) {
		}

		long fin = System.nanoTime();

		System.out.println("Tiempo : " + (fin - inicio) / 1.0e6
				+ " milisegundos");
	}

}
