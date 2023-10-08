package visualizer.events.listeners;

import visualizer.logic.Graph;

import java.awt.*;

public interface Listener<T extends Container> {
    void configureListener(Graph graph, T frame);
}
