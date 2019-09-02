#include <iostream>
#include <random>
#include <thread>
#include <chrono>
#include <ctime>
#include <cstdlib>
#include <atomic>
using namespace std;


static std::atomic <int> aciertos;
std::random_device rd;
std::mt19937 gen(rd());
std::uniform_real_distribution<> dis(0, 1);


void PiMonteCarlo(int p)
{
    double cx,cy;
    for(int n=0;n<p;n++)
    {
        cx = dis(gen);
        cy = dis(gen);

        if((cx*cx+cy*cy)<1)
            aciertos++;
    }
}

int main()
{
    std::chrono::time_point<std::chrono::system_clock> start, end;
    int nPuntos,nTareas;
    int i;
    cout << "Introduzca el numero de puntos a lanzar\n";
    cin>> nPuntos;

    cout << "Introduzca el numero de hilos a lanzar\n";
    cin >> nTareas;


    i=(nPuntos/nTareas);
    thread hilos[nTareas];

    start = std::chrono::system_clock::now();

    for(int j=0;j<nTareas;j++) hilos[j] = thread(PiMonteCarlo,i);
    for(int j=0;j<nTareas;j++) hilos[j].join();

    double PI = (4.0*aciertos/nPuntos);

    end = std::chrono::system_clock::now();

    std::chrono::duration<double> elapsed_seconds = end-start;
    std::time_t end_time = std::chrono::system_clock::to_time_t(end);

    cout << "Aproximacion PI " << PI << endl;
    std::cout << "Tiempo: " << elapsed_seconds.count() << "s\n";
    system("pause");

    return(0);

}
