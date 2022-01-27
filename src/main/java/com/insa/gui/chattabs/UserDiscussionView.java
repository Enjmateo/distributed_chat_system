package com.insa.gui.chattabs;

import java.util.UUID;

import com.insa.app.User;
import com.insa.communication.Message;
import com.insa.communication.TextMessage;
import com.insa.gui.UserLabel;
import com.insa.gui.chattabs.messagesField.MessagesField;
import com.insa.utils.Consts;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserDiscussionView extends VBox{
    User user;
    TopBar topBar;
    SendBar sendbar;
    MessagesField messagesField;
    public UserDiscussionView(User user) {
        
        //TODO MVC
        user.setUserDiscussionView(this);

        this.user = user;

        topBar = new TopBar(user.getPseudoProperty());
        sendbar = new SendBar(user);
        messagesField = new MessagesField();

        VBox.setVgrow(messagesField, Priority.ALWAYS);

        //TODO move to constants
        super.setMinWidth(200);
        super.setPrefWidth(600);
        super.setSpacing(Consts.FIELDS_GAP);
        HBox.setHgrow(this,Priority.ALWAYS);

        super.getChildren().addAll(topBar, messagesField,sendbar);


    }
    public void addMessage(Message message, boolean sent) {
        messagesField.addTextMessage((TextMessage)message, sent);
    }
    
}
