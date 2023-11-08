package org.example;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public record Matrix(Double[][] matrix, int rows, int cols) {
    public static Matrix ofAnotherMatrix(Double[][] matrix) {
        return new Matrix(Arrays.stream(matrix).map(Double[]::clone).toArray(Double[][]::new), matrix.length, matrix[0].length);
    }

    public static Matrix inputMatrixFromScanner(String matrixName) {
        System.out.printf("Enter size of%s matrix: ", matrixName == null ? "" : " " + matrixName);
        int rows = Main.scanner.nextInt();
        int cols = Main.scanner.nextInt();
        Main.scanner.nextLine();
        System.out.printf("Enter%s matrix:\n", matrixName == null ? "" : " " + matrixName);
        Double[][] matrix = new Double[rows][cols];
        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.stream(Main.scanner.nextLine().split(" "))
                    .mapToDouble(Double::parseDouble)
                    .boxed().limit(cols).toArray(Double[]::new);
        }

        return Matrix.ofAnotherMatrix(matrix);
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix).map(row -> Arrays.stream(row)
                        .mapToDouble(Double::doubleValue)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
