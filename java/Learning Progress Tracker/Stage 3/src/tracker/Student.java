package tracker;

import java.util.*;

public record Student(String firstName, String lastName, String email, List<Integer> coursePoints) {
    public Student(String firstName, String lastName, String email) {
        this(firstName, lastName, email, new ArrayList<>(Collections.nCopies(AvailableCourses.values().length, 0)));
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
