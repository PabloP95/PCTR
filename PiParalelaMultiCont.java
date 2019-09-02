
/**
 * @author Pablo Piedad Garrido
 * 
 * En este programa hacemos una APROXIMACIÓN del valor de PI
 * Ésta varía según el número de puntos que lancemos
 * Utilizamos grano grueso para realizar el cometido 
 * anteriormente descrito
 */

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PiParalelaMultiCont implements Runnable {
	Random r = new Random(System.nanoTime());
	static Object candado = new Object();
	public static volatile int Hits = 0;
	public double Cx,Cy;
	private int inicio, fin;
	private  int ContHilo = 0;

	/**
	 * Constructor de los objetos de la clase PiParalelaMultiCont
	 * @param inicio
	 * @param fin
	 */
	public PiParalelaMultiCont(int inicio, int fin) {
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
			 if (Math.pow(Cx, 2) + Math.pow(Cy, 2) <= 1)
				ContHilo++;
		}
		 synchronized (candado)
		 {
			 Hits+=ContHilo;
		}
	}

	
	/**
	 * MAIN : 
	 * En este introducimos por teclado el numero de puntos a lanzar mediante entrada estandar.
	 * No se introduce mediante linea de comandos.
	 * Este programa, aunque utilice grano grueso 
	 * y realice menos bloques sincronizados 
	 * es más lento que su contraparte PiParalelaFutureCont.
	 * @see PiParalelaFutureCont
	 * @param args
	 */
	
	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		int puntos, ini, fin;
		int N = Runtime.getRuntime().availableProcessors();
		PiParalelaMultiCont[] v = new PiParalelaMultiCont[N];

		System.out.println("Introduzca el numero de puntos a lanzar : ");
		puntos = S.nextInt();
		int ph = puntos / N;

		ini = 0;
		fin = ph;
		long t = System.nanoTime();
		ExecutorService ex = Executors.newCachedThreadPool();

		for (int i = 0; i < N; i++) {
			v[i] = new PiParalelaMultiCont(ini, fin);
			ini = fin;
			fin += ph;
			if (puntos - fin <= puntos)
				fin = puntos;

			ex.execute(v[i]);
		}

		ex.shutdown();

		while (!ex.isTerminated());
		long total = (System.nanoTime() - t) / (long) 1.0e6;

		double integral = 4*(double) Hits / puntos;

		System.out.println("Resultado = " + integral + "\nCalculado en "
				+ total + " ms");
		
		S.close();
	}

}
