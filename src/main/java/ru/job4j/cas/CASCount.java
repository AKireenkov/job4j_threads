package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    private static AtomicInteger num = new AtomicInteger();

    public void increment() {
        int currentNum;
        do {
            currentNum = count.get();
        } while (!count.compareAndSet(currentNum, num.incrementAndGet()));
    }

    public int get() {
        int currentNum;
        do {
            if (count.get() == null) {
                throw new IllegalStateException("count is empty");
            }
            currentNum = count.get();
        } while (!count.compareAndSet(currentNum, num.decrementAndGet()));
        return currentNum;
    }
}