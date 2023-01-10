package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchIndexTest {
    @Test
    public void whenStringTypeAndParallelSearch() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String[] arr = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test1", "test2", "test5", "test4"};
        String el = "test5";
        int to = arr.length - 1;
        int from = 0;
        int index = forkJoinPool.invoke(new ParallelSearchIndex<>(arr, from, to, el));
        assertThat(index).isEqualTo(9);
    }

    @Test
    public void whenIntTypeAndLineSearch() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer[] arr = {8, 11, 3, 0, -1, 7, 1, 23};
        int el = 0;
        int to = arr.length - 1;
        int from = 0;
        int index = forkJoinPool.invoke(new ParallelSearchIndex<>(arr, from, to, el));
        assertThat(index).isEqualTo(3);
    }

    @Test
    public void whenObjectTypeAndParallelSearch() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] arr = {1, "test3", new Object(), true, 5, 6, 0, 8, false, "test", 't'};
        Object el = false;
        int to = arr.length - 1;
        int from = 0;
        int index = forkJoinPool.invoke(new ParallelSearchIndex<>(arr, from, to, el));
        assertThat(index).isEqualTo(8);
    }

    @Test
    public void whenElementNotFound() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] arr = {1, "test3", new Object(), true, 5, 6, 0, 8, false, "test", 't'};
        Object el = 'a';
        int to = arr.length - 1;
        int from = 0;
        int index = forkJoinPool.invoke(new ParallelSearchIndex<>(arr, from, to, el));
        assertThat(index).isEqualTo(-1);
    }
}