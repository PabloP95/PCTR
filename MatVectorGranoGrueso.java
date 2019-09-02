/**
 * @author Pablo Piedad Garrido
 * Programa con el que se realiza el producto de una matriz por un vector.
 * Se realiza de forma paralela
 */

import java.util.Scanner;
import java.util.Random;

import java.util.concurrent.*;//Hace falta para usar ejecutores(es decir, pool de threads)

public class MatVectorGranoGrueso implements Runnable {

	private static Scanner S = new Scanner(System.in);
	private static Random R = new Random(System.nanoTime());
	private static int [][]A;
	private static int [] b,y;
	private static int f,c;
	private static int N = Runtime.getRuntime().availableProcessors();
	private int linf;
	private  int lsup;
/**
 * Constructor de objetos matVectorConcurrente
 * @param fila
 */
	public MatVectorGranoGrueso(int liminf,int limsup)
	{
		this.linf = liminf;
		this.lsup = limsup; 
	}

/**
 * Sobreescritura del metodo run() de la interfaz runnable
 * Aqui es donde se realiza el producto
 */

	public void run() 
	{
		for(int i=linf;i<lsup;i++)
		{
			for(int j=0;j<c;j++)
			{
				y[i] += A[i][j] * b[j];
			}
		}
		
		
	}
	
	
	public static void main(String[] args)
	{
		int op,inf,sup;
		
		System.out.print("Introduzca el numero de filas : ");
		f = S.nextInt();
		System.out.print("\nIntroduzca el numero de columnas : ");
		c = S.nextInt();
		
		
		int window = N/f;
		inf = 0;
		sup = window;
		
		A = new int[f][c];
		b = new int[A[0].length];
		y = new int[A.length];
		MatVectorGranoGrueso [] m = new MatVectorGranoGrueso[N]; //Al ser el coeficiente de bloqueo = 0, el número de threads = Número de hilos logicos disponibles
		System.out.println("Introduzca como quiere introducir los elementos de la matriz y el vector  : ");
		System.out.println("1.- Introduccion manual");
		System.out.println("2.- Introduccion aleatoria, y por tanto, automatica");
		
		op = S.nextInt();
		
		switch(op)
		{
		case 1 : prod1();break;
		case 2 : prod2();break;
		default : System.err.println("Debe introducir una operacion valida");
		}

		long inicTiempo = System.nanoTime();
		ExecutorService ex = Executors.newCachedThreadPool();
	
		
		for(int i=0;i<N;i++)
		{
			m[i] =new MatVectorGranoGrueso(inf,sup); //El contenido de cada hilo es el objeto creado por el constructor matVectorConcurrente(int fila)
			inf = sup;sup+=window;
			if(f-sup <= f)
				sup=f;
			ex.execute(m[i]);
		}
		ex.shutdown();
		
		while(!ex.isTerminated());

		long tiempoTotal = (System.nanoTime()-inicTiempo)/(long)1.0e9;
	    System.out.println("Operacion realizada en "+(tiempoTotal)+" segundos");
	
	}

	
	/**
	 * Punto de partida para la introduccion de matriz y vector de forma manual
	 */
	private static void prod1()
	{
		introMatriz();
		introVector();
	}
		
	private static void prod2() 
	{
		IntroMatAuto();
		introVecAuto();
	}

	
/**
 * Lo utilizamos para introducir el contenido de la matriz A manualmente
 */
	private static void introMatriz()
	{
		for(int i=0;i<f;i++)
		{
			for(int j=0;j<c;j++)
			{
				System.out.println();
				System.out.print("Introduzca el elemento de la posicion ["+i+","+j+"] : ");
				A[i][j] = S.nextInt();
			}
		}
	}
/**
* Lo utilizamos para introducir el contenido del vector b manualmente
*/
	private static void introVector()
	{
		for(int j=0;j<A.length;j++)
		{
			System.out.println();
			System.out.print("Introduzca el elemento de la posicion "+j+" : ");
			b[j] = S.nextInt();
		}	
	}

/**
 * Utilizado para introducir el contenido de la matriz A de forma aleatoria
 */
	private static void IntroMatAuto()
	{
		for(int i=0;i<f;i++)
		{
			for(int j=0;j<c;j++)
			{
				A[i][j] = R.nextInt(10);
			}
		}
	}
	
/**
 * Utilizado para introducir el contenido del vector b de forma aleatoria	
 */
	private static void introVecAuto() 
	{
		for(int j=0;j<A.length;j++)
		{
			b[j] = R.nextInt(10);
		}
	}
	
	
	
	
	/* 
	private static void res()
	{
		System.out.println("El resultado es el vector : ");
		for(int i=0;i<y.length;i++)
		{
			System.out.println(y[i]);
		}
	}
	*/
}
