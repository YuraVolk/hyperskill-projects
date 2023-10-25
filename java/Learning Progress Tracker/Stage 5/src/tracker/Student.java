package tracker;

import java.util.*;

public class Student {
    public final String firstName;
    public final String lastName;
    public final String email;
    public List<Integer> coursePoints = new ArrayList<>(Collections.nCopies(AvailableCourses.values().length, 0));
    private final List<List<Integer>> previousCoursePoints = new ArrayList<>();
    private final List<Boolean> hasBeenNotified = new ArrayList<>(Collections.nCopies(AvailableCourses.values().length, false));

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void saveCurrentPoints() {
        previousCoursePoints.add(new ArrayList<>(coursePoints));
    }

    public List<Integer> getCourseActivity() {
        List<Integer> activity = new ArrayList<>(Collections.nCopies(AvailableCourses.values().length, 0));
        List<Integer> previousPoints = new ArrayList<>(Collections.nCopies(activity.size(), 0));

        for (var points : previousCoursePoints) {
            for (int i = 0; i < points.size(); i++) {
                int difference = points.get(i) - previousPoints.get(i);
                if (difference > 0) {
                    activity.set(i, activity.get(i) + 1);
                }

                previousPoints.set(i, points.get(i));
            }
        }

        return activity;
    }

    public boolean notify(AvailableCourses course) {
        if (this.hasBeenNotified.get(course.id)) return false;
        this.hasBeenNotified.set(course.id, true);
        System.out.printf("To: %s%n" +
                "Re: Your Learning Progress%n" +
                "Hello, %s %s! You have accomplished our %s course!%n", email, firstName, lastName, course.name);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Math.abs(Objects.hash(firstName, lastName, email));
    }
}
