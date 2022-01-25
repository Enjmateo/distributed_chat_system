package com.insa.gui.chattabs;


import com.insa.gui.chattabs.messagesField.MessagesField;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopBar extends HBox{
    Label pseudoLabel;

    public TopBar(StringProperty pseudo) {
        super();
        pseudoLabel = new Label();
        pseudoLabel.textProperty().bind(pseudo);

        super.getChildren().addAll(pseudoLabel);
    }

}
