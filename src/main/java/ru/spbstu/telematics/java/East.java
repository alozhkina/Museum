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
            if (!control.isMuseumOpen()) {
                try {
                    System.out.println("East: Музей закрыт, поток ждет открытия.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (control.isMuseumOpen()) {
                control.enterMuseum(visitorId++);
            }

            // Задержка между прибытием новых посетителей
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Завершаем поток при прерывании
            }
        }
        System.out.println("East: Поток завершен.");
    }
}
