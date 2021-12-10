package com.insa.utils.udp;

import com.insa.app.ObjectHandler;
import com.insa.utils.*;
import java.net.*;
import java.io.*;

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
        while (isRunning()) {
            try {
                byte[] recvBuf = new byte[5000];
                DatagramPacket packet = new DatagramPacket(recvBuf,
                        recvBuf.length);
                socket.receive(packet);

                System.out.println("[+] Received message UDP ");
                // int byteCount = packet.getLength();
                ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
                ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                ObjectMessage object = (ObjectMessage) is.readObject();
                if (object instanceof ConfigMessage) {
                    ((ConfigMessage) object).setAddress(packet.getAddress());
                }
                is.close();
                ObjectHandler.handleObject(object);
            } catch (IOException e) {
                System.err.println("Exception:  " + e);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            close(true);
        } catch (Exception e) {
        }
    }

    public synchronized void close(boolean force) throws IOException {
        if (force) {
            socket.close();
        }
        this.running = false;
    }

}
