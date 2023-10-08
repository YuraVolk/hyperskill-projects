package visualizer.logic.algorithms;

import visualizer.logic.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DepthFirstSearchAlgorithm implements NodeAlgorithm {
    private final Vertex vertex;
    private List<Vertex> result;

    public DepthFirstSearchAlgorithm(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        if (this.vertex == null) return false;
        Set<Vertex> visited = new HashSet<>();
        result = new ArrayList<>();
        depthFirstSearch(vertex, visited);
        return true;
    }

    private void depthFirstSearch(Vertex currentVertex, Set<Vertex> visited) {
        visited.add(currentVertex);
        result.add(currentVertex);

        List<Vertex.VertexConnection> currentVertices = new ArrayList<>(currentVertex.getConnectedVertices());
        for (Vertex.VertexConnection connection : currentVertices) {
            Vertex connectedVertex = connection.vertex();
            if (!visited.contains(connectedVertex)) {
                depthFirstSearch(connectedVertex, visited);
            }
        }
    }

    public List<Vertex> getResult() {
        return result;
    }

    public String getStringResult() {
        return String.format("DFS : %s", result.stream().map(Vertex::getLabel).collect(Collectors.joining(" -> ")));
    }
}
