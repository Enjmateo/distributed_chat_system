package com.insa.utils;
import java.util.*;
import java.lang.reflect.*;

public class ObjectHandler {
    HashMap<Class,Object> parameters;

    //Permer d'ajouter une classe à handle
    public void addHandler(Class classe, Object object) {
        parameters.put(classe, object);
    }

    //Permet d'appeler la méthode action sur l'objet objectMessage avec en argument l'objet associé à la classe de objectMessage
    public void handleObject(ObjectMessage objectMessage){
        try{
            ObjectMessage.class.getMethod("action", Object.class).invoke(objectMessage, parameters.get(objectMessage.getClass()));
        }catch(Exception e){

        }
    }

}
