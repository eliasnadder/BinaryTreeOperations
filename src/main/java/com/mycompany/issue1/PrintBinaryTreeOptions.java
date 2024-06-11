package com.mycompany.issue1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

class PrintBinaryTreeOptions extends JFrame implements ActionListener {
    private JRadioButton preOrderRadioButton, inOrderRadioButton, postOrderRadioButton, levelOrderRadioButton,
            drawingTreeRadioButton, texRadioButton, drawingTextRadioButton;
    private JButton printButton;
    private BinaryTree binaryTree;
    private Node binaryTreeRoot;
    private Drawing drawing;

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public void setBinaryTree(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    public void setBinaryTreeRoot(Node binaryTreeRoot) {
        this.binaryTreeRoot = binaryTreeRoot;
    }

    public PrintBinaryTreeOptions() {
        setTitle("Print Binary Tree Options");
        setLayout(new FlowLayout());

        // إنشاء مجموعة الأزرار
        ButtonGroup group = new ButtonGroup();

        // خيارات الطباعة باستخدام JRadioButton
        texRadioButton = new JRadioButton("Text");
        preOrderRadioButton = new JRadioButton("Pre-order");
        inOrderRadioButton = new JRadioButton("In-order");
        postOrderRadioButton = new JRadioButton("Post-order");
        levelOrderRadioButton = new JRadioButton("Level-order");
        drawingTreeRadioButton = new JRadioButton("Drawing Tree");

        // إضافة الأزرار إلى المجموعة
        group.add(texRadioButton);
        group.add(preOrderRadioButton);
        group.add(inOrderRadioButton);
        group.add(postOrderRadioButton);
        group.add(levelOrderRadioButton);
        group.add(drawingTreeRadioButton);

        // تخصيص الأزرار وإضافتها إلى النافذة
        customizeRadioButton(texRadioButton);
        customizeRadioButton(preOrderRadioButton);
        customizeRadioButton(inOrderRadioButton);
        customizeRadioButton(postOrderRadioButton);
        customizeRadioButton(levelOrderRadioButton);
        customizeRadioButton(drawingTreeRadioButton);

        add(texRadioButton);
        add(preOrderRadioButton);
        add(inOrderRadioButton);
        add(postOrderRadioButton);
        add(levelOrderRadioButton);
        add(drawingTreeRadioButton);

        // زر لطباعة الشجرة الثنائية
        printButton = new JButton("Print");
        customizeButton(printButton);
        printButton.addActionListener(this);
        add(printButton);

        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public PrintBinaryTreeOptions(String t) {
        setTitle("Print Binary Tree Options");
        setLayout(new FlowLayout());

        // إنشاء مجموعة الأزرار
        ButtonGroup group = new ButtonGroup();

        // خيارات الطباعة باستخدام JRadioButton
        drawingTextRadioButton = new JRadioButton("Drawing as a text");
        texRadioButton = new JRadioButton("Text");
        preOrderRadioButton = new JRadioButton("Pre-order");
        inOrderRadioButton = new JRadioButton("In-order");
        postOrderRadioButton = new JRadioButton("Post-order");
        levelOrderRadioButton = new JRadioButton("Level-order");
        drawingTreeRadioButton = new JRadioButton("Drawing Tree");

        // إضافة الأزرار إلى المجموعة
        group.add(drawingTextRadioButton);
        group.add(texRadioButton);
        group.add(preOrderRadioButton);
        group.add(inOrderRadioButton);
        group.add(postOrderRadioButton);
        group.add(levelOrderRadioButton);
        group.add(drawingTreeRadioButton);

        // تخصيص الأزرار وإضافتها إلى النافذة
        customizeRadioButton(drawingTextRadioButton);
        customizeRadioButton(texRadioButton);
        customizeRadioButton(preOrderRadioButton);
        customizeRadioButton(inOrderRadioButton);
        customizeRadioButton(postOrderRadioButton);
        customizeRadioButton(levelOrderRadioButton);
        customizeRadioButton(drawingTreeRadioButton);

        add(drawingTextRadioButton);
        add(texRadioButton);
        add(preOrderRadioButton);
        add(inOrderRadioButton);
        add(postOrderRadioButton);
        add(levelOrderRadioButton);
        add(drawingTreeRadioButton);

        // زر لطباعة الشجرة الثنائية
        printButton = new JButton("Print");
        customizeButton(printButton);
        printButton.addActionListener(this);
        add(printButton);

        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void customizeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(120, 66, 119));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setBorderPainted(false);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                ((AbstractButton) c).setOpaque(false);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g2, c);
                g2.dispose();
            }
        });
    }

    private void customizeRadioButton(JRadioButton radioButton) {
        radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        radioButton.setBackground(Color.WHITE);
        radioButton.setPreferredSize(new Dimension(200, 30));
    }

    public void viewOutputTreeGUI(String title, ArrayList<String> text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OutputTree outputTree = new OutputTree(title, text);
                outputTree.setVisible(true);
            }
        });
    }

    public void viewOutputTreeGUI(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OutputTree outputTree = new OutputTree(text);
                outputTree.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == printButton) {
            ArrayList<String> binaryTreeContent = new ArrayList<>();
            if (drawingTextRadioButton.isSelected()) {
                viewOutputTreeGUI(drawing.importDrawing(drawing));
            } else if (texRadioButton.isSelected()) {
                viewOutputTreeGUI("(" + binaryTree.printFormattedTree(binaryTreeRoot) + ")");
            } else if (preOrderRadioButton.isSelected()) {
                binaryTreeContent.add("Pre-order:");
                binaryTree.getPreOrder(binaryTreeRoot, binaryTreeContent);
                viewOutputTreeGUI(binaryTreeContent.get(0), binaryTreeContent);
            } else if (inOrderRadioButton.isSelected()) {
                binaryTreeContent.add("In-order:");
                binaryTree.getInOrder(binaryTreeRoot, binaryTreeContent);
                viewOutputTreeGUI(binaryTreeContent.get(0), binaryTreeContent);
            } else if (postOrderRadioButton.isSelected()) {
                binaryTreeContent.add("Post-order:");
                binaryTree.getPostOrder(binaryTreeRoot, binaryTreeContent);
                viewOutputTreeGUI(binaryTreeContent.get(0), binaryTreeContent);
            } else if (levelOrderRadioButton.isSelected()) {
                binaryTreeContent.add("Level-order:");
                binaryTree.getLevelOrder(binaryTreeRoot, binaryTreeContent);
                viewOutputTreeGUI(binaryTreeContent.get(0), binaryTreeContent);
            } else if (drawingTreeRadioButton.isSelected()) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Drawing Binary Tree");
                        frame.setSize(600, 400);

                        DrawingBinaryTree treePanel = new DrawingBinaryTree(binaryTree);
                        frame.add(treePanel);
                        frame.setVisible(true);
                    }
                });
            }
        }
    }
}
