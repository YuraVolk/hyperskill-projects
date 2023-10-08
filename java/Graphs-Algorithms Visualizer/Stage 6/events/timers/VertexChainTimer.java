package visualizer.events.timers;

import visualizer.elements.Graph;
import visualizer.logic.Vertex;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VertexChainTimer extends VisualizationTimer<Vertex[]> {
    private final VertexTimerListener listener = new VertexTimerListener();

    public VertexChainTimer(Graph graph, Runnable terminateExecution) {
        super(graph, terminateExecution);
    }

    private class VertexTimerListener implements VisualizationTimer.TimerListener<Vertex[]> {
        private Queue<Vertex[]> vertices;

        public void setVertices(List<Vertex[]> vertices) {
            this.vertices = new LinkedList<>(vertices);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            VertexChainTimer.this.graph.clearHighlightedVertices();
            Vertex[] vertexList = vertices.poll();
            if (vertexList == null) {
                timer.stop();
                VertexChainTimer.this.terminateExecution.run();
            } else {
                for (Vertex vertex : vertexList) VertexChainTimer.this.graph.highlightVertex(vertex);
            }

            VertexChainTimer.this.graph.render();
        }
    }

    @Override
    protected VisualizationTimer.TimerListener<Vertex[]> getListener() {
        return listener;
    }
}
