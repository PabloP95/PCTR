
/**
 * @author Pablo Piedad Garrido
 * 
 * En este programa hacemos una APROXIMACIÓN del valor de PI
 * Ésta varía según el número de puntos que lancemos
 * Utilizamos grano grueso para realizar el cometido 
 * anteriormente descrito.
 * 
 * Tambien utilizaremos una ArrayList con Future y la interfaz Callable para ir rellenando
 * dicho ArrayList y obtener finalmente el numero de aciertos totales
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PiParalelaFutureCont implements Callable<Integer> {
	Random r = new Random(System.nanoTime());
	public double Cx,Cy;
	private int inicio, fin;

	/**
	 * Constructor de los objetos de la clase PiParalelaFutureCont
	 * @param inicio
	 * @param fin
	 */
	public PiParalelaFutureCont(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}
	/**
	 * Metodo call sobreescrito.
	 * En este calculamos el número de puntos que cumplan la condición
	 * y los sumamos, para posteriormente devolverlos como un objeto Integer.
	 * 
	 * Dicho objeto Integer lo captará la ArrayList
	 */
	public Integer call()  
	{
		int Hits = 0;
		for (int i = inicio; i < fin; i++)
		{
			 Cx = r.nextDouble();
			 Cy = r.nextDouble();
			 if (Math.pow(Cx, 2) + Math.pow(Cy, 2) <= 1)
					 Hits++;
		}
		return Hits;
	}
	

	/**
	 * MAIN : 
	 * En este introducimos por teclado el numero de puntos a lanzar mediante entrada estandar.
	 * No se introduce mediante linea de comandos.
	 * Esta implementación es la más potente, ya que no 
	 * necesita utilizar bloques sincronizados,
	 * por ello, el tiempo de ejecución del programa es mucho menor
	 * que en las anteriores implementaciones.
	 * @see PiParalelauniCont
	 * @see PiParalelaMultiCont
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		int puntos, ini, fin;
		int N = Runtime.getRuntime().availableProcessors();
		int total = 0;

		System.out.println("Introduzca el numero de puntos a lanzar : ");
		puntos = S.nextInt();
		int ph = puntos / N;

		ini = 0;
		fin = ph;
		ArrayList <Future<Integer>>  PICont = new ArrayList<Future<Integer>>();
		long t = System.nanoTime();
		ThreadPoolExecutor PH = new ThreadPoolExecutor(N,N,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < N; i++) {
			PICont.add(PH.submit(new PiParalelaFutureCont(ini,fin)));
			ini=fin;
			fin+=ph;
		}
		
		for(Future<Integer> iterador : PICont)
			try {
				total+=iterador.get();
			} catch (InterruptedException e) {}
			catch (ExecutionException e) {}
		
		long tot = (System.nanoTime() - t) / (long) 1.0e6;
		
		PH.shutdown();
		while (!PH.isTerminated());

		double integral = 4*(double) total / puntos;

		System.out.println("Resultado = " + integral + "\nCalculado en "
				+ tot + " ms");
		
		S.close();
	}


}
