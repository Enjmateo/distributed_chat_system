package com.insa.communication;

import com.insa.app.*;
import com.insa.utils.*;
import java.util.*;

public class TextMessage extends ObjectMessage implements Message {
    /*
    public static void main(String[] args) {
        
        Data data = new Data();
        TextMessage msg = new TextMessage(UUID.randomUUID(),UUID.randomUUID(),"Salut c'est un test");
        Database database = new Database();
        database.connect();
        msg.sendToDatabase(database);
    }
    */

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
        //database.executeUpdate("alter table messages alter column sendDate mediumint");
        //database.executeUpdate("INSERT INTO messages (sender, receiver, sendDate, contentID, messageType) VALUES ('"+super.sender.toString()+"', '"+super.recepter.toString()+"' ,"+super.date.getTime()+", (SELECT max(messageID) FROM text_message)+1 "+",0);");
        //System.out.println("Seconde requette");
        //database.executeUpdate("INSERT INTO text_message (messageID, messagePart,content) VALUES((SELECT max(messageID) FROM text_message)+1,null,'"+content+"')");
        // TODO...

    }


}
