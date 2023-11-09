package org.example.algorithms;

import org.example.Matrix;

public interface MatrixAlgorithm {
    Matrix inputFromScanner();

    default Matrix execute(Matrix matrix) {
        return matrix;
    }

    String ERROR_MESSAGE = "This operation cannot be performed.";
}
