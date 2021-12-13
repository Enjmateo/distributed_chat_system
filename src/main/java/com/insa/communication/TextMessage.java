package com.insa.communication;

import com.insa.utils.*;
import java.util.*;

public class TextMessage extends ObjectMessage implements Message {

    private String content;

    public String getContent() {
        return content;
    }
    public TextMessage (UUID sender, UUID receiver, String content) {
        super(sender,receiver);
        this.content = content;
    }

    public TextMessage (UUID receiver, String content) {
        super(receiver);
        this.content = content;
    }
    public void display(){}

    public void sendToDatabase(Database database) {
        database.executeUpdate("INSERT INTO messages (messageID, sender, receiver, sendDate, contentID, messageType) VALUES (@id := ifnull(@id, 0) + 1, '" 
            + super.sender.toString()+"', '" 
            + super.receiver.toString()+"' ," 
            + super.date.getTime() 
            + ", 0" 
            + ", 0);");
        //database.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES((SELECT max(messageID) FROM text_message)+1,null,'"+content+"')");
        // INSERT INTO messages (messageID, sender, receiver, sendDate, contentID, messageType) VALUES (0, '5cc3661c-441e-4293-a37f-a40fd4ff3374', 'b0cfc92e-97f9-4d2d-b230-b1bb8d116a8e' ,1639391150521, 0 ,0);
    }


}
