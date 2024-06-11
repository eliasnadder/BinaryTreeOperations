package com.mycompany.issue1;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayCombinationOptions extends JFrame implements ActionListener {

    JPanel panel, bottomPanel;
    JButton applyButton;
    JLabel countLabel;
    List<JCheckBox> checkBoxes;
    List<Map<Boolean, Node>> validResults;
    List<Map<Boolean, Node>> selectedResults = new ArrayList<>();
    List<Node> nodes;
    JTextArea outputArea;
    BinaryTree root;
    Drawing drawing;

    public void setRoot(BinaryTree root) {
        this.root = root;
    }

    public List<JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    DisplayCombinationOptions(List<Map<Boolean, Node>> validResults, JTextArea outputArea, List<Node> nodes) {
        this.validResults = validResults;
        this.outputArea = outputArea;
        this.nodes = nodes;
        setTitle("View Combinations");
        setSize(600, 400);
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        checkBoxes = new ArrayList<>();

        for (Map<Boolean, Node> result : validResults) {
            Node node = result.get(Boolean.TRUE);
            JCheckBox checkBox = new JCheckBox(
                    node.getName() + " - Dimensions: [" + node.getWidth() + ", " + node.getHeight() + "]");
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }
        add(new JScrollPane(panel), BorderLayout.CENTER);

        bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1, directly under welcomeLabel1 which is at row 0
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        add(bottomPanel, BorderLayout.SOUTH);

        countLabel = new JLabel(Methods.getCountCombinations() + " Combinations");
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; // Align to the left side
        gbc.gridwidth = GridBagConstraints.REMAINDER; // End row after this component
        bottomPanel.add(countLabel, gbc);

        applyButton = new JButton("Combine");
        applyButton.addActionListener(this);
        customizeButton(applyButton);
        gbc.gridy = 1; // Move applyButton to the second row, same column
        gbc.weighty = 1.0; // Give vertical weight to push components up
        bottomPanel.add(applyButton, gbc);
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

    void viewDrawing(Drawing drawing) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OutputTree outputTree = new OutputTree(drawing.importDrawing(drawing));
                outputTree.setVisible(true);
            }
        });
    }

    private void displayResults(List<Map<Boolean, Node>> results) {
        StringBuilder resultText = new StringBuilder("Chosen combinations:\n");
        for (Map<Boolean, Node> result : results) {
            Node node = result.get(Boolean.TRUE);
            resultText.append(node.getName()).append(" - Dimensions: [")
                    .append(node.getWidth()).append(", ").append(node.getHeight()).append("]\n");

            drawing = new Drawing(node.getWidth(), node.getHeight());
            Sheet sheet = new Sheet(node.getName(), node.getWidth(), node.getHeight(), 10, 0);
            drawing.addSheet(sheet);
        }
        String[] options = { "Add new node", "Add to tree and delete combination nodes", "Cancel" };
        int response = JOptionPane.showOptionDialog(null, resultText.toString(), "Combinations",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response == JOptionPane.YES_OPTION) {
            // Add new node to the tree
            for (Map<Boolean, Node> result : results) {
                Node node = result.get(Boolean.TRUE);
                addRectangle(node);
                viewDrawing(drawing);
                // ImportManuallyGUI.setCombinedNodes(node);
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

            // Add new nodes from the results
            for (Map<Boolean, Node> result : results) {
                Node node = result.get(Boolean.TRUE);
                addRectangle(node);
                viewDrawing(drawing);
                // ImportManuallyGUI.setCombinedNodes(node);
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
                    "Rectangle added: " + node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]" + "\n");
            // System.out.println(ImportFromFileGUI.getCombinedNodes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    ImportManuallyGUI.setCombinedNodes(validResults.get(i).get(Boolean.TRUE));
                    try {
                        ImportFromFileGUI.setCombinedNodes(validResults.get(i).get(Boolean.TRUE));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    selectedResults.add(validResults.get(i));
                }
            }
            displayResults(selectedResults);
            this.dispose();
        }
    }
}
