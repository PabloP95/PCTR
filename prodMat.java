/**
 * @author Pablo Piedad Garrido
 * Este programa realiza el producto de dos matrices.
 * Para que se pueda realizar dicho producto, el numero de columnas de la primera matriz ha de coincidir con el numero de filas de la segunda matriz
 */
import java.util.Scanner;
import java.util.Random;
public class prodMat {
	private static int [][] A,B,Y;
	private static int fA,fB,cA,cB,m;
	private static Scanner S = new Scanner(System.in);
	private static Random R = new Random(System.nanoTime());
	
	public static void main(String[] args)
	{	
		int op;
		
		System.out.print("Introduzca el numero de filas de la primera matriz : ");
		fA = S.nextInt();
		System.out.print("\nIntroduzca el numero de columnas de la primera matriz : ");
		cA = S.nextInt();
		
		System.out.print("\nIntroduzca el numero de filas de la segunda matriz : ");
		fB = S.nextInt();
		System.out.print("\nIntroduzca el numero de columnas de la segunda matriz : ");
		cB = S.nextInt();
		
		if(cA!=fB)
		{
			System.err.println("El numero de filas de la segunda matriz ha de coincidir con el numero de columnas de la primera matriz para que se pueda realizar el producto de matrices");
			System.exit(0);
		}
		else
		{
			A = new int[fA][cA];
			B = new int[cA][cB];
			Y = new int[fA][cB];
			System.out.println("Introduzca como quiere introducir los elementos de ambas matrices");
			System.out.println("1.- Manualmente");
			System.out.println("2.- Automaticamente");
			op = S.nextInt();
			long Tinicio = System.nanoTime();
			
			switch(op)
			{
			case 1: prodMan();break;
			case 2: prodAuto();break;
			}
			
			//res();
			long Tfin = (System.nanoTime()-Tinicio)/(long)1.0e9;
			System.out.println("Se ha realizado en "+Tfin+" segundos");
		}
	}
	
	/**
	 * Su funcion es realizar la llamada a los demas metodos de prodMan
	 */
	private static void prodMan()
	{
		intro(A);
		intro(B);
		prod();
	}
	
	/**
	 * Funcion : Realizar la llamada a los demas metodos de prodAuto
	 */
	private static void prodAuto()
	{
		intrornd(A);
		intrornd(B);
		prod();
	}
	
	/**
	 * @param X = Matriz de enteros
	 * Funcion que introduce de forma manual los elementos de la matriz que se haya pasado.
	 * Matriz 0 = Matriz A . Matriz 1 = Matriz B
	 */
	
	private static void intro(int X[][])
	{
		m = 0;
		for(int i=0;i<X.length;i++)
		{
			for(int j=0;j<X[0].length;j++)
			{
				System.out.println();
				System.out.print("Introduzca el elemento de la posicion ["+i+","+j+"] de la matriz "+m+" : ");
				X[i][j] = S.nextInt();
			}
		}
		m++;
	}
	
	/**
	 * 
	 * @param X
	 * 
	 * Funcion : Introducir automaticamente los elementos de la matriz que se le haya pasado
	 */
	private static void intrornd(int X[][])
	{
		for(int i=0;i<X.length;i++)
		{
			for(int j=0;j<X[0].length;j++)
			{
				X[i][j] = R.nextInt(10);
			}
		}
	}
	
	/**
	 * Funcion : Realiza el producto de dos matrices y guarda el resultado en otra matriz de dimensiones: Filas = Las mismas que A. Columnas = Las mismas que B
	 */
	
	private static void prod()
	{
		for(int i=0;i<A.length;i++)
		{
			for(int j=0;j<B.length;j++)
			{
				for(int k=0;k<B[0].length;k++)
				{
					Y[i][k]+=A[i][j]*B[j][k];
				}
			}
		}
	}
/*	
	/**
	 * Imprime por pantalla la matriz resultado
	 */
/*
	private static void res()
	{
		System.out.println("El resultado es la matriz : ");
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
