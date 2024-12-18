package ru.spbstu.telematics.java;

public class Director implements Runnable {
    private final Control control;

    public Director(Control control) {
        this.control = control;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) { // Музей открывается и закрывается 3 раза
                control.openMuseum();
                Thread.sleep(2000); // Музей работает 5 секунд
                control.closeMuseum();
                Thread.sleep(1000); // Перерыв перед следующим открытием
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
