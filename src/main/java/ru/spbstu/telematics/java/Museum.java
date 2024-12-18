package ru.spbstu.telematics.java;

public class Museum {
    public static void main(String[] args) {
        Control control = new Control();

        Thread director = new Thread(new Director(control));
        Thread east = new Thread(new East(control));
        Thread west = new Thread(new West(control));

        director.start();
        east.start();
        west.start();

        try {
            director.join(); // Ждем завершения потока Director
            east.interrupt();
            west.interrupt();
            east.join(); // Ждем завершения потока East
            west.join(); // Ждем завершения потока West
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Программа завершена.");
    }
}
