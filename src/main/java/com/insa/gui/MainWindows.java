package com.insa.gui;

import com.insa.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindows implements ActionListener {

    /**
     * Migration vers le layout floating pour plus de flexibilité en cours.
     * Voir les examples, ajouter deux panels (image et b+text)
     * @param contentPane
     */
    private static void addComponents(final Container contentPane) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        //FlowLayout el = new FlowLayout();
        //contentPane.setLayout(el);
        //el.setAlignment(FlowLayout.TRAILING);

        // Panels
        final JPanel image = new JPanel();
        final JPanel login = new JPanel();
        image.setLayout(new FlowLayout());
        login.setLayout(new FlowLayout());

        image.add(new JButton("Button 1"));
  
        // Pseudo field
        JTextField textField = new JTextField("Pseudo", 10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setHorizontalAlignment(JTextField.CENTER);

        // Button go
        JButton button = new JButton("Go!");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        login.add(textField);
        login.add(button);

        contentPane.add(image, BorderLayout.CENTER);
        contentPane.add(login, BorderLayout.SOUTH); ;
    }

    public void start() {
        // GTK+
        GUIUtils.initGTK();

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

    public void stop() {
        
    }

    public void actionPerformed(ActionEvent e) {
        ExitHandler.exit();
    }
    
}
