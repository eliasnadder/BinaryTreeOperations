package com.mycompany.issue1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WelcomeGUI extends JFrame implements ActionListener {

    private JButton importManuallyButton, importFromFileButton, drawingButton;
    private JPanel mainPanel, buttonPanel;
    private JLabel welcomeLabel1, space;

    public WelcomeGUI() {
        setTitle("Welcome");
        setSize(600, 400); // تعديل حجم النافذة ليتناسب مع حجم الصورة
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1, directly under welcomeLabel1 which is at row 0
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        add(mainPanel);

        // إنشاء عبارة الترحيب وتخطيطها في أعلى اللوحة
        welcomeLabel1 = new JLabel("Welcome");
        welcomeLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel1.setFont(new Font("Flatlion", Font.BOLD, 40));
        welcomeLabel1.setForeground(Color.BLACK);
        welcomeLabel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(welcomeLabel1, gbc);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        gbc.gridy = 1;
        mainPanel.add(buttonPanel, gbc);

        importManuallyButton = new JButton("Import Tree Manually");
        importManuallyButton.addActionListener(this);
        customizeButton(importManuallyButton);
        buttonPanel.add(importManuallyButton);

        space = new JLabel(" ");
        buttonPanel.add(space);

        importFromFileButton = new JButton("Import from file");
        importFromFileButton.addActionListener(this);
        customizeButton(importFromFileButton);
        buttonPanel.add(importFromFileButton);

        drawingButton = new JButton("Import and Drawing");
        drawingButton.addActionListener(this);
        customizeButton(drawingButton);
        gbc.gridy = 2;
        mainPanel.add(drawingButton, gbc);
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

    public void viewImportFromFileGUI() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getAbsolutePath().endsWith(".txt")) {
                JOptionPane.showMessageDialog(this, "Please select a .txt file", "Invalid File Type",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SwingUtilities.invokeLater(() -> {
                ImportFromFileGUI importFromFileGUI = new ImportFromFileGUI();
                importFromFileGUI.setLocationRelativeTo(null);
                importFromFileGUI.setVisible(true);
                importFromFileGUI.outputArea.setText(content.toString());
                importFromFileGUI.setFile(selectedFile);
            });
        }
    }

    public void viewImportManuallyGUI() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            ImportManuallyGUI importManuallyGUI = new ImportManuallyGUI();
            importManuallyGUI.setLocationRelativeTo(null);
            importManuallyGUI.setVisible(true);
        });
    }

    public void viewAddDimensionGUI() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            // AddDimensionGUI addDimensionGUI = new AddDimensionGUI();
            // addDimensionGUI.setLocationRelativeTo(null);
            // addDimensionGUI.setVisible(true);
            ImportAndDrawingGUI importAndDrawingGUI =new ImportAndDrawingGUI();
            importAndDrawingGUI.setLocationRelativeTo(null);
            importAndDrawingGUI.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importManuallyButton) {
            viewImportManuallyGUI();
        } else if (e.getSource() == importFromFileButton) {
            viewImportFromFileGUI();
        } else if (e.getSource() == drawingButton) {
            viewAddDimensionGUI();
        }
    }
}
