


import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.Random;

public class BonolotoServidor extends UnicastRemoteObject implements iBonoloto
{
	private static int v[] = new int[6];
	private Random r = new Random(System.nanoTime());
	public void resetServidor() throws RemoteException
	{
		for(int i=0;i<v.length;i++)
		{
			v[i] = r.nextInt(49)+1;
		}
	}
	
	
	public boolean compApuesta(int [] apuesta) throws RemoteException
	{
		boolean ganador = true;
		
		for(int i=0;i<apuesta.length && v[i]!=apuesta[i];i++)
			 if(v[i]!=apuesta[i])
				 ganador=false;
			 
		return ganador;
	
	}
	
	
	
	public BonolotoServidor() throws RemoteException
	{
	}
	
	
	public static void main(String[] args) throws Exception
	{
		iBonoloto ORemoto = new iBonoloto();
		
		Naming.bind("Sorteo",ORemoto);
		
		System.out.println("Servidor Remoto Preparado");
	}
}
