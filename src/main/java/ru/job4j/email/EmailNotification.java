package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", user.getUsername(), email);
        String body = String.format("Add a new event to %s", email);


        pool.submit(() -> send(subject, body, email));
        close();
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
