package com.mycompany.issue1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OutputTree extends JFrame {
    JTextArea textArea;
    JScrollPane scrollPane;

    public OutputTree(String title, ArrayList<String> text) {
        setTitle(title);
        setSize(300, 300); // تعديل حجم النافذة ليتناسب مع حجم الصورة
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false); // جعل منطقة النص غير قابلة للتعديل
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // استخدام خط ثابت العرض لترتيب النص
        textArea.setMargin(new Insets(10, 10, 10, 10)); // إضافة حواف بيضاء حول منطقة النص
        for (String line : text) {
            textArea.append(line + "\n");
        }

        scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public OutputTree(String printTree) {
        setTitle("Drawing Tree");
        setSize(600, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // استخدام خط ثابت العرض لترتيب النص
        textArea.setMargin(new Insets(10, 10, 10, 10)); // إضافة حواف بيضاء حول منطقة النص

        textArea.append(printTree);

        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
