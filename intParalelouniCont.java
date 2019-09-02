/**
 * @author Pablo Piedad Garrido
 * 
 * En este programa hacemos una APROXIMACIÓN del valor de 
 * la integral [0-1] de sin(x).
 * Ésta varía según el número de puntos que lancemos
 * Utilizamos grano grueso para realizar el cometido 
 * anteriormente descrito.
 *
*/


import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class intParalelouniCont implements Runnable {
	Random r = new Random(System.nanoTime());
	Object candado = new Object();
	public static volatile int Hits = 0;
	public double Cx,Cy;
	private int inicio, fin;

	/**
	 * Constructor de los objetos de la clase intParalelouniCont
	 * @param inicio
	 * @param fin
	 */

	public intParalelouniCont(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}
	/**
	 * Sobreescritura del metodo run
	 * Para incrementar el numero de aciertos, lo hacemos mediante un bloque sincronizado 
 	 *	asi no se produce entrelazado
	 */
	public void run() {
		for (int i = inicio; i < fin; i++) {
			 Cx = r.nextDouble();
			 Cy = r.nextDouble();
			 if (Cy < Math.sin(Cx))
				 synchronized (candado) {
					 Hits++;
				 }
			}
		}

	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		int puntos, ini, fin;
		int N = Runtime.getRuntime().availableProcessors();
		intParalelouniCont[] v = new intParalelouniCont[N];

		System.out.println("Introduzca el numero de puntos a lanzar : ");
		puntos = S.nextInt();
		int ph = puntos / N;

		ini = 0;
		fin = ph;
		long t = System.nanoTime();
		ExecutorService ex = Executors.newCachedThreadPool();

		for (int i = 0; i < N; i++) {
			v[i] = new intParalelouniCont(ini, fin);
			ini = fin;
			fin += ph;
			if (puntos - fin <= puntos)
				fin = puntos;

			ex.execute(v[i]);
		}

		ex.shutdown();

		while (!ex.isTerminated());
		long total = (System.nanoTime() - t) / (long) 1.0e6;

		double integral = (double) Hits / puntos;

		System.out.println("Resultado = " + integral + "\nCalculado en "
				+ total + " ms");
		
		S.close();
	}

}
