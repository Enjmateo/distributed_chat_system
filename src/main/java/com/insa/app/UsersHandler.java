package com.insa.app;
import java.util.*;
import java.net.*;
import java.util.stream.*;

import com.insa.communication.*;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;
import com.insa.utils.udp.ConfigMessage;
import com.insa.utils.udp.UDPObjectSender;

public class UsersHandler extends Thread {
    static private User self;
    static private ArrayList<User> users  = new ArrayList<User>();

    public UsersHandler() {
        start();
        init();
    }
    public synchronized static void init() {
        LogHandler.display(1,"  [+] Creating self user" );
        self = new User(null, Data.getUUID());
    }

    public static void updateSelfPseudo(String pseudo){
        self.setPseudo(pseudo);
        UDPObjectSender.invokeLater(
            new Runnable() {
                public void run() {
                    try {
                        UDPObjectSender.broadcastMessage(new ConfigMessage(pseudo, ConfigMessage.MessageType.KEEP_ALIVE), Consts.UDP_PORT);
                    } catch (Exception e) {
                        ExitHandler.error(e);
                    }
                }
            });
    }

    public synchronized static void addUser(User user) {
        users.add(user);

    }
    
    /**
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
        for(User user : users){
            if(user.isInstantAlive()){
                user.setAlive(true);
                user.setInstantAlive(false);
            }else{
                user.setAlive(false);
            }
        }
        }

    public synchronized static ArrayList<String> getPseudos(){
        if (users!= null) return new ArrayList<String>(users.stream().map(e -> e.getPseudo()).collect(Collectors.toList()));
        else return new ArrayList<String>();
    }

    public synchronized static User getLocalUser(){
        return self;
    }
    
    public synchronized static void listUsers() {
        LogHandler.display(4, "[+] Local user: " + self.toString() );
        LogHandler.display(4, "[+] List of received users: " );
        for (User user : users) {
            LogHandler.display(4, "    [>] Users : " + user.toString() );
        }
    }

    private void notifyAlive(){
        UDPObjectSender.invokeLater(
                new Runnable() {
                    public void run() {
                    try {
                        LogHandler.display(2,"[+] Broadcast KEEP_ALIVE");
                        UDPObjectSender.broadcastMessage( 
                        new ConfigMessage(UsersHandler.getLocalUser().getPseudo(), ConfigMessage.MessageType.KEEP_ALIVE), Consts.UDP_PORT);
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
