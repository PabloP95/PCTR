#include <iostream>
#include <thread>
#include <ctime>
#include <cstdlib>
#include <chrono>
#include "pcMonitor.cpp"

using namespace std;
static pcMonitor p(100);
static int n;
void productor()
{
    for(int i=0;i<n;i++)
    {
        p.insertar(i);
    }

}

void consumidor()
{
    for(int i=0;i<n;i++)
    {
        p.eliminar();
    }

}


int main()
{
     std::chrono::time_point<std::chrono::system_clock> start, end;
     cout << "Introduzca el numero de elementos";
     cin >> n;
     int nHilos = 10;

     thread hilos[nHilos];

     start = std::chrono::system_clock::now();

     for(int j=0;j<10;j++)
     {
         if(j%2==0)
            hilos[j] = thread(productor);
         else
            hilos[j] = thread(consumidor);
     }

     for(int k=0;k<10;k++)
        hilos[k].join();

    end = std::chrono::system_clock::now();

    std::chrono::duration<double> elapsed_seconds = end-start;
    std::time_t end_time = std::chrono::system_clock::to_time_t(end);

    cout << "Tiempo : " <<elapsed_seconds.count()<<" segundos"<<endl;
    system("pause");
    return(0);
}

