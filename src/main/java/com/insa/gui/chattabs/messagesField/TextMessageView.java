package com.insa.gui.chattabs.messagesField;

import com.insa.communication.TextMessage;
import com.insa.utils.Consts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextMessageView extends VBox {
    Label textLabel;
    Label timeLabel;
    boolean sent;

    public TextMessageView(TextMessage message, boolean sent){
        super.setPrefWidth(10);
        //super.setMaxWidth(Consts.MESSAGES_WIDTH);
        this.sent = sent;
        this.textLabel = new Label(message.getContent());
        this.timeLabel= new Label(Consts.DATE_FORMAT.format(message.getSendTime()));
        this.timeLabel.setTextFill(Color.GREY);

        textLabel.setTextFill(Color.WHITE);
        textLabel.setWrapText(true);
        //textLabel.setMaxWidth(Consts.MESSAGES_WIDTH);
        textLabel.setPadding(new Insets(Consts.MESSAGE_PADDING));
        textLabel.setBackground(
            new Background(new BackgroundFill(
                sent ? Consts.SENT_MESSAGE_BACKGROUND_COLOR:Consts.RECEIVED_MESSAGE_BACKGROUND_COLOR , 
                new CornerRadii(Consts.MESSAGES_ROUNDING), 
                Insets.EMPTY)));
        super.setAlignment(sent?Pos.CENTER_RIGHT:Pos.CENTER_LEFT);
        super.getChildren().addAll(textLabel,timeLabel);
    }
    
}
