
public class lectorEscritor {
	
	private int n=0;
	private boolean escribiendo = false;
	
	public synchronized void inicia_lectura()
	{
		while(escribiendo)
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		n++;
		notifyAll();
	}
	
	
	public synchronized void fin_lectura()
	{
		while(n!=0)
		n--;
			
		notifyAll();
	
	}
	public synchronized void inicio_escritura()
	{
		while(n!=0||escribiendo)
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		escribiendo=true;
	}
	
	public synchronized void fin_escritura()
	{
		escribiendo=false;
		notifyAll();
	}
}

