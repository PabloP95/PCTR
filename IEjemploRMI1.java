/**Ejemplo del interfaz remoto para implementar un RMI
 * @author Antonio Tomeu
 *Es un servidor remoto aritmético.
 */
 
 
 //se importan las clases del paquete rmi 
 import java.rmi.*;
 
 //toda interface remota debe extender la clase Remote
 interface IEjemploRMI1 extends Remote
 {
 	//todo metodo de la interfaz remota debe declarar la excepcion 
 	//RemoteException
 	int Suma(int x, int y)throws RemoteException; 
 	int Resta(int x, int y)throws RemoteException; 
 	int Producto(int x, int y)throws RemoteException; 
 	float Cociente(int x, int y)throws RemoteException; 
 }	