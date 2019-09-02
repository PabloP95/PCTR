/**
 * @author Pablo Piedad Garrido
 * Este programa implementa el algoritmo de la panadería de Lamport
 * Es uno de los algoritmos que controlan la exclusion mutua de procesos
 * 
 * En este caso hemos utilizado un pool de threads
 * 
 */

import java.util.concurrent.*;


public class algoLamport implements Runnable{
	
	private static volatile int[] turno; 
	private static volatile boolean[]  elegir;
	private static volatile int N;
	private static volatile int iter;
	private static volatile int enteroCompartido = 0;
	
	private int i;
	
	/**
	 * 
	 * @param i Identificador Hilo
	 */
	public algoLamport(int i)
	{
		this.i = i;
	}
	
	/**
	 * Hemos sobreescrito el metodo run de la implementacion Runnable
	 */
	
	public void run()
	{
		for(int k=0;k<iter;k++)
		{

			elegir[i] = true;
			turno[i] = 1 + max(turno);
			elegir[i] = false;
			for(int j=0;j<N;j++)
			{
				while(elegir[j]){}
				while((turno[j]!=0)&& ((turno[j]<turno[i]) || ((turno[j] == turno[i]) && j<i)));
				{}
			}	
			//SECCION CRITICA
			if(i%2==0)
				enteroCompartido++;
			else
				enteroCompartido--;
			//FIN SECCION CRITICA
			
			turno[i] = 0;
		}
	}
	/**
	 * Calcula el elemento maximo del vector turnos
	 * @param v
	 * @return maximo
	 */
	private int max(int v[])
	{
		int maximo = v[0];
		for(int i=0;i<v.length;i++)
		{
			if(v[i]>maximo)
				maximo = v[i];
		}
		
		return maximo;
	}
	
	
	
	/**
	 * No se introduce elementos por linea de comandos
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		ThreadPoolExecutor ex = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		N = Runtime.getRuntime().availableProcessors();
		
		System.out.println("El numero de procesos será igual al numero de procesadores que posea el ordenador");
		System.out.println("En este ordenador hay "+N+" procesos");
		
		iter = 200000;
		
		turno = new int[N];
		elegir = new boolean[N];
		
		
		algoLamport[] h = new algoLamport[N];
		for(int i=0;i<N;i++)
		{
			h[i] = new algoLamport(i);
			ex.execute(h[i]);
		}
		
		ex.shutdown();
		
		try
		{
			ex.awaitTermination(10, TimeUnit.SECONDS);
			System.out.println("El valor del entero compartido es : "+enteroCompartido);
			System.out.println("Debe ser 0");
		}catch(InterruptedException e){System.err.println("ERROR");}
		
	}

}
