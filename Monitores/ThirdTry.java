
/**
 * 
 * @author Pablo
 * Comentarios :
 * 
 * 	Como podemos ver al ejecutarlo, se produce un interbloqueo muy feo
 * 	Por ello, se ejecuta el P un numero x de veces, pero se queda to loco
 * 	Esto puede ocurrir por lo siguiente:
 * 	
 * 	Como cada una de las clases comprueba que las condiciones del bucle contrario
 * 	son falsas permanece en el bucle, por lo que se produce interbloqueo, lo cual no deberia ocurrir en un algoritmo bueno de exclusion mutua
 */



public class ThirdTry {

	static final int iter = 200000; 
	static volatile int enteroCompartido = 0;
	static volatile boolean wantp = false;
	static volatile boolean wantq = false;
	
	class P extends Thread
	{
		public void run()
		{
			for(int i=0;i<iter;i++)
			{
				wantp = true;
				while(wantq)
					Thread.yield();
				enteroCompartido++;System.out.println("P");	System.out.println(i);
				wantp = false;
			}
		}
	}
	
	class Q extends Thread
	{
		public void run()
		{
			for(int i=0;i<iter;i++)
			{
				try{Thread.sleep(10);}catch(Exception e){}
				wantq = true;
				while(wantp)
					Thread.yield();
				enteroCompartido--; System.out.println("Q");
				wantq = false;
			}
		}
	}
	
	
	ThirdTry()
	{
		Thread p = new P();
		Thread q = new Q();
		
		p.start();
		q.start();
		
		try
		{
			p.join();
			q.join();
			System.out.println("El valor de la variable compartida es : "+enteroCompartido);
			System.out.println("El valor de la variable compartida debe ser 0");
		}catch(InterruptedException e){}
 	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		new ThirdTry();
		
	}

}
