package ru.spbstu.telematics.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MuseumTests {

    @Test
    void testOpenAndCloseMuseum() {
        Control control = new Control();
        control.openMuseum();
        assertTrue(control.isMuseumOpen(), "Музей должен быть открыт");

        control.closeMuseum();
        assertFalse(control.isMuseumOpen(), "Музей должен быть закрыт");
    }

    @Test
    void testEnterAndExitMuseum() {
        Control control = new Control();
        control.openMuseum();

        control.enterMuseum(1);
        assertTrue(control.hasVisitors(), "В музее должен быть хотя бы один посетитель");

        control.exitRandomVisitor();
        assertFalse(control.hasVisitors(), "В музее не должно остаться посетителей");
    }

    @Test
    void testConcurrentVisitors() {
        Control control = new Control();
        control.openMuseum();

       Runnable addVisitors = () -> {
            for (int i = 0; i < 100; i++) {
                control.enterMuseum(i);
            }
        };

         Runnable removeVisitors = () -> {
            for (int i = 0; i < 100; i++) {
                if (control.hasVisitors()) {
                    control.exitRandomVisitor();
                }
                else --i;
            }
        };

        Thread thread1 = new Thread(addVisitors);
        Thread thread2 = new Thread(removeVisitors);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // После завершения потоков проверяем состояние
        assertFalse(control.hasVisitors(), "Все посетители должны покинуть музей");
    }
}