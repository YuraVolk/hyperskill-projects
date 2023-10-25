package tracker.commands;

import tracker.AvailableCourses;
import tracker.LearningProgressTracker;
import tracker.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddPointsCommand implements TrackerCommand {
    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        System.out.printf("Enter an id and points or '%s' to return:%n", TrackerCommand.STOP_MESSAGE);
        while (true) {
            String input = LearningProgressTracker.scanner.nextLine();
            if (Objects.equals(input, TrackerCommand.STOP_MESSAGE)) break;
            try {
                List<Integer> inputs = Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
                if (inputs.size() != AvailableCourses.values().length + 1) throw new Exception();
                if (!inputs.stream().allMatch(integer -> integer >= 0)) throw new Exception();

                Optional<Student> student = tracker.getStudents().stream().filter(queried -> queried.hashCode() == inputs.get(0)).findFirst();
                if (student.isEmpty()) {
                    System.out.printf("No student is found for id=%s.%n", inputs.get(0));
                } else {
                    var studentPoints = student.get().coursePoints;
                    for (int i = 0; i < AvailableCourses.values().length; i++) {
                        studentPoints.set(i, inputs.get(i + 1) + studentPoints.get(i));
                    }
                    student.get().saveCurrentPoints();
                    System.out.println("Points updated.");
                }
            } catch (Exception ignored) {
                if (!input.split(" ")[0].chars().allMatch(Character::isDigit)) {
                    System.out.printf("No student is found for id=%s.%n", input.split(" ")[0]);
                } else System.out.println("Incorrect points format.");
            }
        }
    }
}
