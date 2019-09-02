/**
 * Monitor para el acceso y devolucion de impresoras
 * 
 * @author Pablo Piedad Garrido
 * 
 */

public class monitorImpresion {
	private int impresoras;
	private boolean[] libre = new boolean[3];

	/**
	 * Constructor de monitorImpresion. Aqui inicializamos los valores
	 * impresoras y el vector libre de booleano
	 * 
	 */
	public monitorImpresion() {
		impresoras = 3;
		for (int i = 0; i < libre.length; i++) {
			libre[i] = true;
		}

	}

	/**
	 * Metodo sincronizado en el cual se "pilla" una de las 3 impresoras para
	 * imprimir. Todas las impresoras pueden ser tomadas, pero en el caso en el
	 * que se quiera tomar una impresora cuando las tres ya estan ocupadas se
	 * bloqueara el proceso ,pasando el hilo que llama al metodo a la cola de
	 * wait-set
	 * 
	 * @return numero de impresora que se esta utilizando
	 */
	public synchronized int usa_imp() {
		while (impresoras == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}

		int n = 0;
		while (!libre[n])
			n++;
		libre[n] = false;
		impresoras--;
		return n;
	}

	/**
	 * En este metodo se libera la impresora n, asi el numero de impresoras se
	 * incrementa. Asi, el hilo que se encontraba en la cola de wait-set, pasa a
	 * la cola de exclusion mutua, solo en el caso en el que la condicion de
	 * guarda se cumpla
	 * 
	 * @param n
	 */
	public synchronized void dejar_imp(int n) {
		libre[n] = true;
		impresoras++;
		notifyAll();
	}

}
