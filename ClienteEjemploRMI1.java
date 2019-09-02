/**Ejemplo de implementacion de un cliente para RMI
 * @author Antonio Tomeu
 */
import java.rmi.*;
import java.rmi.registry.*;

public class ClienteEjemploRMI1
{
	public static void main(String[] args)
	  throws Exception
	{
		int a =  10;
		int b = -10;
		
		//En esta ocasion trabajamos sin gestor de seguridad
		//System.setSecurityManager(new RMISecurityManager());
		
		//Se obtiene una referencia a la interfaz del objeto remoto
		//SIEMPRE debe convertirse el retorno del metodo Naming.lookup
		//a un objeto de interfaz remoto
		
		IEjemploRMI1 RefObRemoto = 
		  (IEjemploRMI1)Naming.lookup("//localhost/Servidor");
		  	
		 System.out.println(RefObRemoto.Suma(a,b));	
		 System.out.println(RefObRemoto.Resta(a,b));	
		 System.out.println(RefObRemoto.Producto(a,b));	
		 System.out.println(RefObRemoto.Cociente(a,b));	
		
	}	
}	