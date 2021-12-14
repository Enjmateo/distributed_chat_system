package com.insa.utils.udp;
import com.insa.utils.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPObjectSender extends Thread {
    private static DatagramSocket socket = null;
    private static ArrayList<Runnable> tasks = new ArrayList<Runnable>();

    public void run() {
        try {
            socket = new DatagramSocket(Consts.UDP_PORT);
        } catch (SocketException e) {
            ExitHandler.error(e);
        }
        Runnable r = null;
        while (true) {
            synchronized (this){

                if(!tasks.isEmpty())r=tasks.remove(0);
            }
            if(r!=null){
                r.run();
                r = null;
            }
        }
    }

    public static synchronized void invokeLater(Runnable runnable) {
        tasks.add(runnable);
    }

    /**
     * @throws Exception
     */
    public UDPObjectSender() {
        start();
    }


    private static void send(ObjectMessage message, InetAddress address, int port) throws Exception {
        DatagramPacket packet = objectToDatagramPacket(message, address, port);
        socket.send(packet);
    }
    
    public static void sendMessage(ObjectMessage message, InetAddress address, int port) throws Exception {
        System.out.println("[+] Sending UDP message to " + address.toString() + ":" + port);
        socket.setBroadcast(false);
        send(message, address, port);
    }

    public static void broadcastMessage(ObjectMessage broadcastMessage, int port) throws Exception {
        socket.setBroadcast(true);
        ArrayList<InetAddress> adresses = listAllBroadcastAddresses();
        for(InetAddress address : adresses) {
            System.out.println("[+] Broadcast sur "+address.toString()+" port : "+port); 
            send(broadcastMessage, address, port);
        }

    }

    //Récupération des adresses de boradcast
    private static ArrayList<InetAddress> listAllBroadcastAddresses() throws SocketException, UnknownHostException {
        ArrayList<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress toDrop = InetAddress.getByName("0.0.0.0");
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
    
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            networkInterface.getInterfaceAddresses().stream() 
              .map(a -> a.getBroadcast())
              .filter(Objects::nonNull)
              .filter(m->!m.equals(toDrop))
              .forEach(broadcastList::add);
        }
        return broadcastList;
    }

    //Conversion des objets en datagramPacket
    public static DatagramPacket objectToDatagramPacket(ObjectMessage message, InetAddress address, int port) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(Consts.MAX_UDP_PACKET_SIZE);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        final byte[] data = baos.toByteArray();

        return new DatagramPacket(data, data.length, address, port);
    }
}
