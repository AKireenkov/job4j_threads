package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.job4j.pool.RolColSum.asyncSum;
import static ru.job4j.pool.RolColSum.sum;

class RolColSumTest {
    @Test
    public void when4on4Matrix() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Sums[] asyncSum = asyncSum(matrix);
        Sums[] sum = sum(matrix);
        Sums expected = new Sums();
        expected.setColSum(32);
        expected.setRowSum(26);

        assertThat(asyncSum[1]).isEqualTo(expected);
        assertThat(sum[1]).isEqualTo(expected);
    }

    @Test
    public void when3on3Matrix() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {5, 5, 6},
                {7, 8, 9}
        };
        Sums[] asyncSum = asyncSum(matrix);
        Sums[] sum = sum(matrix);
        Sums expected = new Sums();
        expected.setColSum(18);
        expected.setRowSum(24);

        assertThat(asyncSum[2]).isEqualTo(expected);
        assertThat(sum[2]).isEqualTo(expected);
    }

    @Test
    public void whenElementNotExistInMatrix() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {5, 5, 6},
                {7, 8, 9}
        };
        Sums[] asyncSum = asyncSum(matrix);
        Sums[] sum = sum(matrix);
        assertThatThrownBy(() -> asyncSum[5].getColSum()).isInstanceOf(ArrayIndexOutOfBoundsException.class);
        assertThatThrownBy(() -> sum[5].getColSum()).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void whenMatrixIsNotASquare() {
        int[][] matrix = {
                {1, 2, 3},
                {5, 5, 6, 7},
                {7}
        };
        assertThatThrownBy(() -> asyncSum(matrix))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("matrix is not square !");
        assertThatThrownBy(() -> asyncSum(matrix))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("matrix is not square !");
    }
}