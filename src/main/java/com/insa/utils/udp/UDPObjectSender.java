package com.insa.utils.udp;
import com.insa.utils.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPObjectSender {
    private static DatagramSocket socket = null;

    public UDPObjectSender() throws Exception{
        socket = new DatagramSocket();
    }


    private void send(ObjectMessage message, InetAddress address, int port) throws Exception {
        DatagramPacket packet = objectToDatagramPacket(message, address, port);
        socket.send(packet);
    }
    
    public void sendMessage(ObjectMessage message, InetAddress address, int port) throws Exception {
        socket.setBroadcast(false);
        send(message, address, port);
    }

    public void broadcastMessage(ObjectMessage broadcastMessage, int port) throws Exception {
        socket.setBroadcast(true);
        ArrayList<InetAddress> adresses = listAllBroadcastAddresses();
        for(InetAddress address : adresses) {
            send(broadcastMessage, address, port);
        }

    }

    private ArrayList<InetAddress> listAllBroadcastAddresses() throws SocketException {
        ArrayList<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
    
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
    
            networkInterface.getInterfaceAddresses().stream() 
              .map(a -> a.getBroadcast())
              .filter(Objects::nonNull)
              .forEach(broadcastList::add);
        }
        return broadcastList;
    }

    public DatagramPacket objectToDatagramPacket(ObjectMessage message, InetAddress address, int port) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        final byte[] data = baos.toByteArray();

        return new DatagramPacket(data, data.length, address, port);
    }
}
