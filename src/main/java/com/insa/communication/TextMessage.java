package com.insa.communication;

import com.insa.utils.*;
import java.util.*;

public class TextMessage extends ObjectMessage implements Message {

    private String content;

    public String getContent() {
        return content;
    }

    public TextMessage(UUID sender, UUID receiver, String content) {
        super(sender, receiver);
        this.content = content;
    }

    public TextMessage(UUID receiver, String content) {
        super(receiver);
        this.content = content;
    }

    public void display() {
    }

    public void sendToDatabase(Database database) {
        int contentId = database.newContentId();

        // Adding message in main DB
        database.executeUpdate("INSERT INTO messages (sender, receiver, sendDate, contentID, messageType) VALUES ('"
                + super.sender.toString() + "', '"
                + super.receiver.toString() + "' ,"
                + super.date.getTime()
                + ", " + Integer.toString(contentId)
                + ", 0);");
        
        // Adding message into Text message DB
        database.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES("
                + Integer.toString(contentId) 
                + ", 0,'"+ content + "')");

        // INSERT INTO messages (messageID, sender, receiver, sendDate, contentID,
        // messageType) VALUES (0, '5cc3661c-441e-4293-a37f-a40fd4ff3374',
        // 'b0cfc92e-97f9-4d2d-b230-b1bb8d116a8e' ,1639391150521, 0 ,0);
    }

}
