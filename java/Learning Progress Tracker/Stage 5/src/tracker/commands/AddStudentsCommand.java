package tracker.commands;

import tracker.LearningProgressTracker;
import tracker.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddStudentsCommand implements TrackerCommand {
    public Student validateStudent(String student) {
        String[] stringParts = student.split(" ");
        if (stringParts.length < 3) {
            System.out.println("Incorrect credentials.");
        }  else {
            String regex = "^[a-zA-Z](?:(?!['-]{2,})[a-zA-Z'-])*[a-zA-Z]$";
            List<String> lastNameParts = new ArrayList<>(Arrays.asList(stringParts));
            lastNameParts.remove(0);
            lastNameParts.remove(lastNameParts.size() - 1);

            if (!stringParts[0].matches(regex)) {
                System.out.println("Incorrect first name.");
            } else if (lastNameParts.stream().anyMatch(part -> !part.matches(regex))) {
                System.out.println("Incorrect last name.");
            } else if (!stringParts[stringParts.length - 1].matches("^[^@]+@[^@]+\\.[^@]+$")) {
                System.out.println("Incorrect email.");
            } else return new Student(stringParts[0], String.join(" ", lastNameParts), stringParts[stringParts.length - 1]);
        }

        return null;
    }

    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        System.out.printf("Enter student credentials or '%s' to return:%n", TrackerCommand.STOP_MESSAGE);
        int addedStudents = 0;
        while (true) {
            String input = LearningProgressTracker.scanner.nextLine();
            if (Objects.equals(input, TrackerCommand.STOP_MESSAGE)) break;
            Student student = validateStudent(input);
            if (student == null) continue;
            if (tracker.getStudents().stream().anyMatch(queried -> queried.email.equals(student.email))) {
                System.out.println("This email is already taken.");
                continue;
            }
            System.out.println("The student has been added.");
            addedStudents++;
            tracker.getStudents().add(student);
        }

        System.out.printf("Total %s students have been added.%n", addedStudents);
    }
}
