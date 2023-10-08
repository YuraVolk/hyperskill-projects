package visualizer.logic.algorithms;

import visualizer.logic.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class BreadthFirstSearchAlgorithm implements NodeAlgorithm {
    private final Vertex vertex;
    private List<Vertex> result;

    public BreadthFirstSearchAlgorithm(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        if (this.vertex == null) return false;
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        List<Vertex> result = new ArrayList<>();
        queue.add(vertex);
        visited.add(vertex);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            result.add(currentVertex);

            List<Vertex.VertexConnection> currentVertices = new ArrayList<>(currentVertex.getConnectedVertices());
            currentVertices.sort(Comparator.comparingInt(Vertex.VertexConnection::weight));
            for (Vertex.VertexConnection connection : currentVertices) {
                Vertex connectedVertex = connection.vertex();
                if (!visited.contains(connectedVertex)) {
                    queue.add(connectedVertex);
                    visited.add(connectedVertex);
                }
            }
        }

        this.result = result;
        return true;
    }

    public List<Vertex> getResult() {
        return result;
    }

    public String getStringResult() {
        return String.format("BFS : %s", result.stream().map(Vertex::getLabel).collect(Collectors.joining(" -> ")));
    }
}
