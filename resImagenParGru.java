/**
 * @author Pablo Piedad Garrido
 * @date 22/11/2017
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.util.Scanner;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public class resImagenParGru implements Runnable {
	private static BufferedImage Imagen;
	private static int ancho;
	private static int alto;
	private int inf,sup;
	/**
	 * Constructor
	 * @param inf,sup
	 */
	public resImagenParGru(int inf,int sup)
	{
		this.inf = inf;
		this.sup = sup;
	}
	/**
	 * Este metodo se ha sobreescrito, ya que actualmente se encuentra en la interfaz Runnable
	 * Con run() estamos realizando la operación de resaltado para un numero de hilos Imagen.ancho/N
	 */
	public void run()
	{
		for(int i=inf;i<sup;i++)
		{
			for(int j=0;j<ancho;j++)
			{
				int pix = 4*Imagen.getRGB(i,j);
				if(i+1<alto) pix-=Imagen.getRGB(i+1,j);
				if(i-1>=0) pix-=Imagen.getRGB(i-1,j);
				if(j+1<ancho) pix-=Imagen.getRGB(i,j+1);
				if(j-1>=0) pix -=Imagen.getRGB(i,j-1);
			
				Imagen.setRGB(i,j,pix/8);
			}
		}	
	}
	
	/**
	 * MAIN:
	 * Se carga una imagen, y se resalta gracias a la ayuda de los pools de threads
	 * Dichos pools de threads son variables
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner S = new Scanner(System.in);
		String inicio = "";
		String fin = "";
		int N= Runtime.getRuntime().availableProcessors();
		int inf,sup;
		
	
		
		System.out.println("Introduzca el nombre de la imagen a tratar");
		inicio = S.nextLine();
		
		try {
			Imagen=ImageIO.read(new File(inicio));
		} catch (IOException e) {}
		
		alto = Imagen.getHeight();
		ancho = Imagen.getWidth();
		
		int ventana = alto/N;
		
		inf =0;
		sup = ventana;
		resImagenParGru[] v = new resImagenParGru[N];
		
		long inicTiempo = System.nanoTime();
		ExecutorService ex = Executors.newCachedThreadPool();
	
		for(int i=0;i<N;i++)
		{
			v[i] = new resImagenParGru(inf,sup);
			inf = sup; sup+=ventana;
			if(alto-sup<=alto)
				sup=alto;
			
			ex.execute(v[i]);
		}
		
	ex.shutdown();
	while(!ex.isTerminated()){};
	long tiempoTotal = (System.nanoTime()-inicTiempo)/(long)1.0e9;
	
	System.out.println("Introduzca el nombre del archivo nuevo : ");
	fin = S.nextLine();
	System.out.println("Ha tardado "+tiempoTotal+" segundos");
	File Salida = new File(fin);
	
	try{
		ImageIO.write(Imagen, "png", Salida);
	}catch(IOException e){}
	
	}
}

