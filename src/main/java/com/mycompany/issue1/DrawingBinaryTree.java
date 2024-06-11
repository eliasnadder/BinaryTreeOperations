package com.mycompany.issue1;
import javax.swing.*;
import java.awt.*;
class DrawingBinaryTree extends JPanel {
    private BinaryTree tree;
    private final int NODE_RADIUS = 20;
    private final int VERTICAL_SPACING = 50;

    public DrawingBinaryTree(BinaryTree tree) {
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int horizontalSpacing) {
        g.setColor(Color.BLACK);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.WHITE);
        g.drawString(node.getName(), x - 6, y + 4);

        if (node.getLeft() != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x - 1, y + NODE_RADIUS, x - horizontalSpacing + 1, y + VERTICAL_SPACING - NODE_RADIUS);
            drawTree(g, node.getLeft(), x - horizontalSpacing, y + VERTICAL_SPACING, horizontalSpacing / 2);
        }

        if (node.getRight() != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x + 1, y + NODE_RADIUS, x + horizontalSpacing - 1, y + VERTICAL_SPACING - NODE_RADIUS);
            drawTree(g, node.getRight(), x + horizontalSpacing, y + VERTICAL_SPACING, horizontalSpacing / 2);
        }
    }
}
