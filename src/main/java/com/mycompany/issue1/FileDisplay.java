package com.mycompany.issue1;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDisplay extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public FileDisplay(File file) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // استخدام خط ثابت العرض لترتيب النص

        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        setVisible(true);
    }
}
