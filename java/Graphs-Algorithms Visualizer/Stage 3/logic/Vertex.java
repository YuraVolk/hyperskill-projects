package visualizer.logic;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Vertex {
    private final int x;
    private final int y;
    private final String label;
    private final List<AbstractMap.SimpleEntry<Vertex, Integer>> connectedVertices = new ArrayList<>();

    public Vertex(int x, int y, String label) {
        Objects.requireNonNull(label);
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public List<AbstractMap.SimpleEntry<Vertex, Integer>> getConnectedVertices() {
        return this.connectedVertices;
    }

    public void connectToVertex(Vertex vertex, Integer weight) {
        if (connectedVertices.stream().noneMatch((e) -> e.getKey() == vertex)) {
            connectedVertices.add(new AbstractMap.SimpleEntry<>(vertex, weight));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vertex) obj;
        return this.x == that.x &&
                this.y == that.y &&
                Objects.equals(this.label, that.label) &&
                Objects.equals(this.connectedVertices, that.connectedVertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, label);
    }
}
