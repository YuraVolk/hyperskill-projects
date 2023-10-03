package visualizer.events.listeners;

import visualizer.events.panes.CreateVertexPane;
import visualizer.logic.Graph;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphClickListener implements Listener<JPanel> {
    private record PanelListener(Graph graph) implements MouseListener {
        @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Object source = mouseEvent.getSource();
                if (source instanceof JPanel) {
                    CreateVertexPane pane = new CreateVertexPane(mouseEvent.getX(), mouseEvent.getY());
                    pane.openPane(this.graph);
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
        }

    @Override
    public void configureListener(Graph graph, JPanel frame) {
        frame.addMouseListener(new PanelListener(graph));
    }
}
