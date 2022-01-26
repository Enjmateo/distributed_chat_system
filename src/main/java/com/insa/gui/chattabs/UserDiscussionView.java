package com.insa.gui.chattabs;

import java.util.UUID;

import com.insa.app.User;
import com.insa.communication.Message;
import com.insa.communication.TextMessage;
import com.insa.gui.UserLabel;
import com.insa.gui.chattabs.messagesField.MessagesField;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserDiscussionView extends VBox{
    User user;
    TopBar topBar;
    SendBar sendbar;
    MessagesField messagesField;
    VBox mainLayout = new VBox();
    public UserDiscussionView(User user) {
        
        //TODO MVC
        user.setUserDiscussionView(this);

        this.user = user;

        topBar = new TopBar(user.getPseudoProperty());
        sendbar = new SendBar(user);
        messagesField = new MessagesField();

        messagesField.addTextMessage(new TextMessage(UUID.randomUUID(), "ceci est un test 1"),true);
        messagesField.addTextMessage(new TextMessage(UUID.randomUUID(), "ceci est un autre test avec un texte tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres tres long"),false);
        messagesField.addTextMessage(new TextMessage(UUID.randomUUID(), "ceci est encore un autre test\navec un espace au milieu cette foi"),false);

        //TODO move to constants
        super.setMinHeight(400);
        super.setMinWidth(600);

        super.getChildren().addAll(topBar, messagesField,sendbar);


    }
    public void addMessage(Message message, boolean sent) {
        messagesField.addTextMessage((TextMessage)message, sent);
    }
    
}
