package com.insa.utils.tcp;
import java.io.*;
import java.net.*;

import com.insa.app.ObjectHandler;
import com.insa.app.User;
import com.insa.utils.*;

public class TCPObjectReceiver extends Thread {
    Socket inputSocket;
    InputStream inputStream;
    ObjectInputStream objectInputStream;

    User user;

    private boolean running = true;

    public TCPObjectReceiver(Socket socket, User user){
        this.user = user;
        this.inputSocket = socket;
        this.start();
    }
    public synchronized boolean isRunning() {return running;}
    public synchronized void close(boolean force ) throws IOException{
        if(force){
            if(objectInputStream!=null)objectInputStream.close();
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
            LogHandler.display(5,"[*] "+user.getPseudo()+" user got disconnected");
            try {
                user.disconnect();
            }catch(Exception ignored){

            }
        }
        while(isRunning()){
            try {
                ObjectMessage objectMessage = (ObjectMessage) objectInputStream.readObject();
                LogHandler.display(3,"[+] Receiving a TCP message");
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
