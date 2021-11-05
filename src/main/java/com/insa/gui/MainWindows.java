package com.insa.gui;

import com.insa.utils.*;

import javax.swing.*;
import java.awt.*;

public class MainWindows {
    JFrame frame;
    JPanel panel;
    JTextField textField;
    JButton button;
    Container contentPane;

    public void launchMainFrame() {
        // Creating main frame
        frame = new JFrame("Java chat system");
        panel = new JPanel();

        // Page stand
        textField = new JTextField("login");
        button = new JButton("Login");
        contentPane = frame.getContentPane();

        panel.add(textField);
        panel.add(button);

        contentPane.add(panel, BorderLayout.CENTER);

        // Adding listener
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit chat?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                            ExitHandler.exit();
                }
            }
        });

        // Frame properties
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setVisible(true);
    }
}
