
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class usaLectorEscritor  implements Runnable{
	private static RandomAccessFile r;
	lectorEscritor l = new lectorEscritor();
	
	private static int i = 0;
	private static String fich;

	private int id;
	
	public usaLectorEscritor(int id)
	{
		this.id = id;
	}
	
	public void run()
	{
		switch(id)
		{
		case 0:
			l.inicia_lectura();
			try {
					System.out.println(r.readLine());
			} catch (IOException e) {}
			
			l.fin_lectura();
			break;
		case 1:
			l.inicio_escritura();
			try
			{
				System.out.println("Escribiendo : ");
				r.seek(r.length());
				r.writeBytes("Se ha escrito "+i+" veces\r\n");
				i++;
			}catch(IOException e){}
			
			l.fin_escritura();
			break;
		}
	}
	
	
	public static void main(String[] args)
	{
		Scanner Str = new Scanner(System.in);
		Scanner num = new Scanner(System.in);
		int personas=0;
		System.out.println("Introduzca el nombre del fichero a crear, junto con la extension .txt");
		fich = Str.nextLine();
		
		try {
			r = new RandomAccessFile(fich,"rw");
		} catch (FileNotFoundException e) {}
		do{
			System.out.println("Introduzca el numero de personas. \nEste debe ser par");
			personas = num.nextInt();
		}while(personas%2!=0);
		

		
		usaLectorEscritor [] u = new usaLectorEscritor[personas];
		
		ExecutorService ex = Executors.newCachedThreadPool();
		

		for(int i=0;i<personas;i++)
		{
			u[i] = new usaLectorEscritor(i%2);
			ex.execute(u[i]);
		}
		
		ex.shutdown();
	
		while(!ex.isTerminated());
	
	}
	
	
	
	
	
	
}
