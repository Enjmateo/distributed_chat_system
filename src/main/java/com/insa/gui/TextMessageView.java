package com.insa.gui;

import java.awt.*;
import javax.swing.*;

public class TextMessageView extends JTextField {
    private String sender;
    private String message;

    private static int yPos = 0;

    public TextMessageView(String sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public void addToPanel(Container panel){
        this.setEditable(false);
        this.setText(sender + ": " + message);
        this.setBounds(10, yPos, 600, 40);
        yPos = yPos + 40;

        panel.add(this);
    }
}
