package com.insa.utils;
import java.io.Serializable;
import java.util.*;
import com.insa.communication.*;


public abstract class ObjectMessage implements Serializable {
    protected UUID sender;
    protected UUID receiver;
    protected Date date;

    public ObjectMessage(UUID receiver) {
        this.sender = Data.getUUID();
        date = new Date();
        this.receiver = receiver;

    }


    public ObjectMessage(UUID sender, UUID receiver) {
        this.sender = Data.getUUID();
        date = new Date();
        this.receiver = receiver;
        this.sender = sender;

    }
    public ObjectMessage() {
        this.sender = Data.getUUID();
        date = new Date();
    }
    
    public Date getSendTime() {
        return date;
    }
    public UUID getSender(){
        return sender;
    }

    public UUID getReceiver(){
        return receiver;
    }
}
