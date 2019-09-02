#include <iostream>
#include <random>
#include <chrono>
#include <ctime>
#include <cstdlib>
using namespace std;


int main()
{
    std::chrono::time_point<std::chrono::system_clock> start, end;

    int nPuntos,aciertos=0;
    double PI = 0.0;

    cout << "Introduzca el numero de puntos a lanzar\n";
    cin >> nPuntos;
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_real_distribution<> dis(0, 1);

    std::cout << '\n';
    start = std::chrono::system_clock::now();

    for(int i=0;i<nPuntos;i++)
    {
        double x, y;
        x = dis(gen);
        y = dis(gen);

        if((x*x+y*y)<1)
            aciertos++;
    }


    PI = (4.0*aciertos/nPuntos);

    end = std::chrono::system_clock::now();

    cout <<"La aproximacion del numero PI con "<<nPuntos<< " es "<< PI;


    std::chrono::duration<double> elapsed_seconds = end-start;
    std::time_t end_time = std::chrono::system_clock::to_time_t(end);

    std::cout << "\nTiempo: " << elapsed_seconds.count() << "s\n";
    system("pause");
    return(0);

}
