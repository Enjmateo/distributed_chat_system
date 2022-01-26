package com.insa.gui.chattabs;

import com.insa.app.User;
import com.insa.communication.TextMessage;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SendBar extends HBox{
    TextField textField = new TextField("Texte à  envoyer");
    Button sendButton = new Button("Send");
    User user;

    public SendBar(User user){
        super();
        super.getChildren().addAll(textField,sendButton);
        this.user = user;
        sendButton.setOnAction(e->buttonHandler());
        textField.setOnAction(e->buttonHandler());
    }

    private void buttonHandler() {
        if(textField.getText()!=null)user.sendMessage(new TextMessage(user.getUUID(),textField.getText()));
    }
}
