/**
 * Este monitor ha sido modificado para que pueda funcionar con vectores de
 * matrices, llamados buffer
 * 
 * @author Pablo Piedad Garrido
 * 
 */
public class monitorCadena {
	private int numSlots = 0;
	private matriz[] buffer = null;
	private int putIn = 0, takeOut = 0;
	private int cont = 0;

	/**
	 * Constructor del monitorCadena.
	 * 
	 * @param numSlots
	 *            = Numero de elementos del vector de matrices
	 */

	public monitorCadena(int numSlots) {
		this.numSlots = numSlots;
		buffer = new matriz[numSlots];
	}

	/**
	 * Con este método podemos insertar elementos de tipo matriz en el vector de
	 * matrices. Cada vez que se inserta un elemento el contador cont se
	 * incrementa a 1, para así controlar el número de elementos insertados, y
	 * en el caso en que el contador sea igual al número máximo de elementos el
	 * hilo se bloqueará.
	 * 
	 * @param valor
	 *            = Elemento matriz que se va a insertar en el vector de
	 *            matrices
	 */
	public synchronized void insertar(double[][] valor) {
		while (cont == numSlots)
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("wait interrumpido");
			}
		buffer[putIn] = new matriz(valor);
		putIn = (putIn + 1) % numSlots;
		cont++;
		notifyAll();
	}

	/**
	 * Con este método extraemos los elementos del vector de matrices que sea,
	 * disminuyendo el contador cont. Cuando el contador sea igual a 0, el hilo
	 * se bloquea, esperando a que el contador sea mayor que 0. Dicho hilo se
	 * dirige a la cola de wait-set.
	 * 
	 * @return valor.mat = Valor extraido del vector de matrices
	 *         correspondiente. Dicho valor es una matriz, que será cuadrada
	 */
	public synchronized double[][] extraer() {
		matriz valor;
		while (cont == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("wait interrumpido");
			}
		valor = buffer[takeOut];
		takeOut = (takeOut + 1) % numSlots;
		cont--;
		notifyAll();
		return valor.mat;
	}
}// Buffer

