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

    public void sendToDatabase() {
        try {
            int contentId = DatabaseHandler.newContentId();
            String part;

            // Adding message in main DB
            DatabaseHandler
                    .executeUpdate("INSERT INTO messages (sender, receiver, sendDate, contentID, messageType) VALUES ('"
                            + super.sender.toString() + "', '"
                            + super.receiver.toString() + "' ,"
                            + super.date.getTime()
                            + ", " + Integer.toString(contentId)
                            + ", 0);");

            // Adding message into Text message DB
            int partId = 0;
            String copycontent = new String(this.content);
            while (true) {
                try {
                    part = copycontent.substring(0, Consts.MAX_TEXT_LENGTH);
                    copycontent = copycontent.substring(Consts.MAX_TEXT_LENGTH);
                    DatabaseHandler.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES("
                            + Integer.toString(contentId) + ", "
                            + partId + ",'"
                            + part + "')");
                    partId++;
                } catch (Exception e) {
                    // Adding last part
                    DatabaseHandler.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES("
                            + Integer.toString(contentId) + ", "
                            + partId + ",'"
                            + copycontent + "')");
                    break;
                }
            }
        }

        catch (Exception e) {
            DatabaseHandler.errorDB();
        }
    }
}
