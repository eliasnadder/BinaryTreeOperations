package com.mycompany.issue1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImportManuallyGUI extends JFrame implements ActionListener {

    private JTextField nameField, lengthField, widthField;
    private JButton addButton, checkButton, flipButton, printToFileButton, printTreeButton, backButton;
    private JTextArea outputArea;
    JPanel inputPanel, buttonPanel;
    static BinaryTree root = new BinaryTree();
    ArrayList<Node> nodes = new ArrayList<>();
    private static ArrayList<Node> combinedNodes = new ArrayList<>();
    private static ArrayList<Node> flippedNodes = new ArrayList<>();

    public static void setCombinedNodes(Node combinedNode) {
        combinedNodes.add(combinedNode);
    }

    public static void setFlippedNodes(Node combinedNode) {
        flippedNodes.add(combinedNode);
    }

    public static ArrayList<Node> getCombinedNodes() {
        return combinedNodes;
    }

    public ImportManuallyGUI() {
        setTitle("Rectangle Checker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        nameField.setBorder(new RoundedBorder(10));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Width:"));
        widthField = new JTextField();
        widthField.setBorder(new RoundedBorder(10));
        inputPanel.add(widthField);

        inputPanel.add(new JLabel("Length:"));
        lengthField = new JTextField();
        lengthField.setBorder(new RoundedBorder(10));
        inputPanel.add(lengthField);

        addButton = new JButton("Add Rectangle");
        addButton.addActionListener(this);
        customizeButton(addButton);
        inputPanel.add(addButton);

        printTreeButton = new JButton("Print Tree");
        printTreeButton.addActionListener(this);
        customizeButton(printTreeButton);
        inputPanel.add(printTreeButton);

        printToFileButton = new JButton("Export Tree to File");
        printToFileButton.addActionListener(this);
        customizeButton(printToFileButton);
        inputPanel.add(printToFileButton);

        checkButton = new JButton("Check Combinations");
        checkButton.addActionListener(this);
        customizeButton(checkButton);
        inputPanel.add(checkButton);

        flipButton = new JButton("Flip Combined Nodes");
        flipButton.addActionListener(this);
        customizeButton(flipButton);
        flipButton.setVisible(false);
        inputPanel.add(flipButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0)); // Top, Left, Bottom, Right padding

        // Add the backButton to the panel
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        customizeButton(backButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
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

    private void addRectangle() {
        String name = nameField.getText();
        String lengthText = lengthField.getText();
        String widthText = widthField.getText();
        if (!name.isEmpty() && !lengthText.isEmpty() && !widthText.isEmpty()) {
            int length = Integer.parseInt(lengthText);
            int width = Integer.parseInt(widthText);
            try {
                root.addLeaf(name, width, length);
                nodes.add(new Node(name, width, length));
                outputArea.append("Rectangle added: " + name + "[" + width + "," + length + "]" + "\n");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
            nameField.setText("");
            lengthField.setText("");
            widthField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void addNodeToTree(Node node) {
        // Assuming there's a static method to access the BinaryTree instance
        root.addLeaf(node.getName(), node.getWidth(), node.getHeight());
    }

    private void addRectangle(Node node) {
        try {
            root.addLeaf(node.getName(), node.getWidth(), node.getHeight());
            // nodes.add(node);
            outputArea.append(
                    "Rectangle added: " + node.getName() + "[" + node.getWidth() + "," + node.getHeight() + "]" + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
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

    public void checkMethod() {
        if (nodes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (nodes.size() == 1) {
            JOptionPane.showMessageDialog(this, "Please Add Another Node", "Add Another Node",
                    JOptionPane.WARNING_MESSAGE);
            return;
        } else if (nodes.size() > 1) {
            List<Map<Boolean, Node>> validResults = Methods.canCombineIntoSingleSheet(nodes);
            if (validResults.size() >= 2) {
                SwingUtilities.invokeLater(() -> {
                    DisplayCombinationOptions displayCombinationOptions = new DisplayCombinationOptions(validResults,
                            outputArea, nodes);
                    displayCombinationOptions.setRoot(root);
                    displayCombinationOptions.setLocationRelativeTo(null);
                    displayCombinationOptions.setVisible(true);
                });
                if (combinedNodes.size() >= 1) {
                    flipButton.setVisible(true);
                }
            } else if (validResults.size() == 1) {
                displayResults(validResults);
                if (combinedNodes.size() >= 1) {
                    flipButton.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No combinations found!", "No combinations",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void printToFileMethod() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");

        // تعيين اسم افتراضي للملف
        fileChooser.setSelectedFile(new File("output.txt"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.getName().endsWith(".txt")) {
                try {
                    Methods.exportTree(root, fileToSave);
                    String[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "File Saved Successfully: " + fileToSave.getName(), "Save File",
                            JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (!fileToSave.getName().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
                try {
                    Methods.exportTree(root, fileToSave);
                    String[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "File Saved Successfully: " + fileToSave.getName(), "Save File",
                            JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void displayResults(List<Map<Boolean, Node>> results) {
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
                combinedNodes.add(node);
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
                combinedNodes.add(node);
            }
        }

    }

    private void flipMethod() {
        if (combinedNodes.size() >= 1) {
            FlipWindow flipWindow = new FlipWindow(combinedNodes, outputArea);
            // flipWindow.setValidResults(combinedNodes);
            flipWindow.setRoot(root);
            flipWindow.setFlippedNodes(flippedNodes);
            flipWindow.setLocationRelativeTo(null);
            flipWindow.setVisible(true);
        }
        // JOptionPane.showMessageDialog(null, combinedNodes.size(), getTitle(),
        // JOptionPane.NO_OPTION);
    }

    private void backMethod() {
        dispose(); // Close the current window
        SwingUtilities.invokeLater(() -> {
            WelcomeGUI welcomeGUI = new WelcomeGUI();
            welcomeGUI.setLocationRelativeTo(null);
            welcomeGUI.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addRectangle();
        } else if (e.getSource() == printTreeButton) {
            printTreeMethod();
        } else if (e.getSource() == printToFileButton) {
            printToFileMethod();
        } else if (e.getSource() == checkButton) {
            checkMethod();
        } else if (e.getSource() == flipButton) {
            flipMethod();
        } else if (e.getSource() == backButton) {
            backMethod();
        }
    }
}
