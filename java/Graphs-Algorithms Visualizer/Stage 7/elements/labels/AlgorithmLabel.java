package visualizer.elements.labels;

import visualizer.MainFrame;
import visualizer.logic.GraphEditingMode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlgorithmLabel extends DisplayLabel {
    private final JPanel panel = new JPanel();

    @Override
    protected void initialize(JFrame frame) {
        panel.add(label);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        label.setName("Display");
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(3, 7, 3, 7));
    }

    @Override
    public void render() {
        if (graph.getMode().getCategory() == GraphEditingMode.Category.ALGORITHM || graph.getGraphResult() != null) {
            panel.setVisible(true);
            if (graph.isAlgorithmExecuting()) {
                label.setText("Please wait...");
            } else {
                label.setText(graph.getGraphResult() == null ? "Please choose a starting vertex": graph.getGraphResult());
            }
        } else {
            label.setText("");
            panel.setVisible(false);
        }
    }
}
