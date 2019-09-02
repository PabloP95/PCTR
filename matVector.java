/**
 * @author Pablo Puedad Garrido
 * Programa con el que se realiza el producto de una matriz por un vector de forma secuencial
 */

import java.util.Random;
import java.util.Scanner;


public class matVector {
	
	private static int [][] A;
	private static int []b,y;
	private static int f,c;
	private static Scanner S = new Scanner(System.in);
	private static Random R = new Random(System.nanoTime());
	/**
	 * MAIN. Aqui se puede encontrar la introduccion de los valores de filas y columnas y un menu
	 * @param args
	 */
	public static void main(String[] args)
	{
		int op;
		
		System.out.print("Introduzca el numero de filas : ");
		f = S.nextInt();
		System.out.print("\nIntroduzca el numero de columnas : ");
		c = S.nextInt();
		
		A = new int[f][c];
		b = new int[A[0].length];
		y = new int[A.length];
		
		
		System.out.println("Introduzca la opcion a realizar : ");
		System.out.println("1.- Producto de matriz por un vector, ambos introducidos manualmente");
		System.out.println("2.- Producto de matriz por un vector, ambos introducidos aleatoriamente");
		
		op = S.nextInt();
		long inicioT = System.nanoTime();
		switch(op)
		{
		case 1 : prod1();break;
		case 2 : prod2();break;
		default : System.err.println("Debe introducir una operacion valida");
		}
		long tTotal  = (System.nanoTime()-inicioT)/(long)1.0e6;
		System.out.println("Ha tardado "+tTotal+" milisegundos");
		S.close();
	}
	
	/**
	 * Este es el punto de partida para el producto de uma matriz y un vector introducidos por teclado.
	 *	Se le pasa el numero de filas y columnas para poder crear la matriz y el vector de dimensiones fxc y c respectivamente.
	 * @param f  = Número de filas
	 * @param c = Número de columnas
	 */
	private static void prod1()
	{
		iniciaMatriz();
		iniciaVector();
		prod();
		
	}

	/**
	 * Aqui se introduce cada elemento de la matriz. Como aun necesitamos el numero de filas y columnas los estamos pasando.
	 * @param f
	 * @param c
	 */
	private static void iniciaMatriz() 
	{
		System.out.println();
		for(int i=0;i<A.length;i++)
		{
			for(int j=0;j<A[0].length;j++)
			{
				System.out.print("Introduzca el elemento de la posicion : ["+i+" , "+j+"] : ");
				A[i][j] = S.nextInt();
			}
		}
	}
	
	/**
	 * Aqui introducimos los elementos del vector. Como el numero de "filas" del vector(es decir, el número de elementos) debe ser igual al numero de columnas de  la matriz, el vector tendra c elementos.
	 * Como ya hemos introducido el numero de columnas de la matriz, se pone A[0].length, lo cual es lo mismo que poner el numero de columnas de la matriz.
	 */
	
	private static void iniciaVector() 
	{
		for(int i=0;i<b.length;i++)
		{
			System.out.println();
			System.out.print("Introduzca el elemento de la posicion "+i+" : ");
			b[i] = S.nextInt();
		}
	}
	/**
	 * Aqui realizamos el producto de la matriz y el vector, previamente introducidos.
	 * El resultado será un vector con el mismo numero de elementos que el vector previamente introducido
	 * Los elementos del vector y la matriz pueden haber sido introducidos de manera aleatoria.
	 */
	private static void prod()
	{
		for(int i=0;i<A.length;i++)
		{
			y[i] = 0;
			for(int j=0;j<A[0].length;j++)
			{
				y[i]+=A[i][j]*b[j];
			}
		}
	}

	
	/**
	 * Punto de partida para realizar el producto de una matriz por un vector, ambos tendrán sus elementos introducidos de forma aleatoria
	 * @param f
	 * @param c
	 */
	private static void prod2()
	{
		introMatriz();
		introVector();
		prod();
		
	}
	/**
	 * Introducción de los elementos de la matriz de forma aleatoria.
	 * @param f
	 * @param c
	 */
	private static void introMatriz()
	{
		
		for(int i=0;i<A.length;i++)
		{
			for(int j=0;j<A[0].length;j++)
			{
				A[i][j] = R.nextInt(10);
			}
		}
	}
	/**
	 * Introduccion aleatoria de los elementos del vector.
	 */
	private static void introVector()
	{
		for(int i=0;i<b.length;i++)
		{
			b[i] = R.nextInt(10);
		}
	}
	

}
