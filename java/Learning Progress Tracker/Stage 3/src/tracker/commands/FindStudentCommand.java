package tracker.commands;

import tracker.AvailableCourses;
import tracker.LearningProgressTracker;
import tracker.Student;

import java.util.Objects;
import java.util.Optional;

public class FindStudentCommand implements TrackerCommand {
    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        System.out.printf("Enter an id or '%s' to return:%n", TrackerCommand.STOP_MESSAGE);
        while (true) {
            String input = LearningProgressTracker.scanner.nextLine();
            if (Objects.equals(input, TrackerCommand.STOP_MESSAGE)) break;
            int numericId;
            try {
                numericId = Integer.parseInt(input);
            } catch (Exception ignored) { continue; }
            Optional<Student> studentOptional = tracker.getStudents().stream().filter(queried -> queried.hashCode() == numericId).findFirst();
            if (studentOptional.isEmpty()) {
                System.out.printf("No student is found for id=%s%n", numericId);
            } else {
                var points = studentOptional.get().coursePoints();
                System.out.printf("%s points:", numericId);
                for (var course : AvailableCourses.values()) {
                    System.out.printf(" %s=%s;", course.name, points.get(course.id));
                }
                System.out.println();
            }
        }
    }
}
