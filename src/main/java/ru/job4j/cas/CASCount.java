package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int currentNum;
        do {
            currentNum = count.get();
        } while (!count.compareAndSet(currentNum, currentNum + 1));
    }

    public int get() {
        return count.get();
    }
}