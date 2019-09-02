
public class TryFirst {
	static final int iter = 2000000; //Se pone fina l para que no se pueda variar
	public static volatile int enteroCompartido=0; //Volatile = Variable volatil : El valor d ela variable es leido y escrito desde memoria principal
	//Con volatile se asegura que el valor de la variable, en este caso enteroCompartido, es siempre el más actualizado
	
	static volatile int turn = 1;
	
	class P extends Thread
	{
		public void run()
		{
			for(int i=0;i<iter;i++)
			{
				while(turn!=1)
					Thread.yield();//Deja paso y no se ejecuta la región crítica
				enteroCompartido++; //SECCION CRITICA
				
				turn = 2;
			}
		}
	}
	
	class Q extends Thread
	{
		public void run()
		{
			for(int i=0;i<iter;i++)
			{
				while(turn!=2)
					Thread.yield();//Deja paso y no se ejecuta la región crítica
				enteroCompartido--; //SECCION CRITICA
				
				turn = 1;
			}
		}
	}
	
	
	TryFirst()
	{
		Thread p = new P();
		Thread q = new Q();
		p.start();
		q.start();
		
		try
		{
			p.join();
			q.join();
			System.out.println("El valor del entero compartido debe ser 0");
			System.out.println("El valor del entero compartido es : "+enteroCompartido);
		}catch(InterruptedException e){}
	}
	
	
	public static void main(String[] args)
	{
		new TryFirst();
	}
	
}
