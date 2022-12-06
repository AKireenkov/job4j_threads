package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    void when0IncrementThen0Get() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get()).isEqualTo(0);
    }

    @Test
    void when1IncrementThen1Get() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);
    }

    @Test
    void when3IncrementThen3Get() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(3);
    }
}