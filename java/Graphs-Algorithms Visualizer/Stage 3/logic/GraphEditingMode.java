package visualizer.logic;

public enum GraphEditingMode {
    CREATE_VERTEX("Add a Vertex"),
    CREATE_EDGE("Add an Edge"),
    NONE("None");

    private final String description;

    GraphEditingMode(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
