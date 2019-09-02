import java.util.Scanner;
import java.util.Random;
public class prodMatConcurrente extends Thread {
	
	private static int [][] A,B,Y;
	private static int fA,fB,cA,cB;
	private int fila;
	private static Scanner S = new Scanner(System.in);
	private static Random R = new Random(System.nanoTime());
	
	public prodMatConcurrente(int fila)
	{
		this.fila = fila;
		Y = new int[A.length][B[0].length];
	}
	
	
	public void run()
	{
		for(int j=0;j<B.length;j++)
		{
			for(int k=0;k<B[0].length;k++)
			{
				Y[fila][k]+=A[fila][j]*B[j][k];
			}	
		}
	}
	/**
	 * Metodo observador para la matriz A
	 * @return A
	 */
	public static int[][] getA(){return A;}
	
	/**
	 * Metodo modificador de la matriz A
	 * @param A
	 */
	
	public static void setA(int[][] A){prodMatConcurrente.A = A;}
	/**
	 * Metodo observador para la matriz B
	 * @return B
	 */
	public static int [][] getB(){return B;}
	
	/**
	 * Metodo modificador de la matriz B
	 * @param B
	 */
	
	public static void setB(int[][]B){prodMatConcurrente.B=B;}
	/**
	 * Metodo observador para la matriz Y
	 * @return Y
	 */
	public static int[][] getY(){return Y;}
	
	/**
	 * Metodo modificador de la matriz Y
	 * @param Y
	 */
	
	public static void setY(int[][]Y){prodMatConcurrente.Y = Y;}
	
	/**
	 * Metodo observador para el numero de filas de la matriz A
	 * @return fA
	 */
	public static int getfA(){return fA;}
	
	/**
	 * Metodo modificador del numero de filas de la matriz A
	 * @param fA
	 */
	
	public static void setfA(int fA){prodMatConcurrente.fA = fA;}
	
	/**
	 * Metodo observador para el numero de filas de la matriz B
	 * @return fB
	 */
	public static int getfB(){return fB;}
	
	/**
	 * Metodo modificador del numero de filas de la matriz B
	 * @param fB
	 */
	
	
	public static void setfB(int fB){prodMatConcurrente.fB = fB;}
	
	/**
	 * Metodo observador para el numero de columnas de la matriz A
	 * @return cA
	 */
	
	public static int getcA(){return cA;}
	
	/**
	 * Metodo modificador del numero de columnas de la matriz A
	 * @param cA
	 */
	
	
	public static void setcA(int cA){prodMatConcurrente.cA = cA;}
	
	/**
	 * Metodo observador para el numero de columnas de la matriz B
	 * @return cB
	 */
	
	public static int getcB(){return cB;}

	/**
	 * Metodo modificador del numero de columnas de la matriz B
	 * @param cB
	 */
	
	public static void setcB(int cB){prodMatConcurrente.cB = cB;}
		

	/**
	 * Llama al metodo intro, pasandole la matriz a rellenar
	 * @see intro(int[][] X)
	 */
	public static void introMan()
	{
		intro(A);
		intro(B);
	}

/**
 * Introduccion manual de los datos de la matriz
 * @param X
 */
	private static void intro(int[][] X) 
	{
		for(int i=0;i<X.length;i++)
		{
			for(int j=0;j<X[0].length;j++)
			{
				System.out.println();
				System.out.print("Introduzca el elemento de la posicion ["+i+","+j+"] : ");
				X[i][j] = S.nextInt();
			}
		}
	}

	/**
	 * Llama al metodo introAuto, pasandole la matriz a rellenar
	 * @see introAuto(int[][] X)
	 */
	public static void introAuto() 
	{
		introAuto(A);
		introAuto(B);
	}

/**
 * Introduccion automatica de la matriz. Se consigue s traves de numeros aleatorios
 * @param X
 */
	private static void introAuto(int[][] X) 
	{
		for(int i=0;i<X.length;i++)
		{
			for(int j=0;j<X[0].length;j++)
			{
				X[i][j] = R.nextInt(10);
			}
		}
	}

/*
	public static void res() 
	{
		for(int i=0;i<Y.length;i++)
		{
			for(int j=0;j<Y[0].length;j++)
			{
				System.out.print("\t");
				System.out.print(Y[i][j]);
			}
		System.out.println();
		}
	}
	
*/
	
}


