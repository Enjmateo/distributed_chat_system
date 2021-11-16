package com.insa.gui;

import com.insa.app.App;
import com.insa.utils.*;
import com.insa.utils.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WelcomeWindows extends JFrame implements ActionListener {
    private JButton button;
    private JTextField textField;

    private void addComponents(final Container contentPane) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Panels
        final JPanel image = new JPanel();
        final JPanel login = new JPanel();
        image.setLayout(new FlowLayout());
        login.setLayout(new FlowLayout());

        // Logo
        Image logo = Graphics.getLogo();
        JLabel picLabel = new JLabel(new ImageIcon(logo));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // image.add(new JButton("Button 1"));
        image.add(picLabel);

        // Pseudo field
        textField = new JTextField("Pseudo", 10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setHorizontalAlignment(JTextField.CENTER);

        // Button go
        button = new JButton("Go!");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(this);

        login.add(textField);
        login.add(button);

        contentPane.add(image, BorderLayout.CENTER);
        contentPane.add(login, BorderLayout.SOUTH);
        ;
    }

    public WelcomeWindows() {
        super("Java chat system - Welcome");

        // GTK+
        GUIUtils.initGTK();

        setLayout(new CardLayout());

        addComponents(this.getContentPane());

        // Frame properties
        pack();
        setVisible(true);

        // Adding listener
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ExitHandler.exit();
            }
        });
    }

    /**
     * DEBUG
     * 
     * @see actionPerformed
     */
    public void skipWindows() {
        System.out.println("[!] Skipping...");
        App.mainThread("M. Louis DEBUG");
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[+] Go!");
        App.mainThread(textField.getText());
        this.dispose();
    }
}
