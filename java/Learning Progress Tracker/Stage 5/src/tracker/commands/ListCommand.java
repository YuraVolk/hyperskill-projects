package tracker.commands;

import tracker.LearningProgressTracker;

public class ListCommand implements TrackerCommand {
    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        if (tracker.getStudents().isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            tracker.getStudents().forEach(student -> System.out.println(student.hashCode()));
        }
    }
}
