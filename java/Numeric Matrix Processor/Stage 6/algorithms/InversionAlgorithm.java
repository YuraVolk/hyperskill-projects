package org.example.algorithms;

import org.example.Matrix;

import java.util.Arrays;
import java.util.stream.IntStream;

public class InversionAlgorithm implements MatrixAlgorithm {
    private static final DeterminantAlgorithm determinantAlgorithm = new DeterminantAlgorithm();
    private static final TranspositionAlgorithm transpositionAlgorithm = new TranspositionAlgorithm();
    private static final MultiplicationByConstantAlgorithm multiplicationByConstantAlgorithm = new MultiplicationByConstantAlgorithm();

    @Override
    public Matrix inputFromScanner() {
        return this.execute(Matrix.inputMatrixFromScanner(null));
    }

    @Override
    public Matrix execute(Matrix matrix) {
        Double determinant = determinantAlgorithm.execute(matrix).matrix()[0][0];
        if (determinant.equals(0.0) || determinant.equals(Double.POSITIVE_INFINITY)) {
            throw new UnsupportedOperationException("This matrix doesn't have an inverse.");
        }

        Double[][] invertedMatrix = new Double[matrix.rows()][matrix.cols()];
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                final int finalI = i;
                final int finalJ = j;
                Double[][] subMatrix = IntStream.range(0, matrix.rows())
                        .filter(m -> m != finalI)
                        .mapToObj(m -> {
                            Double[] row = Arrays.copyOf(matrix.matrix()[m], matrix.cols());
                            return IntStream.range(0, row.length)
                                    .filter(n -> n != finalJ)
                                    .mapToObj(n -> row[n])
                                    .toArray(Double[]::new);
                        }).toArray(Double[][]::new);

                Matrix subMatrixObj = Matrix.ofAnotherMatrix(subMatrix);
                Double subDeterminant = determinantAlgorithm.execute(subMatrixObj).matrix()[0][0];
                invertedMatrix[i][j] = (i + j) % 2 == 0 ? subDeterminant : -subDeterminant;
            }
        }

        Matrix transposedMatrix = transpositionAlgorithm.execute(Matrix.ofAnotherMatrix(invertedMatrix),
                TranspositionAlgorithm.TranspositionDirection.MAIN_LINE.number);
        return multiplicationByConstantAlgorithm.execute(transposedMatrix, 1 / determinant);
    }
}
