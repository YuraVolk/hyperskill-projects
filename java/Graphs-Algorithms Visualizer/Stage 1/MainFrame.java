package visualizer;

import visualizer.elements.Vertex;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Graph-Algorithms Visualizer");
        setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
        setVisible(true);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#222222"));

        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(null);
        graphPanel.setName("Graph");
        graphPanel.setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
        add(graphPanel);

        Vertex vertex1 = new Vertex(0, 0, "0");
        vertex1.draw(graphPanel);
        Vertex vertex2 = new Vertex(MainFrame.WIDTH - Vertex.VERTEX_SIZE, 0, "1");
        vertex2.draw(graphPanel);
        Vertex vertex3 = new Vertex(0, MainFrame.HEIGHT - Vertex.VERTEX_SIZE, "2");
        vertex3.draw(graphPanel);
        Vertex vertex4 = new Vertex(
                MainFrame.WIDTH - Vertex.VERTEX_SIZE,
                MainFrame.HEIGHT - Vertex.VERTEX_SIZE,
                "3"
        );
        vertex4.draw(graphPanel);
    }
}
