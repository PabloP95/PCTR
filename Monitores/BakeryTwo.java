import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
* @author Fernando Pacheco Ibáñez.
* @version 1.0
*/
public class BakeryTwo implements Runnable {
static volatile int[] turno; //turno
static volatile boolean[] elegir; //elegir
static volatile int N;
static volatile int iter;
static volatile int enteroCompartido;
private int i;
/**
* @param i Identificador del hilo
*/
public BakeryTwo(int i) {
this.i = i;
}
public void run() {
for(int k = 0 ; k < iter ; k ++) {
elegir[i] = true;
turno[i] = 1 + max(turno);
elegir[i] = false;
for(int j = 0 ; j < N ; j ++) {
while(elegir[j]) {}
while((turno[j] != 0) && ((turno[j] < turno[i]) || ((turno[j] == turno[i]) && j < i)))
{}
}
if(i % 2 == 0)
enteroCompartido ++;
else
enteroCompartido --;
turno[i] = 0;
}
}
/**
* @param v Vector de enteros
* @return Máximo del vector
*/
private int max(int[] v) {
int max = v[0];
for(int i = 0 ; i < v.length ; i ++) {
if(v[i] > max)
max = v[i];
}
return max;
}
/**
* @param args No se utilizan parámetros por línea de comandos
*/
public static void main(String[] args) {
ThreadPoolExecutor pool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
N = Runtime.getRuntime().availableProcessors();
iter = 50000;
turno = new int[N];
elegir = new boolean[N];
BakeryTwo[] hilos = new BakeryTwo[N];
for(int i = 0 ; i < N ; i ++) {
hilos[i] = new BakeryTwo(i);
pool.execute(hilos[i]);
}
pool.shutdown();
try {
pool.awaitTermination(10, TimeUnit.SECONDS);
System.out.println("Valor de la variable compartida: " + enteroCompartido);
if(N % 2 == 0)
System.out.println("Debería ser 0.");
else
System.out.println("Debería ser 5000.");
} catch(InterruptedException e) {}
}
}
