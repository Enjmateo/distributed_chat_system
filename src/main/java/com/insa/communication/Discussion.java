package com.insa.communication;

import java.util.ArrayList;
import java.util.UUID;

public class Discussion {
    ArrayList<Message> messages;
    int unreadMessages = 0;

    public void addMessage(Message message) {
        messages.add(message);
    }
    Discussion(){ 
        messages=new ArrayList<Message>(); 
    }

    Discussion(UUID uuid){
        messages = DatabaseHandler.getMessages(uuid);
    }
    
}
