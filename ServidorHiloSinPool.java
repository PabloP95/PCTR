
/**
 * @author Pablo Piedad Garrido
 * Este programa es una modificación del programa dado
 * por el profesor de la asignatura.
 * 
 * En este  no utilizamos pool de threads por ello, la implementación será peor
 * y el tiempo que tarde será mayor
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServidorHiloSinPool
 implements Runnable
{
    Socket enchufe;
    private int nombre;
    private static  long total=0;
    
    /**
     * Constructor para los objetos de la clase ServidorHiloSinPool
     * @param s
     * @param nombre
     */
    
    public ServidorHiloSinPool(Socket s,int nombre)
    {enchufe = s;this.nombre = nombre; }
    
    /**
     * Sobreescritura del metodo run() el cual se encuentra en la interfaz Runnable del API de Java
     */
    public void run()
    {  
    	
    	Date d = new Date(); //Necesario para que el tiempo para cada hilo sea distinto uno de otro
    	long t = System.currentTimeMillis();
    	d.setTime(t);
    try{
        BufferedReader entrada = new BufferedReader(
                                    new InputStreamReader(
                                        enchufe.getInputStream()));
        String datos = entrada.readLine();
        int j;
        int i = Integer.valueOf(datos).intValue();
        for(j=1; j<=20; j++)
        	System.out.println("El hilo "+nombre+" escribiendo el dato "+i);
        enchufe.close();
       
        System.out.println("El hilo "+nombre+" cierra su conexion...");
    
    } catch(Exception e) {System.out.println("Error...");}
    
    long fin = (System.currentTimeMillis() - t);
    d.setTime(fin);
    total(fin);
    
  }//run
    /**
     * Metodo utilizado para calcular el tiempo transcurrido en total
     * @param fin
     */
    private void total(long fin) {
    	total+=fin;
		System.out.println("Total = "+(total)+" ms");
		
	}

/**
 * MAIN : Aqui no utilizamos un pool de hilos, por lo que va a tardar más la ejecución del programa
 * @param args
 */
public static void main (String[] args)
{
    int i=0;
    int puerto = 2001;

        try{
            ServerSocket chuff = new ServerSocket (puerto, 3000);

            while (true){
                System.out.println(" Esperando solicitud de conexion...");
                Socket cable = chuff.accept();
                System.out.println(" Recibida solicitud de conexion...");
              
                new Thread(new ServidorHiloSinPool(cable,i)).start();
                new Thread(new ServidorHiloSinPool(cable,i)).join();
                i++;
                
        }//while
      } catch (Exception e)
        {System.out.println("Error en sockets...");}
}//main

}//ServidorHiloSinPool

