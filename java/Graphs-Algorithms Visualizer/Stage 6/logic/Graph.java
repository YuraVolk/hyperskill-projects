package visualizer.logic;

import visualizer.events.timers.VertexChainTimer;
import visualizer.events.timers.VertexTimer;
import visualizer.logic.algorithms.*;

import java.util.ArrayList;

import static visualizer.elements.Vertex.HALF_VERTEX_SIZE;
import static visualizer.elements.Vertex.VERTEX_SIZE;

public class Graph {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final visualizer.elements.Graph uiGraph;
    private GraphEditingMode mode = GraphEditingMode.CREATE_VERTEX;
    private boolean algorithmExecuting = false;
    private String graphResult = null;

    public Graph(visualizer.elements.Graph uiGraph) {
        this.uiGraph = uiGraph;
    }

    public void createNewVertex(int x, int y, String label) {
        vertices.add(new Vertex(x - (VERTEX_SIZE / 2), y - (VERTEX_SIZE / 2), label));
    }

    public void render() {
        this.uiGraph.setVertices(this.vertices);
        this.uiGraph.render();
    }

    public GraphEditingMode getMode() {
        return mode;
    }

    public void setMode(GraphEditingMode mode) {
        if (mode == this.mode || algorithmExecuting) return;
        this.graphResult = null;
        this.mode = mode;
        this.render();
    }

    public boolean canCreateVertex(int x, int y) {
        return mode == GraphEditingMode.CREATE_VERTEX && vertices.stream().noneMatch((v) -> v.getX() == x - HALF_VERTEX_SIZE && v.getY() == y - HALF_VERTEX_SIZE);
    }

    public Vertex getClosestVertex(int x, int y) {
        Vertex closestVertex = null;
        double closestDistance = Double.MAX_VALUE;
        for (Vertex vertex : vertices) {
            double distance = Math.sqrt(Math.pow(Math.abs(vertex.getX() + HALF_VERTEX_SIZE - x), 2) + Math.pow(Math.abs(vertex.getY() + HALF_VERTEX_SIZE - y), 2));
            if (distance <= HALF_VERTEX_SIZE && distance < closestDistance) {
                closestVertex = vertex;
                closestDistance = distance;
            }
        }

        return closestVertex;
    }

    public void connectTwoVertices(Vertex a, Vertex b, Integer weight) {
        a.connectToVertex(b, weight, true);
        b.connectToVertex(a, weight);
    }

    public void removeEdge(int x, int y) {
        if (new RemoveEdgeAlgorithm(x, y).execute(vertices)) {
            this.render();
        }
    }

    public void removeVertex(int x, int y) {
        if (new RemoveVertexAlgorithm(this.getClosestVertex(x, y)).execute(vertices)) {
            this.render();
        }
    }

    public void resetGraph() {
        this.vertices.clear();
        this.render();
    }

    public boolean isAlgorithmExecuting() {
        return algorithmExecuting;
    }

    private void stopExecution(String result) {
        uiGraph.clearHighlightedVertices();
        algorithmExecuting = false;
        this.mode = GraphEditingMode.NONE;
        this.graphResult = result;
        uiGraph.render();
    }

    public void breadthFirstSearch(int x, int y) {
        algorithmExecuting = true;
        uiGraph.render();
        var algorithm = new BreadthFirstSearchAlgorithm(this.getClosestVertex(x, y));
        if (!algorithm.execute(this.vertices)) {
            algorithmExecuting = false;
        } else new VertexTimer(uiGraph, () -> stopExecution(algorithm.getStringResult())).start(algorithm.getResult());
    }

    public void depthFirstSearch(int x, int y) {
        algorithmExecuting = true;
        uiGraph.render();
        var algorithm = new DepthFirstSearchAlgorithm(this.getClosestVertex(x, y));
        if (!algorithm.execute(this.vertices)) {
            algorithmExecuting = false;
        } else new VertexTimer(uiGraph, () -> stopExecution(algorithm.getStringResult())).start(algorithm.getResult());
    }

    public void dijkstraAlgorithm(int x, int y) {
        algorithmExecuting = true;
        uiGraph.render();
        var algorithm = new DijkstrasAlgorithm(this.getClosestVertex(x, y));
        if (!algorithm.execute(this.vertices)) {
            algorithmExecuting = false;
        } else new VertexChainTimer(uiGraph, () -> stopExecution(algorithm.getStringResult())).start(algorithm.getResult());
    }

    public String getGraphResult() {
        return graphResult;
    }
}
