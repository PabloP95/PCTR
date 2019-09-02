/**
 * @author Pablo Piedad Garrido
 * 

 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class piParalelo implements Runnable {

	private static AtomicInteger aciertos = new AtomicInteger(0);
	private int inicio, fin;

	public piParalelo(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}

	public void run() {

		for (int i = inicio; i < fin; i++) {
			double cx = Math.random();
			double cy = Math.random();
			if ((Math.pow(cx, 2) + Math.pow(cy, 2)) < 1)
				aciertos.incrementAndGet();
		}
	}

	/**
	 * Programa que realiza una aproximación de PI a traves del metodo de
	 * MonteCarlo. Será necesario la ejecución en una consola de comandos. Se
	 * deberá ejecutar este programa poniendo el numero de hilos a lanzar y el
	 * numero de puntos a lanzar, separados por un espacio.
	 * 
	 * Se utiliza un entero atomico inicializado a 0.
	 */
	public static void main(String[] args) {

		int nHilos = Integer.parseInt(args[0]);
		int nPuntos = Integer.parseInt(args[1]);
		int begin = 0;
		int ventana = nPuntos / nHilos;
		int next = ventana;

		ExecutorService ex = Executors.newFixedThreadPool(nHilos);
		long inicio = System.nanoTime();
		for (int i = 0; i < nHilos; i++) {
			ex.execute(new piParalelo(begin, next));
			begin = next;
			next += ventana;
			if (nPuntos - next <= nPuntos)
				next = nPuntos;
		}

		ex.shutdown();
		while (!ex.isTerminated())
			;
		long fin = (long) ((System.nanoTime() - inicio) / 1.0e6);
		double PI = (4.0 * aciertos.get() / nPuntos);
		System.out.println(PI);
		System.out.println("Se ha calculado en " + fin + " milisegundos");
	}
}
