
/**
 * 
 * @author Pablo
 *	Puede dar lugar a procesos ansiosos(también conocidos como procvesos hambrientos)
 */


public class TryFour {

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
				{
					wantp= false;
					Thread.yield();
					wantp = true;
				}
					
				enteroCompartido++;
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
				wantq = true;
				while(wantp)
				{
					wantq = false;
					Thread.yield();
					wantq = true;
				}
					
				enteroCompartido--;
				wantq = false;
					
			}
		}
	}
	
	TryFour()
	{
		Thread p = new P();
		Thread q = new Q();
		p.start();q.start();
		try{
			p.join();q.join();
			System.out.println("El valor del entero compartido es "+enteroCompartido);
			System.out.println("Debe valer 0");
		}catch(InterruptedException e){}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		new TryFour();
		
	}

}
