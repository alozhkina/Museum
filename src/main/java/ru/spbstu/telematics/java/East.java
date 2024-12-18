package ru.spbstu.telematics.java;

public class East implements Runnable {
    private final Control control;

    public East(Control control) {
        this.control = control;
    }

    @Override
    public void run() {
        int visitorId = 1; // Уникальный идентификатор для посетителей
        while (true) {
            try {
                Thread.sleep(400); // Интервал между входами
                if (!control.isMuseumOpen()) {
                    break;
                }
                control.enterMuseum(visitorId++); // Посетитель входит в музей
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Завершаем поток при прерывании
            }
        }
        System.out.println("East: Поток завершен.");
    }
}
