package visualizer.elements;

import visualizer.logic.Graph;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModeLabel {
    private final Graph graph;
    private final JLabel label = new JLabel();

    public ModeLabel(JFrame frame, Graph graph) {
        this.graph = graph;
        frame.getContentPane().add(label, BorderLayout.NORTH);
    }

    public void render() {
        label.setText(String.format("Current Mode -> %s", graph.getMode()));
        label.setName("Mode");
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(5, 0, 0, 5));
    }
}
