package com.insa.utils;
import java.io.Serializable;
import java.util.*;


public abstract class ObjectMessage implements Serializable {
    abstract void action(Object object);
    
}
