package visualizer.events.timers;

import visualizer.elements.Graph;
import visualizer.logic.Vertex;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VertexTimer extends VisualizationTimer<Vertex> {
    private final VisualizationTimer.TimerListener<Vertex> listener = new VertexTimerListener();

    public VertexTimer(Graph graph, Runnable terminateExecution) {
        super(graph, terminateExecution);
    }

    private class VertexTimerListener implements VisualizationTimer.TimerListener<Vertex> {
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
            }

            VertexTimer.this.graph.render();
        }
    }

    @Override
    protected VisualizationTimer.TimerListener<Vertex> getListener() {
        return this.listener;
    }
}
