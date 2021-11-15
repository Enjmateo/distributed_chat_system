package com.insa.utils.tcp;

import java.net.Socket;
import java.io.*;
import com.insa.utils.*;

public class TCPObjectSender {
    Socket outputSocket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;

    private boolean running = false;

    public synchronized boolean isRunning() {return running;}

    public synchronized void close() throws IOException {
        if(running){
            objectOutputStream.close();
            outputStream.close();
            outputSocket.close();
            running = false;
        }
    }

    public TCPObjectSender(Socket socket) throws Exception{
        this.outputSocket = socket;
        this.outputStream = outputSocket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
    }

    synchronized void sendMessageObject(ObjectMessage message) throws Exception{
        if(running){
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }catch(Exception e){
                running = false;
                throw e;
            }
        }else{
            throw new IOException("TCPObjectSender not running");
        }
    }
}
