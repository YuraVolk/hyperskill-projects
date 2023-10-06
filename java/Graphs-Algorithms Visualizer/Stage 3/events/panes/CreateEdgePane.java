package visualizer.events.panes;

import visualizer.logic.Graph;
import visualizer.logic.Vertex;

import javax.swing.*;

public class CreateEdgePane implements OptionsPane {
    private final Vertex startVertex;
    private final Vertex endVertex;

    public CreateEdgePane(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    @Override
    public boolean checkInputErrors(String input) {
        return !input.matches("^-?\\d$");
    }

    @Override
    public void openPane(Graph graph) {
        String output = validatePaneInput(() -> JOptionPane.showInputDialog(
                null,
                "Enter Weight",
                "Input",
                JOptionPane.QUESTION_MESSAGE
        ));
        if (output == null) return;

        graph.connectTwoVertices(this.startVertex, this.endVertex, Integer.parseInt(output));
        graph.render();
    }
}
