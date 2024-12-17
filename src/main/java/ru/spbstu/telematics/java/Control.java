package ru.spbstu.telematics.java;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Control {
    private boolean isOpen = false;
    private final Lock lock = new ReentrantLock(); // Объект блокировки для синхронизации потоков
    private final Condition museumOpen = lock.newCondition();

    public void openMuseum() {
        lock.lock(); // Захват блокировки для синхронизации доступа
        try {
            isOpen = true;
            System.out.println("Control: Музей открыт.");
            museumOpen.signalAll(); // Разбудить все потоки, которые ожидают открытия музея
        } finally {
            lock.unlock(); // Освободить блокировку
        }
    }

    public void closeMuseum() {
        lock.lock();
        try {
            isOpen = false;
            System.out.println("Control: Музей закрыт.");
        } finally {
            lock.unlock();
        }
    }

}