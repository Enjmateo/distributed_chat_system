package com.insa.gui;

import com.insa.app.App;
import com.insa.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindows extends JFrame implements ActionListener {
    
    private JPanel panel;
    private JLabel header1;
    private JLabel header2;
    private JLabel header3;

    private JScrollPane chatPane;
    private JTextArea chatArea;

    private JButton sendButton;
    private JTextField sendBox;

    private JList<String> contactList;
    DefaultListModel<String> rawList = new DefaultListModel<>();

    private String pseudo;
    private String lastStatus;



    private void addComponents(final Container contentPane) {
        panel = new JPanel();

        contactList = new JList<String>(rawList);

        sendBox = new JTextField();
        sendButton = new JButton();

        chatPane = new JScrollPane();
        chatArea = new JTextArea();

        header1 = new JLabel();
        header2 = new JLabel();
        header3 = new JLabel();

        setResizable(false);
        panel.setLayout(null);

        ///Ajout de la liste de contact
        panel.add(contactList);
        contactList.setBounds(760, 80, 200, 660);

        /// Ajout des textes d'entête
        header1.setText("Objet de Discution Digitale - Client");
        panel.add(header1);
        header1.setBounds(10, 0, 600, 40);

        header2.setText("Licence GNU GPL v3");
        panel.add(header2);
        header2.setBounds(10, 20, 600, 40);

        updateStatus();
        panel.add(header3);
        header3.setBounds(10, 40, 600, 40);

        /// Ajout de la barre d'envoi des messages
        sendBox.setToolTipText("text\tType your message here...");
        panel.add(sendBox);
        sendBox.setBounds(10, 750, 650, 40);

        sendButton.setText("Send");
        panel.add(sendButton);
        sendButton.setBounds(670, 750, 80, 40);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonAction(evt);
            }
        });

        /// Ajout de l'espace de chat
        chatArea.setColumns(30);
        chatArea.setRows(10);

        chatPane.setViewportView(chatArea);
        panel.add(chatPane);
        chatPane.setBounds(10, 80, 740, 660 );

        /// Mise en forme de la fenêtre
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    public MainWindows (){
        super("Java chat system");

        GUIUtils.initGTK();

        Container contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());

        addComponents(getContentPane());

        pack();
        setVisible(true);

        pseudo = "Unknown";
        lastStatus = "Unknown";

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ExitHandler.exit();
            }
        });
    }

    private void updateStatus() {
        header3.setText(this.lastStatus + " - " + this.pseudo);
    }

    public void setStatus(String status){
        this.lastStatus = status;
        updateStatus();
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
        updateStatus();
    }

    /**
     * @deprecated -> il faut créer la class utilisateur et la faire passer en parametres
     * @param pseudo
     */
    public void addUser(String pseudo){
        rawList.addElement(pseudo);
        //rawList.removeElement(pseudo);
    }

    /** @deprecated -> bad argument (à changer) */
    public void removeUser(){}

    /** @deprecated -> bad argument (à changer)*/
    public void addMessage(String msg){
        chatArea.append("\n " + pseudo + ": " + msg);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
        ExitHandler.exit();
    }

    private void sendButtonAction(ActionEvent evt) {
        // Debug
        addMessage(sendBox.getText());

        //App.sendMessage(sendBox.getText());
        sendBox.setText("");
    }
}
