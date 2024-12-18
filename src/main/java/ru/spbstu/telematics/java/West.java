package ru.spbstu.telematics.java;

public class West implements Runnable {
    private final Control control;

    public West(Control control) {
        this.control = control;
    }

    @Override
    public void run() {
        int visitorId = 1; // Уникальный идентификатор для выхода
        while (true) {
            try {
                Thread.sleep(400);
                control.exitMuseum(visitorId++); // Посетитель выходит из музея
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Завершаем поток при прерывании
            }
        }
        System.out.println("West: Поток завершен.");
    }
}
