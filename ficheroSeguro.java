
/**
 * @author Pablo Piedad Garrido
 * Programa que crea un fichero en el cual se escriben tantas veces
 * como hilos haya introducido Hilo nºHilo
 */

import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ficheroSeguro implements Runnable{
	
	private static RandomAccessFile r;
	private Object candado = new Object();
	
	private static String fich;
	private int identi;
	/**
	 * Constructor de los objetos de la clase ficheroSeguro
	 * @param identi
	 */
	public ficheroSeguro(int identi)
	{
		this.identi = identi;
	}
	/**
	 * Sobreescribimos el metodo run(), que se encuentra en la interfaz Runnable
	 * Aqui utilizamos bloques sincronizados para escribir en el fichero 
	 * Hilo nºHilo.
	 * Utilizamos bloques sincronizados para que así no se imprima 
	 * más de una
	 * vez el mismo hilo.
	 * 
	 */
	public void run() 
	{
		try 
		{
			synchronized(candado)
			{
					System.out.println("Se esta imprimiendo el hilo "+identi);
					r.seek(r.length());
					r.writeBytes("Hilo "+identi+"\r\n");
			}
		} catch (Exception e){}
	}
	/**
	 * MAIN:
	 * En este se introduce, mediante la entrada estandar, el 
	 * nombre del fichero a crear
	 * para luego que el programa escriba en él.
	 * También se introduce mediante entrada estandar el número de 
	 * hilos a lanzar.
	 * 
	 * No se introduce ningún dato por linea de comandos
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		Scanner Str = new Scanner (System.in);
		System.out.println("Introduzca el nombre del fichero que va a crear seguido de .txt");
		fich = Str.nextLine();
		r = new RandomAccessFile(fich,"rw");
		
		System.out.println("Introduzca el numero de hilos a lanzar : ");
		Scanner S = new Scanner (System.in);
		int i = S.nextInt();
		
		ExecutorService ex = Executors.newCachedThreadPool();
		
		for(int j=0;j<i;j++)
		{
			ex.execute(new ficheroSeguro(j));
		}
		ex.shutdown();
		try {
			ex.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {e.printStackTrace();}
		
		
		Str.close();
		S.close();
	}
}
	

