package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.pool.ParallelSearchIndex.search;

class ParallelSearchIndexTest {
    @Test
    public void whenStringTypeAndParallelSearch() {
        String[] arr = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test1", "test2", "test5", "test4"};
        String el = "test5";
        int index = (int) search(arr, el);
        assertThat(index).isEqualTo(9);
    }

    @Test
    public void whenIntTypeAndLineSearch() {
        Integer[] arr = {8, 11, 3, 0, -1, 7, 1, 23};
        int el = 0;
        int index = (int) search(arr, el);
        assertThat(index).isEqualTo(3);
    }

    @Test
    public void whenObjectTypeAndParallelSearch() {
        Object[] arr = {1, "test3", new Object(), true, 5, 6, 0, 8, false, "test", 't'};
        Object el = false;
        int index = (int) search(arr, el);
        assertThat(index).isEqualTo(8);
    }

    @Test
    public void whenElementNotFound() {
        Object[] arr = {1, "test3", new Object(), true, 5, 6, 0, 8, false, "test", 't'};
        Object el = 'a';
        int index = (int) search(arr, el);
        assertThat(index).isEqualTo(-1);
    }
}