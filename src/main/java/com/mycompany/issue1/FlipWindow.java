package com.mycompany.issue1;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlipWindow extends JFrame implements ActionListener {
    JPanel panel;
    JButton applyButton;
    JTextArea outputArea;
    List<JCheckBox> checkBoxes;
    List<Node> nodes;
    List<Node> selectedResults = new ArrayList<>();
    List<Node> flippedNodes;
    BinaryTree root;

    public void setFlippedNodes(List<Node> flippedNodes) {
        this.flippedNodes = flippedNodes;
    }

    public void setRoot(BinaryTree root) {
        this.root = root;
    }

    FlipWindow(List<Node> nodes, JTextArea outputArea) {
        this.nodes = nodes;
        // this.flippedNodes = flippedNodes;
        // this.root = root;
        this.outputArea = outputArea;

        setTitle("Flip Nodes");
        setSize(600, 400);
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        checkBoxes = new ArrayList<>();

        for (Node node : ImportManuallyGUI.getCombinedNodes()) {
            JCheckBox checkBox = new JCheckBox(
                    node.getName() + " - Dimensions: [" + node.getWidth() + ", " + node.getHeight() + "]");
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }

        applyButton = new JButton("Flip");
        applyButton.addActionListener(this);
        customizeButton(applyButton);
        add(new JScrollPane(panel), BorderLayout.CENTER);
        add(applyButton, BorderLayout.SOUTH);
    }

    private void displayFlippedNodes(List<Node> results) {
        StringBuilder resultText = new StringBuilder("Chosen combinations:\n");
        for (Node node : results) {
            resultText.append(node.getName()).append(" - Dimensions: [")
                    .append(node.getWidth()).append(", ").append(node.getHeight()).append("]\n");
        }
        String[] options = { "Add New Node", "Add to Tree and Delete Original Node", "Cancel" };
        int response = JOptionPane.showOptionDialog(null, resultText.toString(), "Combinations",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response == JOptionPane.YES_OPTION) {
            // Add new node to the tree
            for (Node node : results) {
                addRectangle(node);
                flippedNodes.add(node);
            }
        } else if (response == JOptionPane.NO_OPTION) {
            // Collect names of nodes to be removed
            List<String> namesToRemove = nodes.stream()
                    .map(Node::getName)
                    .collect(Collectors.toList());

            // Remove nodes from the list and tree
            nodes.removeIf(n -> namesToRemove.contains(n.getName()));
            namesToRemove.forEach(name -> {
                // root.removeLeaf(root.getRoot(), name);
                root.setRoot(root.removeLeaf(root.getRoot(), name));
                outputArea.append("Removed Node: " + name + "\n");
            });
            for (Node node : results) {
                addRectangle(node);
                flippedNodes.add(node);
            }
        }
    }

    private void addRectangle(Node node) {
        try {
            ImportManuallyGUI.addNodeToTree(node); // Add node to the tree managed by ImportManuallyGUI
            try {
                ImportFromFileGUI.addNodeToTree(node);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            outputArea.append(
                    "Flipped node added: " + node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]"
                            + "\n");
            System.out.println(ImportFromFileGUI.getCombinedNodes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void customizeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(120, 66, 119));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Flatlion", Font.BOLD, 14));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    Node flippedNode = Methods.flip(nodes.get(i));
                    ImportManuallyGUI.setFlippedNodes(flippedNode);
                    try {
                        ImportFromFileGUI.setFlippedNodes(flippedNode);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    selectedResults.add(flippedNode);

                }
            }
            displayFlippedNodes(selectedResults);
            this.dispose();
        }
    }
}
