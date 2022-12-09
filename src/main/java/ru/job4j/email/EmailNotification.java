package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Реализация класса, для отправки сообщений юзеру, с использованием ThreadPool.
 * В данном случае, инициализация происходит по количеству ядер в системе.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 09.12.2022
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Метод отправки соообщения.
     * Передает в метод send() данные для отправки письма по шаблону.
     * Метод send() вызывается в пуле нитей, после этого, необходимо всегда закрывать пул.
     *
     * @param user объект User, которому необходимо отправить email.
     */
    public void emailTo(User user) {
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", user.getUsername(), email);
        String body = String.format("Add a new event to %s", email);
        pool.submit(() -> send(subject, body, email));
        close();
    }

    /**
     * Метод закрывает пул Thread. Что бы убедиться что пул точно закрыт, добавлен цикл while
     * с ожиданием пока весь пул не будет закрыт.
     */
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
