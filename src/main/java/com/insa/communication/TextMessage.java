package com.insa.communication;

import com.insa.app.*;
import com.insa.utils.*;
import java.util.*;

public class TextMessage extends ObjectMessage{


    private String content;

    public TextMessage (UUID sender, UUID receiver, String content) {
        super(sender, receiver);
        this.content = content;
    }

    public void sendToDatabase(Database database) {
        database.executeQuery("INSERT INTO messages (sender, receiver, content) VALUES ("+super.sender.toString()+", "+super.recepter.toString()+" ,"+content+")");
        // TODO...

    }

    public void action(Object object){
        // TODO...
    }
}
