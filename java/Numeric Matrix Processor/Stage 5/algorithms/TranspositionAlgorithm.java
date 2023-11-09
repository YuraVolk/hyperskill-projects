package org.example.algorithms;

import org.example.Main;
import org.example.Matrix;
import org.example.utils.EnumUtils;

import java.util.Arrays;

public class TranspositionAlgorithm implements ParametrizedMatrixAlgorithm<Integer> {
    private enum TranspositionDirection {
        MAIN_LINE(1, "Main diagonal"),
        SIDE_LINE(2, "Side diagonal"),
        VERTICAL_LINE(3, "Vertical Line"),
        HORIZONTAL_LINE(4, "Horizontal Line");

        final int number;
        private final String description;

        TranspositionDirection(int number, String description) {
            this.number = number;
            this.description = description;
        }

        @Override
        public String toString() {
            return String.format("%s. %s", number, description);
        }
    }

    @Override
    public Matrix inputFromScanner() {
        System.out.println();
        Arrays.stream(TranspositionDirection.values()).forEach(System.out::println);
        System.out.print("Your choice: ");
        Integer method = Integer.parseInt(Main.scanner.nextLine());
        return this.execute(Matrix.inputMatrixFromScanner(null), method);
    }

    @Override
    public Matrix execute(Matrix firstMatrix, Integer method) {
        TranspositionDirection direction = EnumUtils.getEnumValues(TranspositionDirection.values(), (option) -> option.number == method);
        Matrix newMatrix = Matrix.ofAnotherMatrix(new Double[firstMatrix.cols()][firstMatrix.rows()]);
        for (int i = 0; i < newMatrix.rows(); i++) {
            for (int j = 0; j < newMatrix.cols(); j++) {
                switch (direction) {
                    case MAIN_LINE -> newMatrix.matrix()[i][j] = firstMatrix.matrix()[j][i];
                    case SIDE_LINE -> newMatrix.matrix()[i][j] = firstMatrix.matrix()[firstMatrix.rows() - 1 - j][firstMatrix.cols() - 1 - i];
                    case VERTICAL_LINE -> newMatrix.matrix()[i][j] = firstMatrix.matrix()[i][firstMatrix.rows() - 1 - j];
                    case HORIZONTAL_LINE -> newMatrix.matrix()[i][j] = firstMatrix.matrix()[firstMatrix.cols() - 1 - i][j];
                }
            }
        }

        return newMatrix;
    }
}
