import java.util.concurrent.*;

/**
 * 
 * @author Pablo
 *	Programa que implementa el algoritmo de Eisenberg-McGuire
 * Este algoritmo es uno de los que controla la exclusion mutua de varios procesos
 *
 *	Si se quiere revisar en Eclipse, es necesario realizar lo siguiente:
 *
 *	Vamos a la pestaña Project, y luego hacemos click en Properties. 
 *	Nos saldra una ventana con diferentes propiedades del proyecto
 *	Solo nos importa una, la que pone "Java Compiler"
 *	En ella, debemos pulsar la unica caja que aparece arriba
 *	Luego, en compiler compliance level, lo debemos poner en 1.5
 */

public class algEisenbergMcGuire implements Runnable{

	private enum pstate  {OCIOSO, ESPERAR, ACTIVO};
	static volatile pstate[] flags;
	private static volatile int turno;
	private static final int iter = 200000;
	private static volatile  int enteroCompartido = 0;
	private int i;
	/**
	 * Constructor para crear objetos de tipo algEisenbergMcGuire
	 * @param i Identificador del hilo
	 */
	public algEisenbergMcGuire(int i)
	{
		this.i = i;
	}
	
	/**
	 * Hemos sobreescrito el metodo run de la implementacion Runnable
	 */
	public void run()
	{
		int indice;
		for( int j=0;j<iter;j++)
		{
			do{
					flags[i] = pstate.ESPERAR;
					indice = turno;
					while(indice!=i)
					{
						if(flags[indice]!=pstate.OCIOSO)
							indice = turno;
						else
							indice = (indice+1)%2;
					}
				
					flags[i] = pstate.ACTIVO;
				
					indice = 0;
					
					while((indice < 2) && ((indice == i) || (flags[indice] != pstate.ACTIVO)))
						indice++;
				
				}while((indice < 2) || ((turno != i) && (flags[turno] != pstate.OCIOSO)));
			
				//Inicio de la seccion critica
			
				turno = i;
			
				if(i==1)
					enteroCompartido++;
				else
					enteroCompartido--;
			
				//Fin de la seccion critica
			
				indice = (turno+1) % 2;
				while(flags[indice] == pstate.OCIOSO)
					indice = (indice+1) %2;
			
				turno = indice;
				flags[i] = pstate.OCIOSO;
			
		}
	}

		
	/**
	 * La linea de comandos no se usa, ya que estamos probando este algoritmo de exclusion mutua
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		flags = new pstate[2];
		algEisenbergMcGuire Eisen1 = new algEisenbergMcGuire(0);
		algEisenbergMcGuire Eisen2 = new algEisenbergMcGuire(1);
		
		ThreadPoolExecutor exe = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		
		exe.execute(Eisen1);
		exe.execute(Eisen2);
		exe.shutdown();
		
		try
		{
			exe.awaitTermination(10, TimeUnit.SECONDS);
			System.out.println("El valor del entero compartido es : "+enteroCompartido);
			System.out.println("Debe valer 0");
		}catch(InterruptedException e){System.err.println("ERROR");}
	}
}


