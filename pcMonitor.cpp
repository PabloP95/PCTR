#include <thread>
#include <mutex>
#include <chrono>
#include <iostream>
#include <condition_variable>
//Monitor en C++
struct pcMonitor
{
    int* vect;
    int tam;

    int front, rear, cont;

    std::mutex candado;
    std::condition_variable no_llena;
    std::condition_variable no_vacia;

    pcMonitor(int tam) : tam(tam), front(0), rear(0), cont(0)
    {
        vect = new int[tam];
    }

    ~pcMonitor()
    {
        delete[] vect;
    }

    void insertar(int valor)
    {
        std::unique_lock<std::mutex> can(candado);

        no_llena.wait(can,[this](){return cont!=tam; });

        vect[rear] = valor;
        rear = (rear + 1)% tam;
        ++cont;
        no_vacia.notify_one();
    }

    void eliminar()
    {
        std::unique_lock<std::mutex> can(candado);
        no_vacia.wait(can,[this](){return cont!=0 ;});
        int num = vect[front];
        front = (front + 1) % tam;
        --cont;
        no_llena.notify_one();
    }
};
