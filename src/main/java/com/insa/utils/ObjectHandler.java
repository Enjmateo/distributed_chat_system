package com.insa.utils;
import java.util.*;
import java.lang.reflect.*;

public class ObjectHandler {
    HashMap<Class, Tuple <Method, Object>> methods;

    public void addHandler(Class classe, Method method, Object object) {
        methods.put(classe, new Tuple<Method, Object>(method, object));
    }

    public void handleObject(Object object){
        Tuple<Method, Object> action;
    }

}
