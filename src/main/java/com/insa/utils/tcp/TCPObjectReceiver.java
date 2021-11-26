package com.insa.utils.tcp;
import java.io.*;
import java.net.*;
import com.insa.utils.*;

public class TCPObjectReceiver extends Thread {
    Socket inputSocket;
    InputStream inputStream;
    ObjectInputStream objectInputStream;

    private boolean running = true;

    public TCPObjectReceiver(Socket socket){
        this.inputSocket = socket;
        this.start();
    }
    public synchronized boolean isRunning() {return running;}
    public synchronized void close(boolean force ) throws IOException{
        if(force){
            objectInputStream.close();
            inputStream.close();
            inputSocket.close();
        }
        running = false;
    }
    public void run(){
        try {
            inputStream = inputSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        }catch(IOException e){
            throw new RuntimeException("Failed to open input stream");
        }
        while(isRunning()){
            try {
                ObjectMessage objectMessage = (ObjectMessage) objectInputStream.readObject();
                ObjectHandler.handleObject(objectMessage);
            }catch(Exception e){
                //TODO Gestion de l'echec de reception de message
            }

        }
        try{
            close(true);
        }catch(Exception e){
            throw new RuntimeException("Failed to close TCPObjectReceiver");
        }
    }
}
