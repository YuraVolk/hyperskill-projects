package visualizer.elements;

import visualizer.logic.Vertex;

import javax.swing.*;
import java.awt.*;

public class Edge extends JComponent {
    private final Vertex startVertex;
    private final Vertex endVertex;
    private final static int HALF_VERTEX_SIZE = visualizer.elements.Vertex.VERTEX_SIZE / 2;

    public Edge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public void renderLabel(JPanel panel, Integer weight) {
        JLabel label = new JLabel(String.valueOf(weight));
        label.setBounds((startVertex.getX() + endVertex.getX()) / 2, (int) (((startVertex.getY() + endVertex.getY()) / 2) * 0.9), 25, 25);
        label.setName(String.format("EdgeLabel <%s -> %s>", startVertex.getLabel(), endVertex.getLabel()));
        label.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 25));
        label.setForeground(Color.LIGHT_GRAY);
        panel.add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        g2.setColor(Color.WHITE);
        g2.drawLine(
                startVertex.getX() + HALF_VERTEX_SIZE,
                startVertex.getY() + HALF_VERTEX_SIZE,
                endVertex.getX() + HALF_VERTEX_SIZE,
                endVertex.getY() + HALF_VERTEX_SIZE
        );
    }
}
