package tracker;

import tracker.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LearningProgressTracker {
    private final List<Student> students = new ArrayList<>();
    public static final Scanner scanner = new Scanner(System.in);

    public LearningProgressTracker() {
        System.out.println("Learning Progress Tracker");
    }

    public void menu() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("No input");
                continue;
            }

            switch (input) {
                case "add students" -> new AddStudentsCommand().executeOnTracker(this);
                case "list" -> new ListCommand().executeOnTracker(this);
                case "add points" -> new AddPointsCommand().executeOnTracker(this);
                case "find" -> new FindStudentCommand().executeOnTracker(this);
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                case TrackerCommand.STOP_MESSAGE -> System.out.println("Enter 'exit' to exit the program.");
                default -> System.out.println("Unknown command!");
            }
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
