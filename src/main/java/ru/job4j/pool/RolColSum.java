package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

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
        return CompletableFuture.supplyAsync(() -> {
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
        });
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