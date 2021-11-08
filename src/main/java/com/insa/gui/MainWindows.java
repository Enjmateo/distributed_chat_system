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

    private void initGTK() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @deprecated
     * @param text
     * @param container
     */
    private static void addButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    /**
     * Migration vers le layout floating pour plus de flexibilité en cours.
     * Voir les examples, ajouter deux panels (image et b+text)
     * @param contentPane
     */
    private static void addComponents(Container contentPane) {
        //contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        FlowLayout el = new FlowLayout();
        contentPane.setLayout(el);
        el.setAlignment(FlowLayout.TRAILING);

        // Logo
        BufferedImage logo;
        try {
            logo = ImageIO.read(new File("img/logo_s.png"));
            JLabel picLabel = new JLabel(new ImageIcon(logo));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pseudo field
        JTextField textField = new JTextField("Pseudo", 20);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setSize(new Dimension(120, 120));
        textField.setHorizontalAlignment(JTextField.CENTER);

        // Button go
        JButton button = new JButton("Go!");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(textField);
        contentPane.add(button);

        // Debug: To remove
        addButton("Long-Named Button 4", contentPane);
    }

    public void start() {
        // GTK+
        initGTK();

        // Creating main frame
        JFrame frame = new JFrame("Java chat system");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new CardLayout());

        addComponents(frame.getContentPane());
        
        // Frame properties
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
        ExitHandler.exit();
    }
    
}
