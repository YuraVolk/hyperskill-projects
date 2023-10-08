package visualizer.logic.algorithms;

import visualizer.logic.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstrasAlgorithm implements NodeAlgorithm {
    private final Vertex vertex;
    private Map<Vertex, Vertex[]> paths;
    private Map<Vertex, Integer> result;

    public DijkstrasAlgorithm(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        if (this.vertex == null) return false;
        Map<Vertex, Integer> distance = new TreeMap<>(Comparator.comparing(Vertex::getLabel));
        Map<Vertex, Vertex[]> paths = new HashMap<>();
        for (Vertex vertex : vertices) {
            distance.put(vertex, Integer.MAX_VALUE);
            paths.put(vertex, new Vertex[]{ vertex });
        }
        distance.put(vertex, 0);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(vertex);
        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            for (Vertex.VertexConnection connection : currentVertex.getConnectedVertices()) {
                Vertex connectedVertex = connection.vertex();
                int newDistance = distance.get(currentVertex) + connection.weight();
                if (newDistance < distance.get(connectedVertex)) {
                    Vertex[] pathToConnectedVertex = Arrays.copyOf(paths.get(currentVertex), paths.get(currentVertex).length + 1);
                    pathToConnectedVertex[pathToConnectedVertex.length - 1] = connectedVertex;
                    paths.put(connectedVertex, pathToConnectedVertex);

                    distance.put(connectedVertex, newDistance);
                    queue.add(connectedVertex);
                }
            }
        }

        this.paths = paths;
        this.result = distance;
        return true;
    }

    public String getStringResult() {
        return result.entrySet().stream()
                .filter(e -> e.getKey() != vertex)
                .map(e -> String.format("%s=%s", e.getKey().getLabel(), e.getValue())).collect(Collectors.joining(", "));
    }

    public List<Vertex[]> getResult() {
        return paths.values().stream().toList();
    }
}
