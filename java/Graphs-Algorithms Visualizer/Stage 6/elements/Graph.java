package visualizer.elements;

import visualizer.MainFrame;
import visualizer.elements.labels.AlgorithmLabel;
import visualizer.elements.labels.DisplayLabel;
import visualizer.elements.labels.ModeLabel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph {
    public final static Color ACTIVE_COLOR = Color.YELLOW;
    private final JFrame frame;
    private final JPanel panel;
    private Vertex[] vertices = { };
    private VertexEdge[] edges = { };

    private final List<Vertex> highlightedVertices = new ArrayList<>();
    private final Set<DisplayLabel> labels = new HashSet<>();

    {
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setName("Graph");
        this.panel.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
        this.panel.setOpaque(false);
        labels.add(new AlgorithmLabel());
        labels.add(new ModeLabel());
    }

    public Graph(JFrame frame) {
        this.frame = frame;
        this.frame.add(panel);
    }

    public void setLogicGraph(visualizer.logic.Graph graph) {
        labels.forEach((label) -> label.setDependencies(frame, graph));
    }

    public JPanel getPanel() {
        return panel;
    }

    private record VertexEdge(visualizer.logic.Vertex start, visualizer.logic.Vertex end, Integer weight, boolean createsLabel) {
        String getName() {
            return String.format("Edge <%s -> %s>", start.getLabel(), end.getLabel());
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
                        .map((connectedVertex) -> new VertexEdge(
                                vertex, connectedVertex.vertex(),
                                connectedVertex.weight(), connectedVertex.createsLabel())))
                .toArray(VertexEdge[]::new);
    }

    public void highlightVertex(visualizer.logic.Vertex vertex) {
        this.highlightedVertices.add(Arrays.stream(this.vertices)
                .filter(v -> v.equalsToLogicVertex(vertex))
                .findFirst()
                .orElseThrow());
    }

    public void clearHighlightedVertices() {
        highlightedVertices.clear();
    }

    public void render() {
        panel.removeAll();
        labels.forEach(DisplayLabel::render);
        for (Vertex vertex : vertices) {
            vertex.draw(panel, highlightedVertices);
        }

        for (VertexEdge edge : edges) {
            Edge uiEdge = new Edge(edge.start, edge.end, highlightedVertices);
            uiEdge.setBounds(Edge.getCalculatedBounds(edge.start, edge.end));
            uiEdge.setName(edge.getName());
            if (edge.createsLabel) {
                uiEdge.renderLabel(panel, edge.weight);
            }
            panel.add(uiEdge);
        }

        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }
}
