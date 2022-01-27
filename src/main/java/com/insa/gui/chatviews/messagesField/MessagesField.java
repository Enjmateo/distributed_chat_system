package com.insa.gui.chatviews.messagesField;

import com.insa.communication.TextMessage;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


public class MessagesField extends ListView<VBox> {
    public MessagesField(){
        //Disable selection of ListView elements
        //super.setMouseTransparent(true); //Problem : disable scrolling
        super.setFocusTraversable(false);
    }
    public void addTextMessage(TextMessage message, boolean sent) {
        super.getItems().add(new TextMessageView(message, sent));
    }
}
