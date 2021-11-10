package com.insa.gui;

import java.awt.*;
import javax.swing.*;

public class TextMessageView extends JTextField {
    private String sender;
    private String message;

    

    public TextMessageView(String sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public void addToPanel(Container panel){
        this.setEditable(false);
        this.setText(sender + ": " + message);

        // TODO: Mettre au propre  
        this.setBounds(10, GUIUtils.yPos, 600, 40);
        GUIUtils.yPos = GUIUtils.yPos + 40;

        panel.add(this);
    }
}
