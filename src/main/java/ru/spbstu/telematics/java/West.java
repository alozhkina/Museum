package ru.spbstu.telematics.java;

public class West implements Runnable {
    private final Control control;

    public West(Control control) {
        this.control = control;
    }

    @Override
    public void run() {
        while (true) {
            if (!control.isMuseumOpen() && control.hasVisitors()) {
                while (control.hasVisitors()) {
                    control.exitRandomVisitor();
                    try {
                        Thread.sleep(50);  // Задержка между выходами
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("West: Все посетители покинули музей.");
            }


            if (control.hasVisitors()) {
                control.exitRandomVisitor();
            }

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("West: Поток завершен.");
    }
}
