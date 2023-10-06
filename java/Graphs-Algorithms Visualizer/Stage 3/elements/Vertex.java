package visualizer.elements;

import javax.swing.*;
import java.awt.*;

public class Vertex {
    private final int x;
    private final int y;
    private final String label;

    public static final int VERTEX_SIZE = 50;

    public Vertex(int x, int y, String label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public void draw(Container frame) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.decode("#e2e2e2"));
                g.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setName(String.format("Vertex %s", this.label));

        JLabel label = new JLabel(this.label);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setName(String.format("VertexLabel %s", this.label));
        label.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 25));
        panel.add(label, BorderLayout.CENTER);

        panel.setBounds(this.x, this.y, Vertex.VERTEX_SIZE, Vertex.VERTEX_SIZE);
        frame.add(panel);
    }
}
