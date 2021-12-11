package com.insa.utils.udp;

import com.insa.app.ObjectHandler;
import com.insa.utils.*;
import java.net.*;
import java.io.*;
import com.insa.app.UsersHandler;

public class UDPObjectReceiver extends Thread {
    DatagramSocket socket;
    boolean running = true;

    public UDPObjectReceiver() throws Exception {
        socket = new DatagramSocket(Consts.udpPort);
        start();
    }

    private synchronized boolean isRunning() {
        return running;
    }

    public void run() {
        DatagramPacket packet;
        while (isRunning()) {
            try {
                byte[] recvBuf = new byte[5000];
                packet = new DatagramPacket(recvBuf,
                        recvBuf.length);
                System.out.println("[#] Waiting for more UDP packets...");
                socket.receive(packet);
                System.out.println("[+] Received message UDP from " + packet.getAddress().toString());
                
                ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
                ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                ObjectMessage object = (ObjectMessage) is.readObject();
                
                if (object == null){
                    System.out.println("   [!] The message is null!");
                }

                if(object.getSender().equals(UsersHandler.getLocalUser().getUUID())){
                    System.out.println(" [skiped]");
                    is.close();
                    continue;
                }
                
                System.out.println("");
                if (object instanceof ConfigMessage) {
                    System.out.println("   [>] Message type: Config message ");
                    ((ConfigMessage) object).setAddress(packet.getAddress());
                }


                is.close();
                byteStream.close();
                ObjectHandler.handleObject(object);
            } catch (Exception e) {
                System.out.println("Exception");
                ExitHandler.error(e);
            }
        }
        try {
            close(true);
        } catch (Exception e) {
            ExitHandler.error(e);
        }
    }

    public synchronized void close(boolean force) throws IOException {
        if (force) {
            socket.close();
        }
        this.running = false;
    }

}
