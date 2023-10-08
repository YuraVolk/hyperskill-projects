package visualizer.elements.labels;

import visualizer.logic.GraphEditingMode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModeLabel extends DisplayLabel {
    @Override
    protected void initialize(JFrame frame) {
        frame.getContentPane().add(label, BorderLayout.NORTH);
        label.setName("Mode");
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(5, 0, 0, 5));
    }

    @Override
    public void render() {
        label.setText(String.format("Current Mode -> %s",
                graph.getMode().getCategory() == GraphEditingMode.Category.ALGORITHM
                        ? GraphEditingMode.NONE.getTitle()
                        : graph.getMode().getTitle())
        );
    }
}
