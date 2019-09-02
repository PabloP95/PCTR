import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Este programa usa el monitor {@link RWFileMonitor} para controlar la
 * exclusión mutua entre hilos.
 * 
 * @author Pablo Piedad Garrido
 */
public class UsaRWFileMonitor implements Runnable {
	static RWFileMonitor m = new RWFileMonitor();
	private int op;

	/**
	 * Este es el constructor de los objetos de la clase UsaRWFileMonitor
	 * 
	 * @param op
	 *            : Tipo de operacion que realiza
	 */
	public UsaRWFileMonitor(int op) {
		this.op = op;
	}

	/**
	 * @override En este sobrescrito método run utilizaremos un switch para
	 *           controlar que hilos se comportan como lectores y cuales se
	 *           comportan como escritor.
	 */
	public void run() {
		switch (op) {
		case 0:
			m.StartWrite();
			m.EndWrite();
			break;

		case 1:
			m.StartRead();
			m.EndRead();
			break;
		}
	}

	/**
	 * MAIN: Se crea un pool fijo de hilos de tamaño 8, y luego ejecutamos
	 * dichos hilos mediante un ejecutor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UsaRWFileMonitor[] urfm = new UsaRWFileMonitor[8];

		ExecutorService ex = Executors.newFixedThreadPool(8);

		for (int i = 0; i < urfm.length; i++) {
			urfm[i] = new UsaRWFileMonitor(i % 2);
			ex.execute(urfm[i]);
		}

		ex.shutdown();
		while (!ex.isTerminated())
			;
	}

}
