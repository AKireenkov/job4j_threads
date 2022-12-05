package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", user.getUsername(), email);
        String body = String.format("Add a new event to %s", email);

        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject, body, email);
            }
        });
        close();
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}
