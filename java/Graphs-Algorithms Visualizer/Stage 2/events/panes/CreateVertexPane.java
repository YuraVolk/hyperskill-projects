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

    private boolean validateInput(String input) {
        return input.length() == 1 && !input.isBlank();
    }

    @Override
    public void openPane(Graph graph) {
        String output;
        do {
            output = JOptionPane.showInputDialog(
                    null,
                    "Enter the Vertex ID (Should be 1 char):",
                    "Vertex",
                    JOptionPane.QUESTION_MESSAGE
            );
            if (output == null) return;
        } while(!validateInput(output));

        graph.createNewVertex(this.x, this.y, output);
        graph.renderVertices();
    }
}
