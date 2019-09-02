/**Ejemplo de implementacion del interfaz remoto para  un RMI
 * @author Antonio Tomeu
 */
 
 //se importan los paquetes necesarios
 import java.rmi.*;
 import java.rmi.server.*;
 import java.rmi.registry.*;
 import java.net.*;
 
 public class EjemploRMI1
   extends UnicastRemoteObject //el servidor debe siempre extender esta clase
     implements IEjemploRMI1   //el servidor debe simpre implementar la interfaz
                               //remota definida con caracter previo
 {
 	public int Suma(int x, int y)
 	  throws RemoteException
 	{return x+y;}  
 	
 	public int Resta(int x, int y)
 	  throws RemoteException
 	{return x-y;}
 	
 	public int Producto(int x, int y)
 	  throws RemoteException
 	{return x*y;}
 	
 	public float Cociente(int x, int y)
 	  throws RemoteException
 	{ if(y == 0) return 1f;
 	  else return x/y; 
 	}
 	
 	//es necesario que haya un constructor (nulo) como minimo, ya que debe 
 	//lanzar RemoteException
 	public EjemploRMI1()
 	  throws RemoteException
 	{//super(); invoca automaticamente al constructor de la superclase
 	}  
 	
 	//el metodo main siguiente realiza el registro del servicio
 	
 	public static void main(String[] args)
 	  throws Exception 
 	{
 		//crea e instala un gestor de seguridad que soporte RMI.
 		//el usado es distribuido con JDK. Otros son posibles.
 		//En esta ocasion trabajamos sin el.
 	    //System.setSecurityManager(
 	    //new RMISecurityManager());
 	    
 		//Se crea el objeto remoto. Podriamos crear mas si interesa.
 		IEjemploRMI1 ORemoto = new EjemploRMI1(); //LOCAL
 		
 		//Se registra el servicio
 		Naming.bind("Servidor", ORemoto);//OREmoto ahora tiene mas informacion, por ello es distinto
 		  
 		System.out.println("Servidor Remoto Preparado");
 	}	  
 	
 	
 }	                              
                               
