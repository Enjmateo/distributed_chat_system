package com.insa.communication;

public interface Message {
    abstract public void sendToDatabase(Database database);
    abstract public void display();
}
