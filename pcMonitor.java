/**
 * 
 * @author Pablo Piedad Garrido
 */

public class pcMonitor {
	private int numSlots = 0;
	private double[] vector = null;
	private int putIn = 0, takeOut = 0;
	private int cont = 0;

	/**
	 * Constructor de pcMonitor : Se introduce el numero de elementos del
	 * vector, y se crea un vector de longitud numSlots de double
	 * 
	 * @param numSlots
	 */
	public pcMonitor(int numSlots) {
		this.numSlots = numSlots;
		vector = new double[numSlots];
	}

	/**
	 * Con este metodo estamos cuidando de que no haya indeterminismo a la hora
	 * de insertar datos.
	 * 
	 * @param valor
	 */
	public synchronized void insertar(double valor) {
		while (cont == numSlots)
			// Mientras que este llena se espera
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Interrumpido");
			}
		vector[putIn] = valor;
		putIn = (putIn + 1) % numSlots;
		cont++;
		notifyAll();
	}

	/**
	 * Con este metodo estamos cuidando de que no haya indeterminismo a la hora
	 * de extraer datos. Ademas, bloqueamos los hilos que no cumplan con la
	 * condicion de guarda establecida. Dichos hilos se van a una cola de
	 * espera, en la cual se quedarán hasta que no se ejecute el metodo
	 * notifyAll().
	 */
	public synchronized void extraer() {
		double valor;

		while (cont == 0)
			// Mientras que esta vacia espera
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Wait interrumpido");
			}

		valor = vector[takeOut];

		takeOut = (takeOut + 1) % numSlots;
		cont--;
		notifyAll();
	}
}
