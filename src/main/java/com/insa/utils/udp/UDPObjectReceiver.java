package com.insa.utils.udp;

import java.net.DatagramSocket;

public class UDPObjectReceiver extends Thread {
    DatagramSocket socket;
    public UDPObjectReceiver() throws Exception{
        socket = new DatagramSocket();
        start();
    }

    public void run(){

    }

}
