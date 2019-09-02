import java.util.concurrent.Semaphore;

public class FiloApiAN {
	private static Semaphore[] tenedor = new Semaphore[5];

	/**
	 * Constructor monitor
	 */

	public FiloApiAN() {
		for (int i = 0; i < 5; i++)
			tenedor[i] = new Semaphore(1);
	}

	/**
	 * Metodo start_eating Indica que un filosofo podrá comer si posee dos
	 * tenedores
	 * 
	 * @param numFil
	 *            numero identificador del filosofo
	 */

	public synchronized void start_eating(int numFil) {
		System.out.println("El filosofo " + numFil + " esta meditando");
		try {
			tenedor[numFil].acquire();
			tenedor[(numFil + 1) % 5].acquire();
		} catch (InterruptedException e) {
		}

		end_eating(numFil);
	}

	/**
	 * Metodo end_eating Indica que el filosofo numFil ha acabado de comer, por
	 * ello suelta los tenedores que cogio anteriormente
	 * 
	 * @param numFil
	 *            : Numero identificador del filosofo
	 */
	public void end_eating(int numFil) {
		try {
			System.out.println("El filosofo " + numFil + " esta comiendo ");
			tenedor[numFil].release();
			tenedor[(numFil + 1) % 5].release();
		} catch (Exception e) {
		}
	}

}
