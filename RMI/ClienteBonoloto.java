	
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Random;

public ClienteBonoloto
{
	private Random r = new Random(System.nanoTime());
	
	public static void main(String[] args) throws Exception
	{
		int apuesta[] = new int[6];
		for(int i=0;i<apuesta.length;i++)
		{
			apuesta[i] = r.nextInt(49)+1;
		
		}
		
		iBonoloto RefObRemoto = (iBonoloto)Naming.lookup("//localhost/Sorteo");
		
		RefObRemoto.resetServidor();
		System.out.println("El servidor ya tiene sus 6 numeros");
		boolean ganador = RefObRemoto.compApuesta(apuesta);
		
		if(ganador)
			System.out.println("Ha ganado");
		else
			System.out.println("Ha perdido");
	}
		
		
		
