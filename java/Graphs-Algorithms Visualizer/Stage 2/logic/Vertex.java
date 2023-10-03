package visualizer.logic;

import java.util.Objects;

public record Vertex(int x, int y, String label) {
    public Vertex {
        Objects.requireNonNull(label);
    }
}
