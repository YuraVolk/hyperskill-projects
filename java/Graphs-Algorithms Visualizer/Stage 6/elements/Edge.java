package visualizer.elements;

import visualizer.logic.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static visualizer.elements.Vertex.HALF_VERTEX_SIZE;

public class Edge extends JComponent {
    private final Vertex startVertex;
    private final Vertex endVertex;
    public final static int EDGE_SIZE = 6;
    private final List<visualizer.elements.Vertex> activeVertices;

    public Edge(Vertex startVertex, Vertex endVertex, List<visualizer.elements.Vertex> activeVertices) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.activeVertices = activeVertices;
    }

    private Double getEdgeAngle() {
        double deltaX = endVertex.getX() - startVertex.getX();
        double deltaY = endVertex.getY() - startVertex.getY();

        if (deltaX == 0 && deltaY == 0) {
            throw new IllegalArgumentException("Both coordinates of start and end cannot be the same.");
        }

        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }

    private boolean isActive() {
        return (activeVertices.stream().anyMatch(v -> v.equalsToLogicVertex(this.startVertex))
                && activeVertices.stream().anyMatch(v -> v.equalsToLogicVertex(this.endVertex))) ||
                (activeVertices.stream().anyMatch(v -> v.equalsToLogicVertex(this.endVertex))
                        && activeVertices.stream().anyMatch(v -> v.equalsToLogicVertex(this.startVertex)));
    }

    public void renderLabel(JPanel panel, Integer weight) {
        final int range = 20;
        int xOffset = 0;
        int yOffset = 0;
        int edgeMiddleX = Math.min(startVertex.getX(), endVertex.getX()) + Math.abs(startVertex.getX() - endVertex.getX()) / 2;
        int edgeMiddleY = Math.min(startVertex.getY(), endVertex.getY()) + Math.abs(startVertex.getY() - endVertex.getY()) / 2;
        double edgeAngle = getEdgeAngle();
        if (Math.abs(edgeAngle) <= range || Math.abs(edgeAngle) >= 180 - range) {
            yOffset = -range;
        } else if (Math.abs(edgeAngle) >= 90 - range && Math.abs(edgeAngle) <= 90 + range) {
            xOffset = EDGE_SIZE - 2;
        } else {
            xOffset = EDGE_SIZE * 2;
            yOffset = EDGE_SIZE * -2;
        }

        JLabel label = new JLabel(String.valueOf(weight));
        label.setBounds(edgeMiddleX + xOffset, edgeMiddleY + yOffset, range * 2, 15);
        label.setName(String.format("EdgeLabel <%s -> %s>", startVertex.getLabel(), endVertex.getLabel()));
        label.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 25));
        label.setForeground(Color.LIGHT_GRAY);
        panel.add(label);
    }

    public static Rectangle getCalculatedBounds(Vertex startVertex, Vertex endVertex) {
        int startX = startVertex.getX() + HALF_VERTEX_SIZE;
        int startY = startVertex.getY() + HALF_VERTEX_SIZE;
        int endX = endVertex.getX() + HALF_VERTEX_SIZE;
        int endY = endVertex.getY() + HALF_VERTEX_SIZE;
        return new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(EDGE_SIZE));
        g2.setColor(this.isActive() ? Graph.ACTIVE_COLOR : Color.WHITE);
        g2.drawLine(
                startVertex.getX() - getBounds().x + HALF_VERTEX_SIZE,
                startVertex.getY() - getBounds().y + HALF_VERTEX_SIZE,
                endVertex.getX() - getBounds().x + HALF_VERTEX_SIZE,
                endVertex.getY() - getBounds().y + HALF_VERTEX_SIZE
        );
    }
}
