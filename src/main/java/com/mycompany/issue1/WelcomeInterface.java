/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.issue1;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeInterface extends JFrame {

    private JPanel mainPanel;
    private JLabel welcomeLabel1, welcomeLabel2;
    private JProgressBar progressBar;

    public WelcomeInterface() {
        setTitle("Welcome");
        setSize(600, 400); // تعديل حجم النافذة ليتناسب مع حجم الصورة
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // إنشاء لوحة رئيسية مع تخطيط الطبقات وإضافة خلفية صورة
        mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // تحميل الصورة ورسمها كخلفية
                Image img = new ImageIcon("Logo\\img4.png").getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
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
        welcomeLabel1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(welcomeLabel1, gbc);

        welcomeLabel2 = new JLabel("to My Application");
        welcomeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel2.setFont(new Font("Flatlion", Font.BOLD, 20));
        welcomeLabel2.setForeground(Color.WHITE);
        welcomeLabel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        gbc.gridy = 1;
        mainPanel.add(welcomeLabel2, gbc);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(new Color(120, 66, 119));
        progressBar.setPreferredSize(new Dimension(40, 27)); // تحديد الأبعاد المفضلة للشريط
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // تحديد الهوامش
        progressBar.setOpaque(false); // جعل الخلفية شفافة
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 50, 0); // تحديد الهوامش للتحكم في المسافة بين العناصر
        mainPanel.add(progressBar, gbc);

        // تأخير عرض واجهة RectangleGUI بعد 1 ثانية
        Timer timer = new Timer(1300, (ActionEvent e) -> {
            dispose(); // إخفاء واجهة الترحيب
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                SwingUtilities.invokeLater(() -> {
                    WelcomeGUI welcomeGUI = new WelcomeGUI();
                    welcomeGUI.setLocationRelativeTo(null);
                    welcomeGUI.setVisible(true);
                });
            } catch (UnsupportedLookAndFeelException ex) {
                JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        timer.setRepeats(false); // تعيين القيمة إلى false لضمان أن يتم تشغيل الحدث مرة واحدة فقط
        timer.start(); // بدء التوقيت
    }
}
