package org.example;

import org.example.algorithms.AdditionAlgorithm;
import org.example.algorithms.MultiplicationByConstantAlgorithm;

import java.util.Arrays;

public class NumericMatrixProcessor {
    public Matrix inputMatrix() {
        int rows = Main.scanner.nextInt();
        int cols = Main.scanner.nextInt();
        Main.scanner.nextLine();
        Double[][] matrix = new Double[rows][cols];
        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.stream(Main.scanner.nextLine().split(" "))
                    .mapToDouble(Double::parseDouble)
                    .boxed().limit(cols).toArray(Double[]::new);
        }

        return Matrix.ofAnotherMatrix(matrix);
    }

    public Matrix sumMatrices(Matrix firstMatrix, Matrix secondMatrix) {
        return new AdditionAlgorithm().execute(firstMatrix, secondMatrix);
    }

    public Matrix multiplyByConstant(Matrix matrix, Double constant) {
        return new MultiplicationByConstantAlgorithm().execute(matrix, constant);
    }
}
