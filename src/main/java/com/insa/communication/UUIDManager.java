package com.insa.communication;
import java.util.UUID;

public class UUIDManager {
    protected static String createNewUUID(){
        return UUID.randomUUID().toString();
    }
}
