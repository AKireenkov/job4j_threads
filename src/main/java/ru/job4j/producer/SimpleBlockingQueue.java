package ru.job4j.producer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
            queue.wait();
        }
        queue.offer(value);
        queue.notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            queue.wait();

        }
        T result = queue.poll();
        queue.notify();
        return result;
    }
}
