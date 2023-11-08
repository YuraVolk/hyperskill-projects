package org.example.algorithms;

import org.example.Matrix;

public class AdditionAlgorithm implements MatrixAlgorithm {
    @Override
    public Matrix execute(Matrix firstMatrix, Matrix secondMatrix) {
        if (firstMatrix.rows() != secondMatrix.rows() || firstMatrix.cols() != secondMatrix.cols()) {
            throw new IllegalArgumentException("The matrix rows and columns must match for addition to be possible.");
        }

        Matrix newMatrix = Matrix.ofAnotherMatrix(firstMatrix.matrix());
        for (int i = 0; i < newMatrix.rows(); i++) {
            for (int j = 0; j < newMatrix.cols(); j++) {
                newMatrix.matrix()[i][j] += secondMatrix.matrix()[i][j];
            }
        }

        return newMatrix;
    }
}
