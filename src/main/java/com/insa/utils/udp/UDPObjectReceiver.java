package com.insa.utils.udp;

import com.insa.utils.*;
import java.net.*;
import java.io.*;

public class UDPObjectReceiver extends Thread {
    DatagramSocket socket;
    ObjectHandler handler;
    boolean running = true;
    public UDPObjectReceiver(ObjectHandler handler) throws Exception{
        socket = new DatagramSocket();
        start();
    }

    private synchronized boolean isRunning(){return running;}

    public void run(){
        while(isRunning()){
            try{
              byte[] recvBuf = new byte[5000];
              DatagramPacket packet = new DatagramPacket(recvBuf,
                                                         recvBuf.length);
                                                         socket.receive(packet);
              //int byteCount = packet.getLength();
              ByteArrayInputStream byteStream = new
                                          ByteArrayInputStream(recvBuf);
              ObjectInputStream is = new
                   ObjectInputStream(new BufferedInputStream(byteStream));
              ObjectMessage object = (ObjectMessage)is.readObject();
              is.close();
              handler.handleObject(object);
            }
            catch (IOException e){
              System.err.println("Exception:  " + e);
              e.printStackTrace();
            }
            catch (ClassNotFoundException e){ 
                e.printStackTrace(); 
            }
        }
    }

    public synchronized void close(){
        this.running = false;
    }

}
