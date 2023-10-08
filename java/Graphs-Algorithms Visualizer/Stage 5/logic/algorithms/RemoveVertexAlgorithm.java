package visualizer.logic.algorithms;

import visualizer.logic.Vertex;

import java.util.List;

public class RemoveVertexAlgorithm implements NodeAlgorithm {
    private final Vertex vertex;

    public RemoveVertexAlgorithm(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        if (vertex == null) return false;
        vertices.remove(vertex);

        for (Vertex iteratedVertex : vertices) {
            if (vertex == iteratedVertex) continue;
            for (var connection : iteratedVertex.getConnectedVertices()) {
                if (connection.vertex().equals(vertex)) {
                    iteratedVertex.getConnectedVertices().remove(connection);
                    break;
                }
            }
        }
        return true;
    }
}
