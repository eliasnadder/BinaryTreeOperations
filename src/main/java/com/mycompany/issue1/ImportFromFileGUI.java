/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.issue1;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ImportFromFileGUI extends JFrame implements ActionListener {

    JTextArea outputArea;
    JButton checkCombinationButton, convertButton, flipButton, printTreeButton, backButton;
    JPanel buttonPanel, backButtonPanel, southPanel;
    File file;
    static BinaryTree root;
    static List<Node> combinedNodes = new ArrayList<>();
    static List<Node> flippedNodes = new ArrayList<>();

    public void setFile(File file) {
        this.file = file;
    }

    public static void setCombinedNodes(Node combinedNode) {
        combinedNodes.add(combinedNode);
    }

    public static List<Node> getCombinedNodes() {
        return combinedNodes;
    }

    public static void setFlippedNodes(Node combinedNode) {
        flippedNodes.add(combinedNode);
    }

    public ImportFromFileGUI() {
        setTitle("Import from file");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        convertButton = new JButton("Convert To Binary Tree");
        convertButton.addActionListener(this);
        customizeButton(convertButton);
        buttonPanel.add(convertButton);

        checkCombinationButton = new JButton("Check combination");
        checkCombinationButton.addActionListener(this);
        checkCombinationButton.setVisible(false);
        customizeButton(checkCombinationButton);
        buttonPanel.add(checkCombinationButton);

        printTreeButton = new JButton("Print Tree");
        printTreeButton.addActionListener(this);
        customizeButton(printTreeButton);
        printTreeButton.setVisible(false);
        buttonPanel.add(printTreeButton);

        flipButton = new JButton("Flip");
        flipButton.addActionListener(this);
        customizeButton(flipButton);
        // flipButton.setVisible(false);
        buttonPanel.add(flipButton);

        add(buttonPanel, BorderLayout.SOUTH);

        backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0)); // Top, Left, Bottom, Right padding
        // Add the backButton to the panel
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        customizeButton(backButton);
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.SOUTH);

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(backButtonPanel, BorderLayout.PAGE_END);

        // Add the combined panel to the south of the main frame
        add(southPanel, BorderLayout.SOUTH);
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

    private void backMethod() {
        dispose(); // Close the current window
        SwingUtilities.invokeLater(() -> {
            WelcomeGUI welcomeGUI = new WelcomeGUI();
            welcomeGUI.setLocationRelativeTo(null);
            welcomeGUI.setVisible(true);
        });
    }

    private void convertMethod() {
        try {
            root = Methods.importTree(file);
            root.preorder(root.getRoot());

            convertButton.setVisible(false);
            JOptionPane.showConfirmDialog(this, "The File Converted to Binarty Tree", "Converted",
                    JOptionPane.DEFAULT_OPTION);
            outputArea.append("This text converted successfully to binary tree\n");
            printTreeButton.setVisible(true);
            checkCombinationButton.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRectangle(Node node) {
        try {
            root.addLeaf(node.getName(), node.getWidth(), node.getHeight());
            combinedNodes.add(node);
            outputArea.append(
                    "Rectangle added: " + node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]" + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void addNodeToTree(Node node) {
        // Assuming there's a static method to access the BinaryTree instance
        root.addLeaf(node.getName(), node.getWidth(), node.getHeight());
    }

    public void checkMethod() {
        List<Node> nodes = Methods.getNodes();
        List<Map<Boolean, Node>> validResults = Methods.canCombineIntoSingleSheet(nodes);
        if (validResults.size() >= 2) {
            SwingUtilities.invokeLater(() -> {
                DisplayCombinationOptions displayCombinationOptions = new DisplayCombinationOptions(validResults,
                        outputArea, nodes);
                displayCombinationOptions.setRoot(root);
                displayCombinationOptions.setLocationRelativeTo(null);
                displayCombinationOptions.setVisible(true);
            });

        } else {
            displayResults(validResults, nodes);
            System.out.println(combinedNodes);
        }
    }

    private void displayResults(List<Map<Boolean, Node>> results, List<Node> nodes) {
        StringBuilder resultText = new StringBuilder("Chosen combinations:\n");
        for (Map<Boolean, Node> result : results) {
            Node node = result.get(Boolean.TRUE);
            resultText.append(node.getName()).append(" - Dimensions: [")
                    .append(node.getWidth()).append(", ").append(node.getHeight()).append("]\n");
        }
        String[] options = { "Add new node", "Add to tree and delete combination nodes", "Cancel" };
        int response = JOptionPane.showOptionDialog(null, resultText.toString(), "Combinations",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (response == JOptionPane.YES_OPTION) {
            // Add new node to the tree
            for (Map<Boolean, Node> result : results) {
                Node node = result.get(Boolean.TRUE);
                addRectangle(node);
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
            }
        }
    }

    private void printTreeMethod() {
        Node BTRoot = root.getRoot();
        SwingUtilities.invokeLater(() -> {
            PrintBinaryTreeOptions2 printBinaryTreeOptions = new PrintBinaryTreeOptions2();
            printBinaryTreeOptions.setBinaryTree(root);
            printBinaryTreeOptions.setBinaryTreeRoot(BTRoot);
            printBinaryTreeOptions.setLocationRelativeTo(null);
            printBinaryTreeOptions.setVisible(true);
        });
    }

    private void flipMethod() {
        SwingUtilities.invokeLater(() -> {
            FlipWindow flipWindow = new FlipWindow(combinedNodes, outputArea);
            flipWindow.setRoot(root);
            flipWindow.setFlippedNodes(flippedNodes);
            flipWindow.setLocationRelativeTo(null);
            flipWindow.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkCombinationButton) {
            checkMethod();
        } else if (e.getSource() == convertButton) {
            convertMethod();
        } else if (e.getSource() == flipButton) {
            flipMethod();
        } else if (e.getSource() == printTreeButton) {
            printTreeMethod();
        } else if (e.getSource() == backButton) {
            backMethod();
        }
    }
}
