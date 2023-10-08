package visualizer.events.timers;

import visualizer.elements.Graph;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class VisualizationTimer<T> {
    protected final static int DELAY = 1000;
    protected final Graph graph;
    protected final Runnable terminateExecution;
    protected Timer timer;

    protected interface TimerListener<T> extends ActionListener {
        void setVertices(List<T> vertices);
    }

    public VisualizationTimer(Graph graph, Runnable terminateExecution) {
        this.graph = graph;
        this.terminateExecution = terminateExecution;
    }

    protected abstract TimerListener<T> getListener();
    public void start(List<T> vertices) {
        this.timer = new Timer(DELAY, this.getListener());
        timer.setInitialDelay(DELAY);
        getListener().setVertices(vertices);
        timer.start();
    }
}
