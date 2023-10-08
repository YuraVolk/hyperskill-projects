package visualizer.logic;

public enum GraphEditingMode {
    DEPTH_FIRST_SEARCH("Depth-First Search", Category.ALGORITHM),
    BREADTH_FIRST_SEARCH("Breadth-First Search", Category.ALGORITHM),
    DIJKSTRA_ALGORITHM("Dijkstra's Algorithm", Category.ALGORITHM),

    CREATE_VERTEX("Add a Vertex", Category.MODE),
    CREATE_EDGE("Add an Edge", Category.MODE),
    REMOVE_VERTEX("Remove a Vertex", Category.MODE),
    REMOVE_EDGE("Remove an Edge", Category.MODE),
    NONE("None", Category.MODE);

    private final String description;
    private final Category category;

    GraphEditingMode(String description, Category category) {
        this.description = description;
        this.category = category;
    }

    public String getTitle() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public enum Category { MODE, ALGORITHM }
}
