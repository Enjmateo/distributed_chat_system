package com.insa.utils;
import java.io.Serializable;
import java.util.*;
import com.insa.communication.*;


public abstract class ObjectMessage implements Serializable {
    private transient UUID sender;
    private transient UUID recepter;
    private Date date;

    abstract public void sendToDatabase(Data data);
    abstract public void action(Object object);
    
}
