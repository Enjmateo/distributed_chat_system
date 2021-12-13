package com.insa.app;
import java.util.*;
import java.net.*;
import java.util.stream.*;

import javax.swing.SwingUtilities;

import com.insa.communication.*;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.udp.ConfigMessage;
import com.insa.utils.udp.UDPObjectSender;

public class UsersHandler extends Thread {
    static private User self;
    static private ArrayList<User> users;

    public synchronized static void init() {
        users = new ArrayList<User>();
        self = new User(null, Data.getUUID());
    }

    public synchronized static void addUser(User user) {
        users.add(user);
    }
    
    /**
     * @deprecated
     * @param inetAddress
     * @return
     * @throws Exception
     */
    public synchronized static User getUserByInetAddress(InetAddress inetAddress) throws Exception{
        for (User user : users) {
            if (user.getInetAddress().equals(inetAddress)) return user;
        }
        throw new Exception("No user found with this IP address");
    }

    public synchronized static User getUserByUUID(UUID uuid) throws Exception{
        for(User user : users){
            if (user.getUUID().equals(uuid))return user;
        }
        throw new Exception("No user found with this UUID");
    }
    public synchronized static ArrayList<User> getAliveUsers(){
        return new ArrayList<User>(users.stream().filter(x->x.isAlive()).collect(Collectors.toList()));
    }
    public synchronized static void updateDeadUsers(){
        //Update dead users
        users.stream().filter(x->!x.isAlive()).forEach(e->e.setStatus(User.Status.DEAD));
        //Reset alive variable for all alive users
        getAliveUsers().stream().forEach(e -> 
            {e.setAlive(false);
                if(e.getStatus() == User.Status.DEAD){
                    e.setStatus(User.Status.ALIVE);
                }
            }
            );
    }

    public synchronized static ArrayList<String> getPseudos(){
        if (users!= null) return new ArrayList<String>(users.stream().map(e -> e.getPseudo()).collect(Collectors.toList()));
        else return new ArrayList<String>();
    }

    public synchronized static User getLocalUser(){
        return self;
    }
    
    public synchronized static void listUsers() {
        System.out.println( "[+] Local user: " + self.toString() );
        System.out.println( "[+] List of received users: " );
        for (User user : users) {
            System.out.println( "    [>] Users : " + user.toString() );
        }
    }

    private void notifyAlive(){
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                    try {
                        System.out.println("[+] Broadcast KEEP_ALIVE");
                        UDPObjectSender.broadcastMessage( 
                        new ConfigMessage(null,ConfigMessage.MessageType.KEEP_ALIVE),Consts.udpPort);
                    } catch (Exception e) {
                        ExitHandler.error(e);
                    }}
                 }
                );
    }

    //TODO: gerer la fermeture
    public void run() {
        try {
            while(true){
                notifyAlive();
                Thread.sleep(Consts.NOTIFY_ALIVE_PERIOD);
                notifyAlive();
                updateDeadUsers();
                Thread.sleep(Consts.NOTIFY_ALIVE_PERIOD);
                listUsers();
            }
        }catch(Exception e){
            ExitHandler.error(e);
        }

    }
}
