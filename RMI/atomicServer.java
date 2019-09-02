import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class atomicServer implements Runnable {

	static double t = 0;
	Socket enchufe;
	private AtomicLong contador;
	static ThreadPoolExecutor ex = new ThreadPoolExecutor(10, 20, 0L,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	public atomicServer(Socket enc) {
		enchufe = enc;
		ex.execute(this);
	}

	public void run() {
		try {
			contador.incrementAndGet();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					enchufe.getInputStream()));
			String data = entrada.readLine();
			int j;
			int i = Integer.valueOf(data).intValue();
			for (j = 1; j <= 20; j++) {
				System.out.println("El hilo " + new Thread(this).getName()
						+ " esta escribiendo el dato " + i);
				new Thread(this).sleep(1000);
			}
			enchufe.close();
			System.out.println("El hilo " + new Thread(this).getName()
					+ " ha cerrado su conexion ... ");
			ex.shutdown();
			contador.decrementAndGet();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		int puerto = 2001;
		t = System.currentTimeMillis();
		try {
			ServerSocket chuff = new ServerSocket(puerto, 3000);
			while (true) {
				System.out.println("Esperando solicitud de conexion ...");
				Socket cable = chuff.accept();
				System.out.println("Recibida solicitud de conexion ...");
				new atomicServer(cable);
				System.out.println("Tiempo : "
						+ (double) (System.currentTimeMillis() - t) + " ms");
			}
		} catch (Exception e) {
			System.err.println("Error en sockets ...");
		}

		System.out.println("Tiempo : "
				+ (double) (System.currentTimeMillis() - t) + " ms");
	}

}
