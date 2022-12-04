package ru.job4j.producerConsumer;

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
        simpleBlockingQueue.offer(value);
    }
}
