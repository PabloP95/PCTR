/**
 * @author Pablo Piedad Garrido
 * @date 22-11-2017
 * 
 * Este programa realiza el reasaltado de una imagen de forma secuencial
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;


public class resImagen {
/**
 * En el programa, el usuario introducirá mediante entrada estandar el nombre de la imagen
 * a cargar, la cual se encontrará en el mismo fichero que el programa.
 * @param args
 */
	public static void main(String[] args) {
		Scanner S = new Scanner(System.in);
		String fichero= "";		
		String salida = "";
		BufferedImage Imagen = null;
		int pix;
		System.out.println("Introduzca el fichero a cargar : ");
		fichero = S.nextLine();

		try {
				Imagen = ImageIO.read(new File(fichero));
		} catch (IOException e){};
		
		int alto = Imagen.getHeight();
		int ancho = Imagen.getWidth();
		
		long inicio = System.nanoTime();
		for(int i=0;i<alto;i++)
		{
			for(int j=0;j<ancho;j++)
			{
				pix = 4*Imagen.getRGB(i, j);
				
				if(i+1<alto) pix-=Imagen.getRGB(i+1, j);
				if(j+1<ancho) pix-=Imagen.getRGB(i, j+1);
				if(i-1>=0) pix-=Imagen.getRGB(i-1,j);
				if(j-1>=0) pix-=Imagen.getRGB(i, j-1);
				
				
				Imagen.setRGB(i, j, pix/8);
			}
		}
		long tiempoTotal = (System.nanoTime()-inicio)/(long)1.0e9;
		System.out.println("Introduzca el nombre del fichero creado : ");
		salida = S.nextLine();
		System.out.println("Ha tardado "+tiempoTotal+" segundos");
		File out = new File(salida);
		try {
			ImageIO.write(Imagen, "png", out);
		} catch (IOException e) {}		
	}
}
