import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class usaConductores implements Runnable {

	private Conductores c = new Conductores();
	private int op;
	private static Object candado = new Object();

	Scanner Str = new Scanner(System.in);
	Scanner num = new Scanner(System.in);
	public usaConductores(int op) {
		this.op = op;
	}

	public void run() {
		synchronized(candado)
		{
			switch (op) {
			case 0:
				Conductor C = new Conductor("","","","", 0);
				
				System.out.println("Introduzca el nombre del nuevo conductor : ");
				String nombre = Str.nextLine();
				C.setNombre(nombre);
				
				System.out.println("Introduzca el DNI del nuevo conductor : ");
				String DNI = Str.nextLine();
				C.setDNI(DNI);
				
				System.out.println("Introduzca el tipo de permiso de conducir : ");
				String tipo = Str.nextLine();
				C.setTipo(tipo);
				
				System.out.println("Introduzca el domicilio del nuevo conductor : ");
				String direccion = Str.nextLine();
				C.setDireccion(direccion);
				
				System.out.println("Introduzca la edad del conductor : ");
				int edad = num.nextInt();
				C.setEdad(edad);
				c.añadir(C);break;
			case 1:
				System.out.println("Introduzca el DNI del conductor que desea borrar");
				String D = Str.nextLine();
				c.borrar(D);
				break;
			case 2:
				System.out.println("Introduzca el DNI del conductor para saber toda su informacion");
				String DN = Str.nextLine();
				c.mostrar(DN);
				break;
				
			case 3:
				int p=0;
				System.out.println("Introduzca el DNI del conductor multado : ");
				String DNI1 = Str.nextLine();
				do{
					System.out.println("Introduzca el numero de puntos que se le han quitado al conductor multado : ");
					p = num.nextInt();
				}while(p>12);
				
				c.puntos(DNI1,p);
			}
		}
	}

	

	public static void main(String[] args) {
		
		ExecutorService ex = Executors.newCachedThreadPool();

			System.out.println("Bienvenido a la base de datos de la DGT");

			System.out.println("Opciones a realizar : ");
			System.out.println("1.- Insertar un nuevo conductor");
			System.out.println("2.- Eliminar un conductor ya introducido");
			System.out.println("3.- Mostrar la informacion del conductor");
			System.out.println("En caso de multa\n4.- Disminuir puntos del conductor");
		

			
			for(int i=0;i<4;i++)
			{
				ex.execute(new usaConductores(i) );
			}
		
		ex.shutdown();
		while(!ex.isTerminated()){}
		System.out.println("Fin del programa");
		}
	}
