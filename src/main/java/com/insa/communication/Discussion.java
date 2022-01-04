package com.insa.communication;

import java.util.ArrayList;
import java.util.UUID;

public class Discussion {
    ArrayList<Message> messages;

    public void addMessage(Message message) {
        messages.add(message);
    }

    Discussion(UUID uuid){
        messages = DatabaseHandler.getMessages(uuid);
    }
    
}
