package tracker;

public enum AvailableCourses {
    JAVA("Java", 0),
    DATA_STRUCTURES_AND_ALGORITHMS("DSA", 1),
    DATABASES("Databases", 2),
    SPRING("Spring", 3);

    public String name;
    public int id;

    AvailableCourses(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
