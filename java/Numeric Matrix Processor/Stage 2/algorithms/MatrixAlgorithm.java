package org.example.algorithms;

import org.example.Matrix;

public interface MatrixAlgorithm {
    default Matrix execute(Matrix matrix) {
        return matrix;
    }
}
