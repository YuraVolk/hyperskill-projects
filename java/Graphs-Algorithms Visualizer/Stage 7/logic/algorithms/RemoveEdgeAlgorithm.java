package visualizer.logic.algorithms;

import visualizer.elements.Edge;
import visualizer.logic.Vertex;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RemoveEdgeAlgorithm implements NodeAlgorithm {
    private final int x;
    private final int y;

    public RemoveEdgeAlgorithm(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(List<Vertex> vertices) {
        var connection = this.getConnectedEdge(vertices);
        if (connection == null) return false;
        Vertex start = connection.getKey();
        Vertex.VertexConnection edge = connection.getValue();
        start.getConnectedVertices().remove(edge);

        Vertex end = edge.vertex();
        for (Vertex.VertexConnection connectedEdge : end.getConnectedVertices()) {
            if (connectedEdge.vertex().equals(start)) {
                end.getConnectedVertices().remove(connectedEdge);
                break;
            }
        }
        return true;
    }

    private AbstractMap.SimpleEntry<Vertex, Vertex.VertexConnection> getConnectedEdge(List<Vertex> vertices) {
        double maxDistance = Double.MAX_VALUE;
        AbstractMap.SimpleEntry<Vertex, Vertex.VertexConnection> connectionSimpleEntry = null;

        for (Vertex vertex : vertices) {
            for (Vertex.VertexConnection connection : vertex.getConnectedVertices()) {
                Vertex end = connection.vertex();
                double distance = calculateDistance(x, y, vertex.getX(), vertex.getY(), end.getX(), end.getY());
                Rectangle bounds = Edge.getCalculatedBounds(vertex, connection.vertex());
                double distanceToRoot = calculateDistance(x, y, bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
                if (distance <= Edge.EDGE_SIZE * 2 && distance < maxDistance) {
                    maxDistance = distance;
                    connectionSimpleEntry = new AbstractMap.SimpleEntry<>(vertex, connection);
                } else if (distanceToRoot == 0.0) {
                    return new AbstractMap.SimpleEntry<>(vertex, connection);
                }
            }
        }

        return connectionSimpleEntry;
    }

    private double calculateDistance(int x, int y, int startX, int startY, int endX, int endY) {
        double dx = endX - startX;
        double dy = endY - startY;
        double length = Math.sqrt(dx * dx + dy * dy);
        double u = ((x - startX) * dx + (y - startY) * dy) / (length * length);

        if (u < 0) {
            return distance(x, y, startX, startY);
        } else if (u > 1) {
            return distance(x, y, endX, endY);
        }

        double intersectionX = startX + u * dx;
        double intersectionY = startY + u * dy;
        return distance(x, y, intersectionX, intersectionY);
    }

    private double distance(int x1, int y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
