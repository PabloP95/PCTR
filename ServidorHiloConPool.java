
/**
 * @author Pablo Piedad Garrido
 * Este programa es una modificación del programa dado
 * por el profesor de la asignatura.
 * 
 * En este utilizamos pool de threads para que la implementación
 * sea mejor
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
public class ServidorHiloConPool implements Runnable
{
    Socket enchufe;
    private int id;
    public static  long total=0;
    
    /**
     * Constructor de objetos de la clase ServidorHiloConPool
     * @param s
     * @param id
     */
    
    public ServidorHiloConPool(Socket s,int id)
    {
        enchufe = s;
        this.id=id;
    }
    /**
     * Sobreescritura del metodo run, el cual se encuentra en la interfaz Runnable
     */
    public void run()
    {
    	
    Date d = new Date();
    long t = System.currentTimeMillis(); 
    d.setTime(t);
    try{
        BufferedReader entrada = new BufferedReader(new InputStreamReader(enchufe.getInputStream()));
        
        String datos = entrada.readLine();
        int j;
        int i = Integer.valueOf(datos).intValue();
        for(j=1; j<=20; j++){
        System.out.println("El hilo "+id+" escribiendo el dato "+i);
        }
        enchufe.close();
        System.out.println("El hilo "+id+" cierra su conexion...");
    } catch(Exception e) {System.out.println("Error...");}
    long fin = System.currentTimeMillis() - t ; //se para el cronometro
    d.setTime(fin);
    total(fin);
    }//run
/**
 * Este metodo es el encargado de calcular el tiempo que dura en total la ejecucion del bloque run para los n procesos del pool
 * @param fin
 */
private void total(long fin) {
		System.out.println("Total = "+(total)+" ms");
		total+=fin;
	}


/**
 * MAIN : Se utiliza un Pool de Hilos para realizar el programa
 * @param args
 */

public static void main (String[] args)
{
    int i=0;
    int puerto = 2001;
    ExecutorService exe=(ThreadPoolExecutor)Executors.newCachedThreadPool();
        try{
            ServerSocket chuff = new ServerSocket (puerto, 3000);

            while (true){
                System.out.println("Esperando solicitud de conexion...");
                Socket cable = chuff.accept();

                System.out.println("Recibida solicitud de conexion...");
                exe.execute(new ServidorHiloConPool(cable,i));
                i++;
            }//while
      } catch (Exception e)
        {System.out.println("Error en sockets...");}
}//main

}//ServidorHiloConPool

