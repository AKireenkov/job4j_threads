package ru.job4j.pool;

import ru.job4j.producer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    /**
     * В конструкторе происходит заполнение списка threads, нитями, в количестве, равном количеству процессов в системе.
     * Нити работают, пока они не будут помечены флагом interrupted().
     * Каждая нить, берет задачу из очереди и запускает ее.
     */
    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();
            this.threads.add(thread);
        }
    }

    /**
     * Метод, помещает в очередь задачи типа Runnable
     *
     * @param job задача, которую помещаем в очередь
     * @throws InterruptedException
     */
    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод, помечает для закрытия, каждую нить из списка threads.
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 11; i++) {
            int taskNum = i;
            threadPool.work(() -> {
                String message = Thread.currentThread().getName() + " " + taskNum;
                System.out.println(message);
            });
        }
        Thread.sleep(5000);
        threadPool.shutdown();
    }
}
