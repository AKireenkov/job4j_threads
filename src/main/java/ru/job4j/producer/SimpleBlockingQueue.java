package ru.job4j.producer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Реализация блокирующей очереди, ограниченной по размеру.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 06.12.2022
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private volatile Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    /**
     * Метод помещает в очередь элемент.
     * Если очередь заполнена, нить переходит в ожидание.
     * После добавления элемента в очередь, оповещаются другие нити, находящиеся в режиме ожидания.
     *
     * @param value элемент, который необходимо добавить в очередь
     * @throws InterruptedException исключение, может возникнуть при вызове wait()
     */
    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == size) {
                queue.wait();
            }
            queue.offer(value);
            queue.notify();
        }
    }

    /**
     * Метод забирает элемент из очереди.
     * Если очередь пустая, нить переходит в ожидание.
     * После взятия элемента из очереди, оповещаются другие нити, находящиеся в режиме ожидания.
     *
     * @return элемент, который взяли из очереди.
     * @throws InterruptedException исключение, может возникнуть при вызове wait()
     */
    public T poll() throws InterruptedException {
        T result = queue.poll();
        synchronized (queue) {
            while (queue.size() == 0) {
                queue.wait();
            }
            //   T result = queue.poll();
            queue.notify();
        }
        return result;
    }
}
