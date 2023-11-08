package org.example.algorithms;

import org.example.Matrix;

import java.util.stream.IntStream;

public class MatrixMultiplicationAlgorithm implements ParametrizedMatrixAlgorithm<Matrix> {
    @Override
    public Matrix inputFromScanner() {
        return this.execute(Matrix.inputMatrixFromScanner("first"), Matrix.inputMatrixFromScanner("second"));
    }

    @Override
    public Matrix execute(Matrix firstMatrix, Matrix secondMatrix) {
        if (firstMatrix.rows() != secondMatrix.cols()) throw new IllegalArgumentException(MatrixAlgorithm.ERROR_MESSAGE);

        Matrix newMatrix = Matrix.ofAnotherMatrix(new Double[firstMatrix.rows()][secondMatrix.cols()]);
        for (int i = 0; i < newMatrix.rows(); i++) {
            for (int j = 0; j < newMatrix.cols(); j++) {
                int finalI = i;
                int finalJ = j;
                newMatrix.matrix()[i][j] = IntStream.range(0, newMatrix.matrix()[i].length)
                        .mapToDouble(k -> firstMatrix.matrix()[finalI][k] * secondMatrix.matrix()[k][finalJ]).sum();
            }
        }

        return newMatrix;
    }
}
