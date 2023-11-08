package org.example;

import org.example.algorithms.*;

import java.util.Arrays;

public class NumericMatrixProcessor {
    private enum MenuOption {
        ADD_MATRICES(1, "Add matrices", new AdditionAlgorithm()),
        MULTIPLY_BY_CONSTANT(2, "Multiply matrix by a constant", new MultiplicationByConstantAlgorithm()),
        MULTIPLY_MATRICES(3, "Multiply matrices", new MatrixMultiplicationAlgorithm()),
        TRANSPOSE_MATRIX(4, "Transpose matrix", new TranspositionAlgorithm()),
        EXIT(0, "Exit", null);

        final int number;
        private final String description;
        final MatrixAlgorithm algorithm;

        MenuOption(int number, String description, MatrixAlgorithm algorithm) {
            this.number = number;
            this.description = description;
            this.algorithm = algorithm;
        }

        @Override
        public String toString() {
            return String.format("%s. %s", number, description);
        }
    }

    private MenuOption findOptionByNumber(int number) {
        for (MenuOption option : MenuOption.values()) {
            if (option.number == number) return option;
        }

        throw new IllegalArgumentException("");
    }

    public void menu() {
        while (true) {
            try {
                Arrays.stream(MenuOption.values()).forEach(System.out::println);
                System.out.print("Your choice: ");
                MenuOption option = this.findOptionByNumber(Integer.parseInt(Main.scanner.nextLine()));
                if (option == MenuOption.EXIT) break;
                System.out.printf("The result is:\n%s\n", option.algorithm.inputFromScanner());
            } catch (Exception exception) {
                System.out.println(MatrixAlgorithm.ERROR_MESSAGE);
            }

            System.out.println();
        }
    }
}
