package visualizer.elements;

import visualizer.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    private final JFrame frame;
    private final JPanel panel;
    private Vertex[] vertices = { };

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

    public JPanel getPanel() {
        return panel;
    }

    public void setVertices(ArrayList<visualizer.logic.Vertex> vertices) {
        this.vertices = Arrays.stream(vertices.toArray(new visualizer.logic.Vertex[0]))
                .map(vertex -> new Vertex(vertex.x(), vertex.y(), vertex.label()))
                .toArray(Vertex[]::new);
    }

    public void render() {
        panel.removeAll();
        for (Vertex vertex : vertices) {
            vertex.draw(panel);
        }
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }
}
