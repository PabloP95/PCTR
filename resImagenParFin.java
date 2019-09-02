/**
 * @author Pablo Piedad Garrido
 * @date 22-11-2017
 * 
 * Con este programa estamos realizando el resaltado de la imagen utilizando
 * grano fino, es decir, utilizamos tantos hilos como filas haya en la imagen.
 * Dichos hilos no seguiran un patrón constante
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import java.util.Scanner;
public class resImagenParFin implements Runnable {
	private static BufferedImage Imagen;
	private static int ancho;
	private static int alto;
	int i;
	/**
	 * Constructor
	 * @param i Numero de fila
	 */
	public resImagenParFin(int i)
	{
		this.i = i;
	}
	/**
	 * El metodo run() se ha sobreescrito. En este metodo se realiza la operacion
	 * de resaltado para el hilo/fila que llegue
	 */
	public void run()
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
	
	/**
	 * Pedimos al usuario que introduzca el nombre de la imagen a cargar.
	 * Dicha imagen debe encontrarse en el mismo fichero donde se encuentra el programa
	 * Se hace un vector de hilos(tantos como filas posea la imagen) y se realiza la operacion de resaltado
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner S = new Scanner(System.in);
		String inicio = "";
		String fin = "";
		 
		
		System.out.println("Introduzca el nombre de la imagen a tratar");
		inicio = S.nextLine();
		
		try {
			Imagen=ImageIO.read(new File(inicio));
		} catch (IOException e) {}
		
		alto = Imagen.getHeight();
		ancho = Imagen.getWidth();
		
		long inicTiempo = System.nanoTime();
		
		Thread [] h = new Thread[alto];
		for(int i=0;i<alto;i++)
		{
			h[i] = new Thread(new resImagenParFin(i));
		}
		
		for(int i=0;i<alto;i++)
		{
			h[i].start();
		}
		
		for(int i=0;i<alto;i++)
		{
			try {
				h[i].join();
			} catch (InterruptedException e) {}
		}
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
