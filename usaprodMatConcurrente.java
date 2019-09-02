/**
 * @author Pablo Piedad Garrido
 * Realiza el producto de dos matrices de forma concurrente
 */

import java.util.Scanner;
public class usaprodMatConcurrente {
	
	public static void main(String[] args)
	{
		Scanner S = new Scanner(System.in);
		int op,f,c;
		System.out.println("Introduzca los siguientes datos de la primera matriz : ");
		System.out.print("Numero de filas : ");
		f = S.nextInt();
		prodMatConcurrente.setfA(f);
		System.out.print("\nNumero de columnas : ");
		c = S.nextInt();
		prodMatConcurrente.setcA(c);
		
		
		System.out.println("Introduzca los siguientes datos de la segunda matriz : ");
		System.out.print("Numero de filas : ");
		f = S.nextInt();
		prodMatConcurrente.setfB(f);
		System.out.print("\nNumero de columnas : ");
		c = S.nextInt();
		prodMatConcurrente.setcB(c);
		
		
		if(prodMatConcurrente.getfB() != prodMatConcurrente.getcA() )
		{
			System.out.println("El numero de columnas de la primera matriz ha de coincidir con el numero de columnas de la segunda matriz\n\nImposible el producto de matrices");
			System.exit(0);
		}
		
		else
		{
			prodMatConcurrente.setA(new int[prodMatConcurrente.getfA()][prodMatConcurrente.getcA()]);
			prodMatConcurrente.setB(new int[prodMatConcurrente.getfB()][prodMatConcurrente.getcB()]);
		
			do
			{
				System.out.println("Escoja como desea realizar la introduccion de los valores de cada vector");
				System.out.println("1.-Introduccion manual");
				System.out.println("2.-Introduccion automatica");
				op  = S.nextInt();
			
				switch(op)
				{
				case 1: prodMatConcurrente.introMan();break;
				case 2: prodMatConcurrente.introAuto();break;
				default : System.out.println("ERROR\nIntroduzca una opcion valida");
				}
				
			S.close();
				
			}while(op<1||op>2);
			
			prodMatConcurrente[]h = new prodMatConcurrente[prodMatConcurrente.getfA()];
			
			for(int i=0;i<prodMatConcurrente.getfA();i++)
			{
				h[i] = new prodMatConcurrente(i);
			}
			
			long Tini = System.nanoTime();
			
			for(int i=0;i<prodMatConcurrente.getfA();i++)
				h[i].start();
			
			for(int i=0;i<prodMatConcurrente.getfA();i++)
					try{
						h[i].join();
					}catch(Exception e){System.err.println("ERROR");}

			
			long Tfin = (System.nanoTime() -Tini)/(long)1.0e9;
			
			//prodMatConcurrente.res();
			
			System.out.println("Solucion hallada en "+Tfin+" segundos");
			
			
			
			
		}
		
		
	}
}
