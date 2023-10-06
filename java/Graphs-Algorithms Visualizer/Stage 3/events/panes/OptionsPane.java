package visualizer.events.panes;

import visualizer.logic.Graph;

import java.util.function.Supplier;

public interface OptionsPane {
    boolean checkInputErrors(String input);
    void openPane(Graph graph);

    default String validatePaneInput(Supplier<String> paneCall) {
        String output;
        do {
            output = paneCall.get();
            if (output == null) return null;
        } while(checkInputErrors(output));

        return output;
    }
}
