import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * @author Pablo Piedad Garrido Programa con el cual podemos obtener el tiempo
 *         del ganador de una triatlon con salida neutralizada.
 * 
 *         Tambien podremos obtener el numero del atleta ganador
 */
public class triatlonBarreras implements Runnable {

	private static long tiempos[] = new long[100];
	private static int ganador = 0;
	private int corredor;
	private static long tiempo = 0;
	CyclicBarrier parada = null;
	// Usado para poder dormir los hilos en el bloque run un tiempo aleatorio
	private Random r;

	public triatlonBarreras(CyclicBarrier parada, int corredor) {

		this.corredor = corredor;
		r = new Random(corredor);
		this.parada = parada;
	}

	public void run() {
		long inicio = System.nanoTime();
		try {
			new Thread(this).sleep(r.nextInt());
		} catch (Exception e) {
		}

		try {
			int i = parada.await();
		} catch (BrokenBarrierException e) {
		} catch (InterruptedException e) {
		}

		tiempos[corredor] += System.nanoTime() - inicio;
		System.out.println("El corredor " + corredor + " ha pasado");
	}

	public static void campeon() {
		for (int i = 0; i < 100; i++) {
			if (tiempo < tiempos[i]) {
				ganador = i;
				tiempo = tiempos[i];
			}
		}
	}

	public static void main(String[] args) {
		Thread atletas[] = new Thread[100];

		CyclicBarrier natacion = new CyclicBarrier(100);
		CyclicBarrier ciclismo = new CyclicBarrier(100);
		CyclicBarrier correr = new CyclicBarrier(100);

		System.out
				.println("Empieza la competicion con la parada de natacion\n");

		for (int i = 0; i < 100; i++) {
			atletas[i] = new Thread(new triatlonBarreras(natacion, i));
		}

		for (int i = 0; i < 100; i++) {
			atletas[i].start();
		}

		for (int i = 0; i < 100; i++) {
			try {
				atletas[i].join();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("Se ha acabado la prueba de natacion\n");
		System.out.println("Comienza la prueba de ciclismo\n");

		for (int i = 0; i < 100; i++) {
			atletas[i] = new Thread(new triatlonBarreras(ciclismo, i));
		}

		for (int i = 0; i < 100; i++) {
			atletas[i].start();
		}

		for (int i = 0; i < 100; i++) {
			try {
				atletas[i].join();
			} catch (InterruptedException e) {
			}
		}

		System.out
				.println("La prueba de ciclismo ya la han acabado todos los atletas\n");
		System.out
				.println("Comienza la ultima prueba, la prueba de carrera a pie\n");

		for (int i = 0; i < 100; i++) {
			atletas[i] = new Thread(new triatlonBarreras(correr, i));
		}

		for (int i = 0; i < 100; i++) {
			atletas[i].start();
		}
		for (int i = 0; i < 100; i++) {
			try {
				atletas[i].join();
			} catch (InterruptedException e) {
			}

		}

		campeon();

		System.out.println("\nEl ganador es el participante numero " + ganador);
		System.out.println("Ha ganado con un tiempo en total de "
				+ (tiempo / 1.0e6) + " milisegundos");
	}

}
