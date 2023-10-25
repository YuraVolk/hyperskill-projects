package tracker;

public enum AvailableCourses {
    JAVA("Java", 0, 600),
    DATA_STRUCTURES_AND_ALGORITHMS("DSA", 1, 400),
    DATABASES("Databases", 2, 480),
    SPRING("Spring", 3, 550);

    public final String name;
    public final int id;
    public final int targetPoints;

    AvailableCourses(String name, int id, int targetPoints) {
        this.name = name;
        this.id = id;
        this.targetPoints = targetPoints;
    }
}
