import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class drakkarVikingo implements Runnable {

	private int op;
	
	static monitorDrakkar m = new monitorDrakkar();
	/**
	 * Constructor de los objetos de tipo drakkarVikingo
	 * @param op
	 */
	public drakkarVikingo(int op)
	{
		this.op = op;
	}
	/**
	 * Metodo run() sobreescrito.
	 * Este metodo
	 */
	public void run() {

		switch(op)
		{
		case 0: //En el caso de los vikingos
			while(true)
			{
				m.comer();
				m.remar();
			}
		case 1:
			while(true) //En el caso del cocinero
			{
				m.llenarmarmita();
				m.rezar();
			}		
		}
	}
	
	/**
	 * MAIN : Este ejercicio es un tipo de productor-consumidor, en el cual el elemento producido nunca se gasta, por ello el programa NUNCA acaba, 
	 * y siempre estara en un bucle constante de consumir el recurso y produciendose.
	 * @param args
	 */
	public static void main(String[] args)
	{
		int vikingos = 0;
		Scanner S = new Scanner(System.in);
		do{
		System.out.println("Introduzca el numero de vikingos : ");
		vikingos = S.nextInt();
		}while(vikingos<=0);
		drakkarVikingo[] v  = new drakkarVikingo[vikingos];
		ExecutorService ex = Executors.newCachedThreadPool();
		ex.execute(new drakkarVikingo(1));
		for(int i=0;i<vikingos;i++)
		{
			v[i] = new drakkarVikingo(0);
			ex.execute(v[i]);
			
		}
		
		ex.shutdown();
		while(!ex.isTerminated()){}
		}
	}

