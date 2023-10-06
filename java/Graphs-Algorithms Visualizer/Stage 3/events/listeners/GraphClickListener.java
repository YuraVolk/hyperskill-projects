package visualizer.events.listeners;

import visualizer.events.panes.CreateEdgePane;
import visualizer.events.panes.CreateVertexPane;
import visualizer.logic.Graph;
import visualizer.logic.GraphEditingMode;
import visualizer.logic.Vertex;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class GraphClickListener implements Listener<JPanel> {
    private static final class PanelListener implements MouseListener {
        private final Graph graph;
        private Vertex clickedVertex;

        private PanelListener(Graph graph) {
            this.graph = graph;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Object source = mouseEvent.getSource();
            if (source instanceof JPanel) {
                if (this.graph.getMode() != GraphEditingMode.CREATE_EDGE) this.clickedVertex = null;

                switch (this.graph.getMode()) {
                    case CREATE_VERTEX -> {
                        CreateVertexPane pane = new CreateVertexPane(mouseEvent.getX(), mouseEvent.getY());
                        pane.openPane(this.graph);
                    }

                    case CREATE_EDGE -> {
                        if (clickedVertex == null) {
                            clickedVertex = this.graph.getClosestVertex(mouseEvent.getX(), mouseEvent.getY());
                        } else {
                            Vertex secondVertex = this.graph.getClosestVertex(mouseEvent.getX(), mouseEvent.getY());
                            if (secondVertex == null || clickedVertex == secondVertex) break;
                            System.out.println("-------------");
                            System.out.println(clickedVertex.getLabel());
                            System.out.println(secondVertex.getLabel());
                            System.out.println("-------------");
                            CreateEdgePane pane = new CreateEdgePane(clickedVertex, secondVertex);
                            clickedVertex = null;
                            pane.openPane(this.graph);
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (PanelListener) obj;
            return Objects.equals(this.graph, that.graph);
        }

        @Override
        public int hashCode() {
            return Objects.hash(graph);
        }
    }
    @Override
    public void configureListener(Graph graph, JPanel frame) {
        frame.addMouseListener(new PanelListener(graph));
    }
}
