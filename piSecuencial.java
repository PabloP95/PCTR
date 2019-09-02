/**
 * @author Pablo Piedad Garrido
 *  Este programa calcula una aproximacion del numero PI, la cual será más o menos
 * exacta según el número de puntos a lanzar. A mayor numero de puntos, mayor será la exactitud.	
 */

import java.util.Random;
import java.util.Scanner;

public class piSecuencial {

	/**
	 * MAIN: Aqui se pide el numero de puntos, el cual se pasa por valor al
	 * metodo calculoAciertos. Con el número de aciertos y el número de puntos
	 * lanzados podemos calcular la aproximación del número PI.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		double pi = 0.0;
		int nPuntos, aciertos;
		Scanner S = new Scanner(System.in);
		System.out.println("Introduzca el numero de puntos a lanzar");
		nPuntos = S.nextInt();
		long inicio = System.nanoTime();
		aciertos = calculoAciertos(nPuntos);

		pi = (4.0 * aciertos / nPuntos);
		long fin = (long) ((System.nanoTime() - inicio) / 1.0e6);
		System.out.println(pi);
		System.out.println("Ha tardado " + fin + "milisegundos");
	}

	/**
	 * Este metodo es el encargado de hallar el numero de aciertos
	 * 
	 * @param nPuntos
	 * @return nAciertos
	 */
	public static int calculoAciertos(int nPuntos) {
		Random r = new Random(System.nanoTime());
		int nAciertos = 0;
		for (int i = 0; i < nPuntos; i++) {
			double cx = r.nextDouble();
			double cy = r.nextDouble();

			if ((Math.pow(cx, 2) + Math.pow(cy, 2)) < 1)
				nAciertos++;
		}

		return nAciertos;
	}
}
