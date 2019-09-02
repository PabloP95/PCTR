/**
 * @author Pablo Piedad Garrido
 * Esta es una implementación del programa que dio el profesor de esta asignatura
 * Dicho programa es clienteHilos.java
 */

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class clienteMultiple
{
	/**
	 * MAIN:
	 * En esta implementación utilizamos un bucle for para que hayan
	 * tantos sockets como peticiones se introduzcan por la linea de comandos
	 * @param args
	 */
    public static void main (String[] args)
    {

		int puerto = 2001;
		int n=0;
		int peticiones=Integer.parseInt(args[0]);
		for(int j=0;j<peticiones;j++)
		{    
			int i = (int)(Math.random()*10);
			try
			{
		      //  System.out.println("Realizandonexion...");
		        Socket cable = new Socket("localhost", 2001);
		        //System.out.println("Realizada conexion a "+cable);
		        PrintWriter salida= new PrintWriter(new BufferedWriter(new OutputStreamWriter(cable.getOutputStream())));
		        salida.println(i);
		        salida.flush();
		        //System.out.println("Cerrando conexion...");
		        cable.close();
			}//try
                catch (Exception e){System.out.println("Error en sockets...");}
                n++;
		}//while
    }//main
}//Cliente_Hilos

