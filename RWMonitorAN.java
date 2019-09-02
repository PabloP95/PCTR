import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Este es el monitor implementado en Java para el {@link UsaRWFileMonitor} En
 * este monitor habrán cuatro métodos, dos para los lectores, y dos para el
 * escritor.
 * 
 * @author Pablo Piedad Garrido
 */

// En esta versión se imprimen y se leen numeros en vez de una frase
class RWMonitorAN {
	int[] v = { 5, 7, 12, 8, 30, 5, 2, 9 };
	private static int i = 0;
	volatile int readers = 0;
	volatile boolean writing = false;
	File data = new File("datos.dat");
	public RandomAccessFile r;

	private static ReentrantLock cerrojo = new ReentrantLock();
	private static Condition leer = cerrojo.newCondition();
	private static Condition escribir = cerrojo.newCondition();

	/**
	 * Constructor de RWMonitorAN En el creamos un objeto RandomAccessFile cuyo
	 * nombre viene dado en data .Este archivo será de lectura-escritura
	 * (Read-Write).
	 */
	public RWMonitorAN() {
		try {
			r = new RandomAccessFile(data, "rw");
		} catch (FileNotFoundException e) {
		}

	}

	/**
	 * Con este método se notifica al hilo lector que empiece a leer, siempre y
	 * cuando se cumpla la condición de guarda (es decir, mientras que no haya
	 * nadie escribiendo)
	 */
	void StartRead() {
		cerrojo.lock();
		while (writing)
			try {
				leer.await();
			} catch (InterruptedException e) {
			}
		readers = readers + 1;

		System.out.println("Lector inicia lectura...");

		try {
			r.seek(0);
			System.out.println(r.readLine());
		} catch (IOException e) {
		}

		leer.signal();
		cerrojo.unlock();
	}

	/**
	 * En este método se indica a los hilos lectores que acaban. Además, si no
	 * hay lectores, le mandará una señal al hilo escritor. Antes de ejecutar
	 * los hilos, se comprueba otra vez la condición de guarda para asi tomar la
	 * decisión de bloquear dicho hilo o seguir ejecutándolo.
	 */
	void EndRead() {
		cerrojo.lock();
		readers = readers - 1;
		if (readers == 0)
			escribir.signal();
		System.out.println("Lector finaliza lectura...");
		cerrojo.unlock();
	}

	/**
	 * Este método indica el inicio de la escritura. Antes de escribir
	 * comprobamos que se cumple con la condicion de guarda .De no ser asi el
	 * hilo se bloquea en su cola de escribir. En el caso en el que la condición
	 * de guarda se cumpla, seguimos con la ejecución del hilo, en la cual se
	 * escribe un dato numérico en el archivo datos.dat
	 */
	void StartWrite() {
		cerrojo.lock();
		while (writing || (readers != 0))
			try {
				escribir.await();
			} catch (InterruptedException e) {
			}
		writing = true;
		System.out.println("Escritor inicia escritura...");

		try {
			r.writeBytes("" + v[i] + " ");
		} catch (IOException e) {
		}

		cerrojo.unlock();

	}

	/**
	 * Este método es el que indica el fin de la escritura.
	 */
	void EndWrite() {
		cerrojo.lock();
		i++;
		writing = false;
		leer.signal();
		System.out.println("Escritor finaliza escritura...");
		cerrojo.unlock();
	}
}