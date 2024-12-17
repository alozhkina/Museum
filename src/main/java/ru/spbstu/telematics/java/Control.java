package ru.spbstu.telematics.java;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Control {
    private boolean isOpen = false;
    private final Lock lock = new ReentrantLock(); // Объект блокировки для синхронизации потоков
    private final Condition museumOpen = lock.newCondition();
    private int visitorCount = 0;

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

    public void enterMuseum(int visitorId) {
        lock.lock();
        try {
            while (!isOpen) {
                try {
                    museumOpen.await();  // Ожидание открытия музея
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Когда музей открыт, посетитель может войти
            visitorCount++;
            System.out.println("Visitor-" + visitorId + " вошел в музей. Количество посетителей: " + visitorCount);
        } finally {
            lock.unlock();  // Освобождаем блокировку
        }
    }

}