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
        String part;

        // Adding message in main DB
        database.executeUpdate("INSERT INTO messages (sender, receiver, sendDate, contentID, messageType) VALUES ('"
                + super.sender.toString() + "', '"
                + super.receiver.toString() + "' ,"
                + super.date.getTime()
                + ", " + Integer.toString(contentId)
                + ", 0);");

        // Adding message into Text message DB
        int partId = 0;
        while (true) {
            try {
                part = content.substring(0, Consts.MAX_TEXT_LENGTH);
                content = content.substring(Consts.MAX_TEXT_LENGTH);
                database.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES("
                        + Integer.toString(contentId) + ", "
                        + partId + ",'"
                        + part + "')");
                partId++;
            } catch (Exception e) {
                // Adding last part
                database.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES("
                + Integer.toString(contentId) + ", "
                + partId + ",'"
                + content + "')");
                break;
            }
        }
    }
}
