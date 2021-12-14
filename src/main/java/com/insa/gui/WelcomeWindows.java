package com.insa.gui;

import com.insa.app.App;
import com.insa.app.UsersHandler;
import com.insa.communication.Data;
import com.insa.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;   


public class WelcomeWindows extends JFrame implements ActionListener {
    private JButton buttonGo;
    private JButton buttonSettings;
    private JTextField textField;

    private JFileChooser fileChooser;

    private void addComponents(final Container contentPane) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Panels
        final JPanel image = new JPanel();
        final JPanel login = new JPanel();
        image.setLayout(new FlowLayout());
        login.setLayout(new FlowLayout());

        // Logo
        Image logo = GUIUtils.getLogo();
        JLabel picLabel = new JLabel(new ImageIcon(logo));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // image.add(new JButton("Button 1"));
        image.add(picLabel);

        // Pseudo field
        textField = new JTextField("Pseudo", 10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setHorizontalAlignment(JTextField.CENTER);

        // Button go
        buttonGo = new JButton("Go!");
        buttonGo.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonGo.addActionListener(this);

        // button settings
        buttonSettings = new JButton("Load configuration");
        buttonSettings.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonSettings.addActionListener(this);

        login.add(textField);
        login.add(buttonGo);
        login.add(buttonSettings);

        contentPane.add(image, BorderLayout.CENTER);
        contentPane.add(login, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(buttonGo);
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
     * @deprecated
     * @see actionPerformed
     */
    public void skipWindows() {
        System.out.println("[!] Skipping...");
        App.mainThread();
        this.dispose();
    }

    private void actionButtonGo() {
        System.out.println("[+] Go!");

        ArrayList<String> pseudoList = UsersHandler.getPseudos();
        String pseudo = textField.getText();
        if (pseudoList.contains(pseudo)) {
            System.out.println("[!] Given pseudo already used!");
            return;
        }

        UsersHandler.getLocalUser().setPseudo(pseudo);
        App.mainThread();
        this.dispose();
    }

    private void actionButtonSettings() {
        System.out.println("[+] Asking config. file...");
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int returnVal = fileChooser.showOpenDialog(this);
        System.out.println("[+] Showing windows...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File oldConfigFile = new File(Consts.CONFIG_FILE);

            String newConfigFile = selectedFile.getAbsolutePath();
            System.out.println("[+] Config. file: " + newConfigFile);

            oldConfigFile.delete();
            selectedFile.renameTo(new File(Consts.CONFIG_FILE)); 

            Data.reloadData();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonGo) {
            actionButtonGo();
        } else if (e.getSource() == buttonSettings) {
            actionButtonSettings();
        }
    }

}
