package org.example;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        int maxLeadingSpaces = Arrays.stream(matrix).flatMap(Stream::of).mapToInt(d -> {
            String stringValue = Double.toString(Math.abs(d));
            int decimalIndex = stringValue.indexOf(".");
            if (decimalIndex >= 0) {
                return stringValue.length() - decimalIndex - 1;
            } else return 0;
        }).max().orElseThrow();
        DecimalFormat format = maxLeadingSpaces == 0 ? new DecimalFormat("#") : new DecimalFormat("#." + "0".repeat(maxLeadingSpaces));
        format.setDecimalSeparatorAlwaysShown(false);

        return Arrays.stream(matrix).map(row -> Arrays.stream(row)
                        .mapToDouble(Double::doubleValue)
                        .mapToObj(format::format)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
