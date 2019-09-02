import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class usaFiloApiAN implements Runnable {

	private int numFil;
	public FiloApiAN dining = new FiloApiAN();

	public usaFiloApiAN(int numFil) {
		this.numFil = numFil;
	}

	public void run() {
		dining.start_eating(numFil);
	}

	public static void main(String[] args) {
		int Filosofos = 5;
		ExecutorService ex = Executors.newFixedThreadPool(Filosofos);
		for (int i = 0; i < Filosofos; i++) {
			ex.execute(new usaFiloApiAN(i));
		}
		while (!ex.isTerminated())
			ex.shutdown();

	}

}
