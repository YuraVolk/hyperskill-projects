package tracker.commands;

import tracker.AvailableCourses;
import tracker.LearningProgressTracker;
import tracker.Student;

import java.util.HashSet;
import java.util.Set;

public class NotifyCommand implements TrackerCommand {
    private static final AvailableCourses[] courses = AvailableCourses.values();

    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        Set<Student> notifications = new HashSet<>();
        for (Student student : tracker.getStudents()) {
            for (int i = 0; i < student.coursePoints.size(); i++) {
                if (courses[i].targetPoints <= student.coursePoints.get(i)) {
                    if (student.notify(courses[i])) notifications.add(student);
                }
            }
        }

        System.out.printf("Total %s students have been notified.", notifications.size());
    }
}
