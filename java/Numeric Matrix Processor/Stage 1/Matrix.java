package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Matrix(Double[][] matrix, int rows, int cols) {
    public static Matrix ofAnotherMatrix(Double[][] matrix) {
        return new Matrix(Arrays.stream(matrix).map(Double[]::clone).toArray(Double[][]::new), matrix.length, matrix[0].length);
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix).map(row -> Arrays.stream(row)
                        .mapToInt(Double::intValue)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
