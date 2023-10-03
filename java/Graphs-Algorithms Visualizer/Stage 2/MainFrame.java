package visualizer;

import visualizer.elements.Graph;
import visualizer.events.listeners.GraphClickListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Graph-Algorithms Visualizer");
        setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#222222"));

        Graph uiGraph = new Graph(this);
        visualizer.logic.Graph logicGraph = new visualizer.logic.Graph(uiGraph);
        new GraphClickListener().configureListener(logicGraph, uiGraph.getPanel());
        logicGraph.renderVertices();
    }
}
