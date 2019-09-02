/*Ejemplo de cliente de sockets
 *@Antonio Tomeu
 *@version 1.0
 */

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class clienteContador {
	public static void main(String[] args) {

		int puerto = 2001;
		int nP = Integer.parseInt(args[0]);
		for (int j = 0; j < nP; j++) {
			int i = (int) (Math.random() * 10);
			try {
				System.out.println("Realizando conexion...");
				Socket cable = new Socket("localhost", 2001);
				System.out.println("Realizada conexion a " + cable);
				PrintWriter salida = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(cable.getOutputStream())));
				salida.println(i);
				salida.flush();
				System.out.println("Cerrando conexion...");
				cable.close();

			}// try
			catch (Exception e) {
				System.out.println("Error en sockets...");
			}
		}

	}// main
}// clienteMultiple

