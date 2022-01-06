package com.insa.gui;

import com.insa.app.User;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserTab extends Tab{
    User user;
    TopBar topBar;
    SendBar sendbar;
    MessagesField messageField;
    VBox mainLayout = new VBox();
    public UserTab(User user) {
        super(user.getPseudo());
        this.user = user;
        super.setContent(mainLayout);
        mainLayout.getChildren().addAll(topBar, messageField,sendbar);

    }
    
}
