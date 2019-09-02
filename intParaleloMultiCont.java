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

public class intParaleloMultiCont implements Runnable {
	
	Random r = new Random(System.nanoTime());
	Object candado = new Object();
	
	public static volatile int Hits = 0;
	private double Cx,Cy;
	private int inicio, fin;
	private int contHilo=0;
	
	/**
	 * Constructor de los objetos de la clase PiParalelauniCont
	 * @param inicio
	 * @param fin
	 */
	
	public intParaleloMultiCont(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}
	/**
	 * Sobreescritura del metodo run
	 * Para incrementar el número de aciertos, lo hacemos mediante un bloque sincronizado 
 	 *	asi no se produce entrelazado.
 	 *
 	 *Ahora, cada hilo posee un numero de aciertos independiente
 	 *Este se suma al numero de aciertos TOTALES = HITS.
	 */
	
	public void run()
	{
		for (int i = inicio; i < fin; i++)
		{
			 Cx = r.nextDouble();
			 Cy = r.nextDouble();
			if (Cy < Math.sin(Cx))
					contHilo++;
			}
		synchronized(candado)
		{
			Hits+=contHilo;
		}
	}
	/**
	 * MAIN : 
	 * En este introducimos por teclado el numero de puntos a lanzar mediante entrada estandar.
	 * No se introduce mediante linea de comandos.
	 * Este programa, aunque utilice grano grueso 
	 * y produzca menos bloques sincronizados
	 * es más lento que su contraparte intParaleloFutureCont
	 * @see intParaleloFutureCont
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		int puntos, ini, fin;
		int N = Runtime.getRuntime().availableProcessors();
		intParaleloMultiCont[] v = new intParaleloMultiCont[N];

		System.out.println("Introduzca el numero de puntos a lanzar : ");
		puntos = S.nextInt();
		int ph = puntos / N;

		ini = 0;
		fin = ph;
		long t = System.nanoTime();
		ExecutorService ex = Executors.newCachedThreadPool();

		for (int i = 0; i < N; i++) {
			v[i] = new intParaleloMultiCont(ini, fin);
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
