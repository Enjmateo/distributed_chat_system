package com.insa.communication;

import com.insa.app.*;
import com.insa.utils.*;

public class TextMessage extends ObjectMessage{
    private User sender;
    private User receiver;

    private String content;

    public TextMessage (User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public void sendToDatabase(Data data) {
        // TODO...

    }

    public void action(Object object){
        // TODO...
    }
}
