
public class monitorDrakkar {
	private static  int marmita = 0; //Tiene que ser estatico ya que varia para cada echa y mete comida
	public monitorDrakkar()
	{
		marmita = 6;
	}
	
	
	public synchronized void comer()
	{
		while(marmita==0)
		{
			notifyAll(); //<-Tiene que mandarle al cocinero la señal para que empieza a cocinar
			try{
				wait();	
			}catch(Exception e){}
			
		}
		marmita--;
		System.out.println("Ahora la marmita tiene contenido para " +marmita+ " vikingos");
	}
	
	public void remar()
	{
		System.out.println("El vikingo esta remando");
	}
	
	
	public void rezar()
	{
		System.out.println("Como la marmita no esta vacia, el cocinero se pone a cargarse furias");
	}
	
	public synchronized void llenarmarmita()
	{
		while(marmita>0)
		{
			try
			{
				wait();
			}catch(Exception e){}
		}
		marmita=6;
		System.out.println("La marmita esta llena");
		notifyAll();//<-despierta a los vikingos
	
	}
}

