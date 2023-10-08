package visualizer.elements;

import visualizer.logic.Graph;
import visualizer.logic.GraphEditingMode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MenuBar {
    private final JFrame frame;
    private final Graph graph;
    public MenuBar(JFrame frame, Graph graph) {
        this.frame = frame;
        this.graph = graph;
    }

    public void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.initializeFileBar(menuBar);
        this.initializeModeBar(menuBar);
        this.initializeAlgorithmsBar(menuBar);
        frame.setJMenuBar(menuBar);
    }

    private void initializeModeBar(JMenuBar menuBar) {
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setMnemonic(KeyEvent.VK_M);

        for (GraphEditingMode mode : GraphEditingMode.values()) {
            if (mode.getCategory() != GraphEditingMode.Category.MODE) continue;
            JMenuItem item = new JMenuItem(mode.getTitle());
            item.setName(mode.getTitle());
            item.addActionListener(event -> graph.setMode(mode));
            modeMenu.add(item);
        }

        menuBar.add(modeMenu);
    }

    private void initializeFileBar(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("New");
        newItem.setName("New");
        newItem.addActionListener(event -> graph.resetGraph());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("Exit");
        exitItem.addActionListener(event -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        fileMenu.add(newItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
    }

    private void initializeAlgorithmsBar(JMenuBar menuBar) {
        JMenu algorithmsMenu = new JMenu("Algorithms");
        algorithmsMenu.setMnemonic(KeyEvent.VK_A);

        for (GraphEditingMode mode : GraphEditingMode.values()) {
            if (mode.getCategory() != GraphEditingMode.Category.ALGORITHM) continue;
            JMenuItem item = new JMenuItem(mode.getTitle());
            item.setName(mode.getTitle());
            item.addActionListener(event -> graph.setMode(mode));
            algorithmsMenu.add(item);
        }

        menuBar.add(algorithmsMenu);
    }
}
