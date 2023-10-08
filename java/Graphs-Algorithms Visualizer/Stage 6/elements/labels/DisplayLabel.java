package visualizer.elements.labels;

import visualizer.logic.Graph;

import javax.swing.*;

public abstract class DisplayLabel {
    protected final JLabel label = new JLabel();
    protected Graph graph;

    public void setDependencies(JFrame frame, Graph graph) {
        this.graph = graph;
        this.initialize(frame);
    }

    protected abstract void initialize(JFrame frame);

    public abstract void render();
}
