package visualizer.events.panes;

import visualizer.logic.Graph;
import javax.swing.*;

public class CreateVertexPane implements OptionsPane {
    final int x;
    final int y;

    public CreateVertexPane(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean checkInputErrors(String input) {
        return input.length() != 1 || input.isBlank();
    }

    @Override
    public void openPane(Graph graph) {
        if (!graph.canCreateVertex(this.x, this.y)) return;
        String output = validatePaneInput(() -> JOptionPane.showInputDialog(
                null,
                "Enter the Vertex ID (Should be 1 char):",
                "Vertex",
                JOptionPane.QUESTION_MESSAGE
        ));
        if (output == null) return;

        graph.createNewVertex(this.x, this.y, output);
        graph.render();
    }
}
