package org.example.algorithms;

import org.example.Matrix;

public interface ParametrizedMatrixAlgorithm<T> extends MatrixAlgorithm {
    Matrix execute(Matrix firstMatrix, T value);
}
