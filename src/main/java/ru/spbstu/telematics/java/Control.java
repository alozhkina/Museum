package ru.spbstu.telematics.java;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Control {
    private boolean isOpen = false;
    private final Lock lock = new ReentrantLock(); // Объект блокировки для синхронизации потоков
    private final Condition museumOpen = lock.newCondition();
    private final List<Integer> visitors = new ArrayList<>();

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
            visitors.add(visitorId);
            System.out.println("Visitor-" + visitorId + " вошел в музей. Количество посетителей: " + visitors.size());
        } finally {
            lock.unlock();
        }
    }


    public boolean isMuseumOpen() {
        return isOpen;
    }

    public boolean hasVisitors() {
        lock.lock();
        try {
            return !visitors.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public void exitRandomVisitor() {
        lock.lock();
        try {
                int randomIndex = ThreadLocalRandom.current().nextInt(visitors.size());
                int visitorId = visitors.remove(randomIndex);
                System.out.println("Visitor-" + visitorId + " вышел из музея. Количество посетителей: " + visitors.size());
        } finally {
            lock.unlock();
        }
    }

}