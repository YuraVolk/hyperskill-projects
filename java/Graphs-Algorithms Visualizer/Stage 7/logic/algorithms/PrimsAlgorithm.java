package visualizer.logic.algorithms;

import visualizer.logic.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class PrimsAlgorithm implements NodeAlgorithm {
    private final Vertex vertex;
    private Map<Vertex, Vertex> result;

    public PrimsAlgorithm(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        if (vertex == null) return false;
        Map<Vertex, Integer> distance = new HashMap<>();
        Map<Vertex, Vertex> parent = new TreeMap<>(Comparator.comparing(Vertex::getLabel));
        for (Vertex v : vertices) {
            distance.put(v, Integer.MAX_VALUE);
            parent.put(v, null);
        }
        distance.put(vertex, 0);

        Set<Vertex> visited = new HashSet<>();
        while (visited.size() < vertices.size()) {
            Vertex currentVertex = getMinimumDistanceVertex(distance, visited);
            visited.add(currentVertex);
            if (currentVertex == null) {
                this.result = new HashMap<>();
                return true;
            }

            for (Vertex.VertexConnection connection : currentVertex.getConnectedVertices()) {
                Vertex neighbor = connection.vertex();
                int weight = connection.weight();

                if (!visited.contains(neighbor) && weight < distance.get(neighbor)) {
                    distance.put(neighbor, weight);
                    parent.put(neighbor, currentVertex);
                }
            }
        }

        this.result = parent;
        return true;
    }

    private Vertex getMinimumDistanceVertex(Map<Vertex, Integer> distance, Set<Vertex> visited) {
        Vertex minVertex = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Vertex, Integer> entry : distance.entrySet()) {
            Vertex v = entry.getKey();
            int d = entry.getValue();
            if (!visited.contains(v) && d < minDistance) {
                minVertex = v;
                minDistance = d;
            }
        }

        return minVertex;
    }

    public List<Vertex[]> getResult() {
        return result.entrySet().stream()
                .filter(e -> e.getKey() != vertex && e.getKey() != null & e.getValue() != null)
                .map(e -> new Vertex[]{ e.getKey(), e.getValue() }).toList();
    }

    public String getStringResult() {
        return result.entrySet().stream()
                .filter(e -> e.getKey() != vertex && e.getKey() != null & e.getValue() != null)
                .map(e -> String.format("%s=%s", e.getKey().getLabel(), e.getValue().getLabel())).collect(Collectors.joining(", "));
    }
}
