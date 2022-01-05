package com.insa.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class LogHandler {
    // 1 Fonctionnement général
    // 2 Réseau  UDP / ConfigMessages
    // 3 Réseau TCP 
    // 4 Etats
    // 5 Problèmes
    // 6 SPAM
    // 5, 6, 7, 8, 9, 10.....
    static ArrayList<Integer> logsToDisplay = new ArrayList<Integer>();

    public static void addAll(){
        logsToDisplay.addAll(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10));
    }
    public static void set(Integer ... numbers){
        logsToDisplay.clear();
        logsToDisplay.addAll(Arrays.asList(numbers));
    }
    public static void add(Integer ... numbers){
        logsToDisplay.addAll(Arrays.asList(numbers));
    }
    public static void remove(Integer number){
        logsToDisplay.remove(number);
    }
    public static void display(Integer logType, String logMessage){
        if(logsToDisplay.contains(logType)) System.out.println(logMessage);
    }
}
