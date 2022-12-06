package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Реализация неблокирующего счетчика по шаблону check-then-act.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 06.12.2022
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    /**
     * Метод увеличивает значение счетчика на 1.
     * С ним могут работать несколько нитей. Достикается за счет получения текущего значениея счетчика в do
     * после этого, сравниваем текущее значение с ранее полученным.
     * Если они совпадают, тогда увеличиваем значение на 1. Если нет - еще раз получаем значение счетчика.
     */
    public void increment() {
        int currentNum;
        do {
            currentNum = count.get();
        } while (!count.compareAndSet(currentNum, currentNum + 1));
    }

    /**
     * @return возврщает текущее значение счетчика.
     */
    public int get() {
        return count.get();
    }
}