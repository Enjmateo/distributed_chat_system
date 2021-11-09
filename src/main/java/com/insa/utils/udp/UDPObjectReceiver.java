package com.insa.utils.udp;

import com.insa.utils.*;
import java.net.*;
import java.io.*;

public class UDPObjectReceiver extends Thread {
    DatagramSocket socket;
    public UDPObjectReceiver() throws Exception{
        socket = new DatagramSocket();
        start();
    }

    public void run(){
        while(true){
            try{
              byte[] recvBuf = new byte[5000];
              DatagramPacket packet = new DatagramPacket(recvBuf,
                                                         recvBuf.length);
                                                         socket.receive(packet);
              int byteCount = packet.getLength();
              ByteArrayInputStream byteStream = new
                                          ByteArrayInputStream(recvBuf);
              ObjectInputStream is = new
                   ObjectInputStream(new BufferedInputStream(byteStream));
              ObjectMessage object = (ObjectMessage)is.readObject();
              is.close();
              //TODO call object Handler
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
        
    }

}
