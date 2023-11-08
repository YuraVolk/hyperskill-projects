package org.example;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            NumericMatrixProcessor processor = new NumericMatrixProcessor();
            System.out.println(processor.sumMatrices(processor.inputMatrix(), processor.inputMatrix()));
        } catch (Exception ignored) {
            System.out.println("ERROR");
        }
    }
}
