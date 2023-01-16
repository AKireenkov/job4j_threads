package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        isValid(matrix);
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = rowAndColSum(matrix, i, n - 1);
        }
        return sums;
    }

    public static Sums rowAndColSum(int[][] data, int number, int size) {
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i <= size; i++) {
            rowSum += data[number][i];
            colSum += data[i][number];
        }
        Sums sums = new Sums();
        sums.setColSum(colSum);
        sums.setRowSum(rowSum);
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        isValid(matrix);
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = rowAndColAsyncSum(matrix, i, n - 1).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> rowAndColAsyncSum(int[][] data, int number, int size) {
        return CompletableFuture.supplyAsync(() -> rowAndColSum(data, number, size));
    }

    private static void isValid(int[][] data) {
        int size = data.length;
        for (int[] el : data) {
            if (el.length != size) {
                throw new IllegalArgumentException("matrix is not square !");
            }
        }
    }
}