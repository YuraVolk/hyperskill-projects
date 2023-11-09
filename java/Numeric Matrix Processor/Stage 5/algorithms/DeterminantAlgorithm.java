package org.example.algorithms;

import org.example.Matrix;

public class DeterminantAlgorithm implements MatrixAlgorithm {
    @Override
    public Matrix inputFromScanner() {
        return this.execute(Matrix.inputMatrixFromScanner(null));
    }

    private Double[][] getSubMatrix(Matrix matrix, int col) {
        Double[][] subMatrix = new Double[matrix.rows() - 1][matrix.cols() - 1];
        for (int i = 1; i < matrix.rows(); i++) {
            for (int j = 0, k = 0; j < matrix.cols(); j++) {
                if (j != col) subMatrix[i - 1][k++] = matrix.matrix()[i][j];
            }
        }

        return subMatrix;
    }

    private Double determinant(Matrix matrix) {
        if (matrix.rows() == 2) {
            return matrix.matrix()[0][0] * matrix.matrix()[1][1] - matrix.matrix()[0][1] * matrix.matrix()[1][0];
        }

        double det = 0.0;
        for (int col = 0; col < matrix.cols(); col++) {
            double sign = (col % 2 == 0) ? 1 : -1;
            double subMatrixDet = this.determinant(Matrix.ofAnotherMatrix(getSubMatrix(matrix, col)));
            det += sign * matrix.matrix()[0][col] * subMatrixDet;
        }

        return det;
    }

    @Override
    public Matrix execute(Matrix matrix) {
        Matrix determinant = Matrix.ofAnotherMatrix(new Double[1][1]);
        if (matrix.rows() != matrix.cols()) {
            determinant.matrix()[0][0] = Double.POSITIVE_INFINITY;
        } else if (matrix.rows() == 1) {
            determinant.matrix()[0][0] = matrix.matrix()[0][0];
        } else determinant.matrix()[0][0] = determinant(matrix);

        return determinant;
    }
}
