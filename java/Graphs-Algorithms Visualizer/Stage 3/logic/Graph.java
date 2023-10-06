package visualizer.logic;

import java.util.ArrayList;

public class Graph {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final visualizer.elements.Graph uiGraph;
    private GraphEditingMode mode = GraphEditingMode.CREATE_VERTEX;
    private final static int HALF_VERTEX_SIZE = visualizer.elements.Vertex.VERTEX_SIZE / 2;

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

    public void render() {
        this.uiGraph.setVertices(this.vertices);
        this.uiGraph.render();
    }

    public GraphEditingMode getMode() {
        return mode;
    }

    public void setMode(GraphEditingMode mode) {
        if (mode == this.mode) return;
        this.mode = mode;
        this.render();
    }

    public boolean canCreateVertex(int x, int y) {
        return mode == GraphEditingMode.CREATE_VERTEX && vertices.stream().noneMatch((v) -> v.getX() == x - HALF_VERTEX_SIZE && v.getY() == y - HALF_VERTEX_SIZE);
    }

    public Vertex getClosestVertex(int x, int y) {
        Vertex closestVertex = null;
        double closestDistance = Double.MAX_VALUE;
        final int maximumDistance = visualizer.elements.Vertex.VERTEX_SIZE / 2;

        for (Vertex vertex : vertices) {
            double distanceX = Math.pow(Math.abs(vertex.getX() + maximumDistance - x), 2);
            double distanceY = Math.pow(Math.abs(vertex.getY() + maximumDistance - y), 2);
            if (distanceX > maximumDistance || distanceY > maximumDistance) continue;
            double distance = Math.sqrt(distanceX + distanceY);
            if (distance <= maximumDistance && distance < closestDistance) {
                closestVertex = vertex;
                closestDistance = distance;
            }
        }

        return closestVertex;
    }

    public void connectTwoVertices(Vertex a, Vertex b, Integer weight) {
        a.connectToVertex(b, weight);
    }
}
