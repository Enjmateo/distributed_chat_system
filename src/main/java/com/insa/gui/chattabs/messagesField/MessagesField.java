package com.insa.gui.chattabs.messagesField;

import com.insa.communication.TextMessage;

import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MessagesField extends ListView<VBox> {
    public MessagesField(){
    }
    public void addTextMessage(TextMessage message, boolean sent) {
        super.getItems().add(new TextMessageView(message, sent));
    }
}
