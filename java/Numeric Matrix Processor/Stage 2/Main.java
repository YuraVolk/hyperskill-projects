package org.example;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            NumericMatrixProcessor processor = new NumericMatrixProcessor();
            System.out.println(processor.multiplyByConstant(processor.inputMatrix(), scanner.nextDouble()));
        } catch (Exception ignored) {
            System.out.println("ERROR");
        }
    }
}
