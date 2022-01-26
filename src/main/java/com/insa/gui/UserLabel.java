package com.insa.gui;


import com.insa.app.User;
import com.insa.utils.Consts;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;


public class UserLabel extends HBox {
    Label messageCount = new Label();
    Label pseudo= new Label();

    public UserLabel(User user){
        messageCount.textProperty().bind(user.getUnreadMessagesCount().asString());
        pseudo.textProperty().bind(user.getPseudoProperty());

        //TODO move dans des variables
        super.setSpacing(3);
        messageCount.setPadding(new Insets(2));
        messageCount.setTextFill(Color.WHITE);
        super.setCursor(Cursor.HAND);

        //Modify color if connected or not
        ObjectProperty<Background> background = messageCount.backgroundProperty();
        background.bind(Bindings.createObjectBinding(() -> {
            Color color = user.getAliveProperty().getValue()?Color.GREEN:Color.RED;
            BackgroundFill fill = new BackgroundFill(color, new CornerRadii(5),Insets.EMPTY);
            return new Background(fill);
        }, user.getAliveProperty()));

        super.getChildren().addAll(messageCount,pseudo);
    }
    
}
