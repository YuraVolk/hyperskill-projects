package visualizer.elements;

import visualizer.logic.Graph;
import visualizer.logic.GraphEditingMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBar {
    private final JFrame frame;
    private final Graph graph;
    public MenuBar(JFrame frame, Graph graph) {
        this.frame = frame;
        this.graph = graph;
    }

    public void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.initializeModeBar(menuBar);
        frame.setJMenuBar(menuBar);
    }

    private void initializeModeBar(JMenuBar menuBar) {
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setMnemonic(KeyEvent.VK_M);

        for (GraphEditingMode mode : GraphEditingMode.values()) {
            JMenuItem item = new JMenuItem(mode.toString());
            item.setName(mode.toString());
            item.addActionListener(event -> graph.setMode(mode));
            modeMenu.add(item);
        }

        menuBar.add(modeMenu);
    }
}
