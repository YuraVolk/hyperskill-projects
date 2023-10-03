package visualizer.logic;

import java.util.ArrayList;

public class Graph {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final visualizer.elements.Graph uiGraph;

    public Graph(visualizer.elements.Graph uiGraph) {
        this.uiGraph = uiGraph;
    }

    public void createNewVertex(int x, int y, String label) {
        vertices.add(
                new Vertex(
                    x - (visualizer.elements.Vertex.VERTEX_SIZE / 2),
                    y - (visualizer.elements.Vertex.VERTEX_SIZE / 2),
                    label
                )
        );
    }

    public void renderVertices() {
        this.uiGraph.setVertices(this.vertices);
        this.uiGraph.render();
    }
}
