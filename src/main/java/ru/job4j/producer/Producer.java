package ru.job4j.producer;

/**
 * Реализация класса Producer - добавляет в очередь элемент.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 06.12.2022
 */
public class Producer implements Runnable {
    private final SimpleBlockingQueue<Integer> simpleBlockingQueue;
    private final int value;

    public Producer(SimpleBlockingQueue<Integer> simpleBlockingQueue, int value) {
        this.simpleBlockingQueue = simpleBlockingQueue;
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println("Помещаем в очередь значение " + value);
        try {
            simpleBlockingQueue.offer(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
