package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

/**
 * Реализация класса для поиска индекса объекта, в массиве объектов.
 * Поиск реализован с помощью ForkJoinPool.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 10.01.2023
 */
public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    public static final int MIN_SIZE_ARRAY = 9;
    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelSearchIndex(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    /**
     * Метод, будет рекурсивно вызываться до тех пор, пока, размер массива не будет равен заданному значению,
     * тогда будет произведен линейный поиск элемента.
     *
     * @return результат линейного поиска, если массив не превышает заданного значения, либо,
     * значение последнего найденного индекса.
     */
    @Override
    protected Integer compute() {
        int arrSize = to - from + 1;
        if (to > MIN_SIZE_ARRAY && arrSize > 2) {
            int mid = (from + to) / 2;
            ParallelSearchIndex<T> firstArrayParallelSearch = new ParallelSearchIndex<T>(array, from, mid, element);
            ParallelSearchIndex<T> secondArrayParallelSearch = new ParallelSearchIndex<T>(array, mid + 1, to, element);
            firstArrayParallelSearch.fork();
            secondArrayParallelSearch.fork();
            return Math.max(firstArrayParallelSearch.join(), secondArrayParallelSearch.join());
        }
        return search();
    }

    /**
     * Метод производит линейный поиск среди заданнного интервала в массиве объектов.
     *
     * @return -1 если элемент не найден, либо, индекс найденного элемента.
     */
    private Integer search() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
