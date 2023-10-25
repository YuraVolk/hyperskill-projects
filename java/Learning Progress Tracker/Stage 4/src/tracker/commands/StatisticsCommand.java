package tracker.commands;

import tracker.AvailableCourses;
import tracker.LearningProgressTracker;
import tracker.Student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticsCommand implements TrackerCommand {
    public void printInitialStatistics(LearningProgressTracker tracker) {
        List<Integer> enrolledStudents = new ArrayList<>(Collections.nCopies(AvailableCourses.values().length, 0));
        List<Integer> activities = new ArrayList<>(Collections.nCopies(enrolledStudents.size(), 0));
        List<List<Integer>> currentPoints = activities.stream()
                .map(activity -> new ArrayList<Integer>())
                .collect(Collectors.toList());
        for (Student student : tracker.getStudents()) {
            List<Integer> activity = student.getCourseActivity();
            for (int i = 0; i < activities.size(); i++) {
                if (student.coursePoints.get(i) > 0) {
                    enrolledStudents.set(i, enrolledStudents.get(i) + 1);
                    currentPoints.get(i).add(student.coursePoints.get(i));
                }
                if (activity.get(i) > 0) activities.set(i, activities.get(i) + activity.get(i));
            }
        }
        List<Double> averageMarks = currentPoints.stream().map(marks -> {
            OptionalDouble average = marks.stream().mapToDouble(a -> a).average();
            return average.isPresent() ? average.getAsDouble() : 0;
        }).toList();

        Integer maxEnrolledStudents = enrolledStudents.stream().max(Integer::compareTo).orElse(0);
        Integer maxActivities = activities.stream().max(Integer::compareTo).orElse(0);
        Double maxAverageMarks = averageMarks.stream().max(Double::compareTo).orElse(0.0);
        Integer minEnrolledStudents = enrolledStudents.stream().min(Integer::compareTo).orElse(0);
        Integer minActivities = activities.stream().min(Integer::compareTo).orElse(0);
        Double minAverageMarks = averageMarks.stream().min(Double::compareTo).orElse(0.0);
        List<String> mostPopularCourses = maxEnrolledStudents == 0 ? Collections.singletonList("n/a") : IntStream.range(0, enrolledStudents.size())
                .filter(i -> enrolledStudents.get(i) >= maxEnrolledStudents).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList();
        List<String> mostActiveCourses = maxActivities == 0 ? Collections.singletonList("n/a") : IntStream.range(0, activities.size())
                .filter(i -> activities.get(i) >= maxActivities).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList();
        List<String> easiestCourses = new ArrayList<>(maxAverageMarks == 0 ? Collections.singletonList("n/a") : IntStream.range(0, averageMarks.size())
                .filter(i -> averageMarks.get(i) >= maxAverageMarks).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList());
        List<String> leastPopularCourses = new ArrayList<>(minEnrolledStudents == 0 ? Collections.singletonList("n/a") : IntStream.range(0, enrolledStudents.size())
                .filter(i -> enrolledStudents.get(i) <= minEnrolledStudents).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList());
        List<String> leastActiveCourses = new ArrayList<>(minActivities == 0 ? Collections.singletonList("n/a") : IntStream.range(0, activities.size())
                .filter(i -> activities.get(i) <= minActivities).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList());
        List<String> hardestCourses = minAverageMarks == 0 ? Collections.singletonList("n/a") : IntStream.range(0, averageMarks.size())
                .filter(i -> averageMarks.get(i) <= minAverageMarks).boxed()
                .map(i -> Arrays.stream(AvailableCourses.values()).filter(val -> val.id == i).findFirst().orElseThrow().name).toList();
        leastPopularCourses.removeAll(mostPopularCourses);
        if (leastPopularCourses.isEmpty()) leastPopularCourses.add("n/a");
        leastActiveCourses.removeAll(mostActiveCourses);
        if (leastActiveCourses.isEmpty()) leastActiveCourses.add("n/a");
        easiestCourses.removeAll(hardestCourses);
        if (easiestCourses.isEmpty()) easiestCourses.add("n/a");

        System.out.printf("Most popular: %s%nLeast popular: %s%n", String.join(", ", mostPopularCourses), String.join(", ", leastPopularCourses));
        System.out.printf("Highest activity: %s%nLowest activity: %s%n", String.join(", ", mostActiveCourses), String.join(", ", leastActiveCourses));
        System.out.printf("Easiest course: %s%nHardest course: %s%n", String.join(", ", easiestCourses), String.join(", ", hardestCourses));
    }

    @Override
    public void executeOnTracker(LearningProgressTracker tracker) {
        System.out.printf("Type the name of a course to see details or '%s' to quit:%n", TrackerCommand.STOP_MESSAGE);
        printInitialStatistics(tracker);
        while (true) {
            String input = LearningProgressTracker.scanner.nextLine().toLowerCase();
            if (input.equals(TrackerCommand.STOP_MESSAGE)) break;
            Optional<AvailableCourses> course = Arrays.stream(AvailableCourses.values()).filter(queried -> queried.name.toLowerCase().equals(input)).findAny();
            if (course.isEmpty()) {
                System.out.println("Unknown course.");
            } else {
                AvailableCourses foundCourse = course.get();
                System.out.println(foundCourse.name);
                System.out.println("id     points completed");
                List<Student> students = new ArrayList<>(tracker.getStudents().stream().filter(student -> student.coursePoints.get(foundCourse.id) > 0).toList());
                students.sort((a, b) -> {
                    if (Objects.equals(a.coursePoints.get(foundCourse.id), b.coursePoints.get(foundCourse.id))) {
                        return Integer.compare(b.hashCode(), a.hashCode());
                    } else return Integer.compare(b.coursePoints.get(foundCourse.id), a.coursePoints.get(foundCourse.id));
                });

                students.forEach(student -> {
                    double completionPercentage = (double) student.coursePoints.get(foundCourse.id) / foundCourse.targetPoints * 100;
                    BigDecimal roundedPercentage = new BigDecimal(completionPercentage).setScale(1, RoundingMode.HALF_UP);
                    System.out.printf("%s %s %s%%%n", student.hashCode(), student.coursePoints.get(foundCourse.id), roundedPercentage);
                });
            }
        }
    }
}
