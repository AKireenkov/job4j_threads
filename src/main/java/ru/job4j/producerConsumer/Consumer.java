package ru.job4j.producerConsumer;

public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

    public Consumer(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        System.out.println("Взяли из очереди значение " + simpleBlockingQueue.poll());
    }
}
