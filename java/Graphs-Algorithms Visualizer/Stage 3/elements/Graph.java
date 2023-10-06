package visualizer.elements;

import visualizer.MainFrame;

import javax.swing.*;
import java.util.*;

public class Graph {
    private final JFrame frame;
    private final JPanel panel;
    private Vertex[] vertices = { };
    private VertexEdge[] edges = { };
    private ModeLabel label;

    {
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setName("Graph");
        this.panel.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
        this.panel.setOpaque(false);
    }

    public Graph(JFrame frame) {
        this.frame = frame;
        this.frame.add(panel);
    }

    public void setLogicGraph(visualizer.logic.Graph graph) {
        this.label = new ModeLabel(frame, graph);
    }

    public JPanel getPanel() {
        return panel;
    }

    private record VertexEdge(visualizer.logic.Vertex start, visualizer.logic.Vertex end, Integer weight) {
        String getName() {
            return String.format("Edge <%s -> %s>", start.getLabel(), end.getLabel());
        }

        String getBackwardsName() {
            return String.format("Edge <%s -> %s>", end.getLabel(), start.getLabel());
        }
    }

    public void setVertices(ArrayList<visualizer.logic.Vertex> vertices) {
        visualizer.logic.Vertex[] verticesArray = vertices.toArray(new visualizer.logic.Vertex[0]);
        this.vertices = Arrays.stream(verticesArray)
                .map(vertex -> new Vertex(vertex.getX(), vertex.getY(), vertex.getLabel()))
                .toArray(Vertex[]::new);
        this.edges = Arrays.stream(verticesArray)
                .flatMap(vertex -> vertex.getConnectedVertices()
                        .stream()
                        .map((connectedVertex) -> new VertexEdge(vertex, connectedVertex.getKey(), connectedVertex.getValue())))
                .toArray(VertexEdge[]::new);
    }

    public void render() {
        label.render();
        panel.removeAll();
        for (Vertex vertex : vertices) {
            vertex.draw(panel);
        }

        for (VertexEdge edge : edges) {
            Edge uiEdge = new Edge(edge.start, edge.end);
            uiEdge.setBounds(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
            uiEdge.setName(edge.getName());
            uiEdge.renderLabel(panel, edge.weight);
            panel.add(uiEdge);

            Edge uiEdgeBackwards = new Edge(edge.end, edge.start);
            uiEdgeBackwards.setBounds(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
            uiEdgeBackwards.setName(edge.getBackwardsName());
            panel.add(uiEdgeBackwards);
        }

        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }
}
