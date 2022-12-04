package ru.job4j.producerConsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private static final int SIZE = 16;

    public synchronized void offer(T value) {
        while (queue.size() == SIZE) {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        queue.notify();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                queue.wait();
                queue.notify();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}
