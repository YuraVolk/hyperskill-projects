package org.example.algorithms;

import org.example.Main;
import org.example.Matrix;

public class MultiplicationByConstantAlgorithm implements ParametrizedMatrixAlgorithm<Double> {
    @Override
    public Matrix execute(Matrix firstMatrix, Double value) {
        Matrix newMatrix = Matrix.ofAnotherMatrix(firstMatrix.matrix());
        for (int i = 0; i < newMatrix.rows(); i++) {
            for (int j = 0; j < newMatrix.cols(); j++) {
                newMatrix.matrix()[i][j] *= value;
            }
        }

        return newMatrix;
    }

    @Override
    public Matrix inputFromScanner() {
        Matrix matrix = Matrix.inputMatrixFromScanner(null);
        System.out.print("Enter constant: ");
        return this.execute(matrix, Double.parseDouble(Main.scanner.nextLine()));
    }
}
