package ru.job4j.concurrent;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'-', '\\', '|', '/'};
        while (!currentThread().isInterrupted()) {
            for (char c : process) {
                System.out.print("\r load: " + c);
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
