package com.insa.app;
import java.util.*;
import java.net.*;
import java.util.stream.*;

public class UsersHandler {
    static private User self;
    static private ArrayList<User> users;

    public synchronized static void addUser(User user) {
        users.add(user);
    }
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
    public synchronized static ArrayList<User> getConnectedUsers(){
        return new ArrayList<User>(users.stream().filter(x->x.isConnected()).collect(Collectors.toList()));
    }

    public synchronized static User getLocalUser(){
        return self;
    }
}
