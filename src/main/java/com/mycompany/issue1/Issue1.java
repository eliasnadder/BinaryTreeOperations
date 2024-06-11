/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.issue1;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Issue1 {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.invokeLater(() -> {
                WelcomeInterface welcomeInterface = new WelcomeInterface();
                welcomeInterface.setLocationRelativeTo(null);
                welcomeInterface.setVisible(true);
            });
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

//        Node a = new Node("A", 2, 1);
//        Node b = new Node("B", 3, 2);
//        Node c = new Node("C", 1, 6);
//        Node d = new Node("D", 6, 1);
//        List<Node> nodes = Arrays.asList(a,b, c, d);
//        System.out.println(Combiner.canCombineIntoSingleSheet(nodes));
    }
}
