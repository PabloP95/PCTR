

/**
 * @author Pablo
 *Este intento no cumple la condicion de exlusion mutua ya que no es segura
 *Es decir, se produce intercalamiento de variables. Es decir, que se producre entrelazado
 *Comom se puede comprobar, hay fallos ya que puede ocurrir que ambos hilos pasen a sus regiones criticas
 */
public class SecondTry {
	
	static final int iter = 2000000; //Se pone fina l para que no se pueda variar
	public static volatile int enteroCompartido=0; //Volatile = Variable volatil : El valor d ela variable es leido y escrito desde memoria principal
	//Con volatile se asegura que el valor de la variable, en este caso enteroCompartido, es siempre el más actualizado
	
	static volatile boolean wantp= false;
	static volatile boolean wantq = false;
	
	class P extends Thread
	{
		public void run()
		{
			for(int i=0;i<iter;i++)
			{
				while(wantq)
					Thread.yield();//Deja paso y no se ejecuta la región crítica
				
				wantp = true;
				enteroCompartido++; //SECCION CRITICA
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
				while(wantp)
					Thread.yield();//Deja paso y no se ejecuta la región crítica
				wantq = true;
				enteroCompartido--; //SECCION CRITICA
				wantq = false;
			}
		}
	}
	
	
	SecondTry()
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
		new SecondTry();
	}
	
}




/*
 public void run() {
for(int k = 0 ; k < iter ; k ++) {
eligiendo[i] = true;
numero[i] = 1 + max(numero);
eligiendo[i] = false;
for(int j = 0 ; j < n_proc ; j ++) {
while(eligiendo[j]) {}
while((numero[j] != 0) && ((numero[j] < numero[i]) || ((numero[j] == numero[i]) && j < i)))
{}
}
if(i % 2 == 0)
variable ++;
else
variable --;
numero[i] = 0;
}
}
*/
