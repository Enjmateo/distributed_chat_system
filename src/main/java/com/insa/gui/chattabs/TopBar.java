package com.insa.gui.chattabs;


import com.insa.gui.chattabs.messagesField.MessagesField;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TopBar extends HBox{
    Label pseudoLabel;

    public TopBar(StringProperty pseudo) {
        super();
        pseudoLabel = new Label();
        pseudoLabel.textProperty().bind(pseudo);
        pseudoLabel.setFont(Font.font(Font.getDefault().toString(),FontWeight.BOLD,15));

        super.getChildren().addAll(pseudoLabel);
    }

}
