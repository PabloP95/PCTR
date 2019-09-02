
/* Dekker's algorithm */
class Dekker {
    /* Number of processes currently in critical section */
    static volatile int inCS = 0;
    /* Process p wants to enter critical section */
    static volatile boolean wantp = false;
    /* Process q wants to enter critical section */    
    static volatile boolean wantq = false;
    /* Process r wants to enter critical section */    
    static volatile boolean wantr = false;
    /* Which processes turn is it */
    static volatile int turn = 1;

    class P extends Thread {
        public void run() {
            while (true) {
                /* Non-critical section */
                wantp = true;
                while (wantq||wantr) {
                    if (turn == 2||turn == 3) {
                        wantp = false;
                        while (turn == 2||turn == 3)
                            Thread.yield();
                        wantp = true;
                    }
                }
                inCS++;
                Thread.yield();
                /* Critical section */
                System.out.println("Numero de procesos en region critica: "
                        + inCS);
                inCS--;
                turn = 2;
                wantp = false;
            }
        }
    }
    
    class Q extends Thread {
        public void run() {
            while (true) {
                /* Non-critical section */
                wantq = true;
                while (wantp||wantr) {
                    if (turn == 1||turn == 3) {
                        wantq = false;
                        while (turn == 1||turn == 3)
                            Thread.yield();
                        wantq = true;
                    }
                }
                inCS++;
                Thread.yield();
                /* Critical section */
                System.out.println("Numero de procesos en region critica: "
                        + inCS);
                inCS--;
                turn = 3;
                wantq = false;
            }
        }
    }
    
    class R extends Thread
    {
    	public void run()
    	{
    		wantr = true;
    			 while (wantp||wantq) 
    			 {
                     if (turn == 1||turn == 2)
                     {
                         wantr = false;
                         while (turn == 1||turn == 2)
                             Thread.yield();
                         wantr = true;
                     }
                 }
                 inCS++;
                 Thread.yield();
                 /* Critical section */
                 System.out.println("Numero de procesos en region critica: "
                         + inCS);
                 inCS--;
                 turn = 1;
                 wantr = false;
             }
         }
    
    

    Dekker() 
    {
        Thread p = new P();
        Thread q = new Q();
        Thread r = new R();
        p.start();
        q.start();
        r.start();
    }

    public static void main(String[] args) {
        new Dekker();
    }
}