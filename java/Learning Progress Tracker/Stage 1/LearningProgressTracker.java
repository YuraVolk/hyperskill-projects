package tracker;

import java.util.Scanner;

public class LearningProgressTracker {
    public LearningProgressTracker() {
        System.out.println("Learning Progress Tracker");
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isBlank()) {
                System.out.println("No input");
                continue;
            }

            if (input.equals("exit")) {
                System.out.println("Bye!");
                return;
            } else {
                System.out.println("Error: unknown command!");
            }
        }
    }
}
