package visualizer.events;

import visualizer.elements.Graph;
import visualizer.logic.Vertex;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VertexTimer {
    private final static int DELAY = 1000;
    private final Graph graph;
    private final Runnable terminateExecution;
    private final Timer timer;
    private final VertexTimerListener listener = new VertexTimerListener();

    public VertexTimer(Graph graph, Runnable terminateExecution) {
        this.graph = graph;
        this.terminateExecution = terminateExecution;
        this.timer = new Timer(DELAY, listener);
        timer.setInitialDelay(DELAY);
    }

    private class VertexTimerListener implements ActionListener {
        private Queue<Vertex> vertices;

        public void setVertices(List<Vertex> vertices) {
            this.vertices = new LinkedList<>(vertices);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Vertex vertex = vertices.poll();
            if (vertex == null) {
                timer.stop();
                VertexTimer.this.terminateExecution.run();
            } else {
                VertexTimer.this.graph.highlightVertex(vertex);
                VertexTimer.this.graph.render();
            }

            VertexTimer.this.graph.render();
        }
    }

    public void start(List<Vertex> vertices) {
        listener.setVertices(vertices);
        timer.start();
    }
}
