import java.util.ArrayList;
import java.util.Scanner;

public class Conductores {

	private static ArrayList<Conductor> cond = new ArrayList<Conductor>();
	/**
	 * Con este método se introducen en el ArrayList tres objetos de la clase Conductor.
	 * Lo ponemos estatico, asi podemos introducir elementos en el arraylist, y se refleja aqui.
	 */
	public synchronized static void elemenprin()
	{
		cond.add(new Conductor("Pablo Piedad Garrido", "49565969A", "B",
				"Avda. Reyes Catolicos", 22));
		cond.add(new Conductor("Pepe Lopez Mora", "24563987L", "A2",
				"Calle Calatrava 20", 18));
		cond.add(new Conductor("Alejandro Romero Navarrete", "85696325Q", "B",
				"Calle Ventormenta 25", 20));
	}
	/**
	 * Método responsable de añadir un objeto de clase Conductor.
	 * Aqui se introducen los datos del conductor, tales como el nombre, o el tipo de permiso de conducir
	 */
	public synchronized void añadir(Conductor C) {
		elemenprin();
		cond.add(C);
		System.out.println("Se ha introducido un nuevo conductor");
	}
/**
 * Con este metodo, localizamos al conductor, si es que existe, gracias al DNI introducido previamente en usaConductores
 * Si lo encuentra, lo elimina, y si no lo encuentra no hará absolutamente nada
 * @param DNI
 */
	synchronized void borrar(String DNI) {
		elemenprin();
		for (int i = 0; i < cond.size(); i++) {
			if (cond.get(i).getDNI().equals(DNI)) {
				System.out.println("El conductor con DNI " + DNI
						+ " se ha borrado");
				cond.remove(i);
			}
		}
	}
/**
 * Con este método podemos localizar al conductor con el mismo DNI que aquel que se ha introducido previamente en la clase usaConductores
 * y mostrar todos los datos de dicho conductor.
 * @param DNI
 */
	synchronized void mostrar(String DNI) {
		elemenprin();
		System.out.println("El conductor con DNI " + DNI
				+ " tiene los siguientes datos : ");
		for (int i = 0; i < cond.size(); i++) {
			if (cond.get(i).getDNI().equals(DNI)) {
				System.out.println(cond.get(i).toString());
			}
		}
	}

	/**
	 * Con este metodo podemos restarle p  puntos al carnet de conducir del conductor con el DNI introducido previamente en la clase usaConductores
	 * @param DNI
	 * @param p
	 */
	synchronized void puntos(String DNI, int p) 
	{
		elemenprin();
		int m;
		for (int i = 0; i < cond.size(); i++) 
		{
			if (cond.get(i).getDNI().equals(DNI))
			{
				m = cond.get(i).getPuntos() - p;
				System.out.println("Al conductor " + cond.get(i).getNombre()
						+ " le han puesto una multa");
				System.out.println("Antes tenia : " + cond.get(i).getPuntos()
						+ " puntos");
				cond.get(i).setPuntos(m);
				System.out.println("Ahora tiene " + m + " puntos");
			}
		}
	}
	
}


