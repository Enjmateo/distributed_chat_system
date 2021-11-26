package com.insa.utils;
import java.io.Serializable;
import java.util.*;
import com.insa.communication.*;


public abstract class ObjectMessage implements Serializable {
    protected transient UUID sender;
    protected UUID recepter;
    protected Date date;

    public ObjectMessage(UUID sender, UUID recepter) {
        this.sender = sender;
        this.recepter = recepter;
        date = new Date();
    }
    public ObjectMessage(UUID sender){
        this.sender = sender;
        date = new Date();
    }

    abstract public void sendToDatabase(Database database);
    abstract public void action(Object object);

    public UUID getSender(){
        return sender;
    }

    public UUID getRecepter(){
        return recepter;
    }
}
