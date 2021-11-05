package com.insa.gui;

import com.insa.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindows implements ActionListener {
    JFrame frame;
    JPanel panel;
    JTextField textField;
    JButton button;
    Container contentPane;

    private void initGTK() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launchMainFrame() {
        // GTK+
        initGTK();

        // Creating main frame
        frame = new JFrame("Java chat system");
        panel = new JPanel();
        contentPane = frame.getContentPane();
        contentPane.setLayout(new CardLayout());

        // Logo
        BufferedImage logo;
        try {
            logo = ImageIO.read(new File("img/logo_s.png"));
            JLabel picLabel = new JLabel(new ImageIcon(logo));
            contentPane.add(picLabel, BorderLayout.PAGE_START);
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentPane.add(panel, BorderLayout.CENTER);

        // Page stand
        textField = new JTextField("Pseudo");
        button = new JButton("Go!");
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(textField, BorderLayout.CENTER);
        contentPane.add(button, BorderLayout.CENTER);
        

        // Frame properties
        frame.setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);

        // Adding listener
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ExitHandler.exit();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

    }
    
}
