package com.insa.app;
import java.util.*;
import java.net.*;
public class UsersHandler {
    static private User self;
    static private ArrayList<User> users;

    public static void addUser(User user) {
        users.add(user);
    }
    public static User getUserByInetAddress(InetAddress inetAddress) throws Exception{
        for (User user : users) {
            if (user.getInetAddress().equals(inetAddress)) return user;
        }
        throw new Exception("No user found with this IP address");
    }
    public static User getUserByUUID(UUID uuid) throws Exception{
        for(User user : users){
            if (user.getUUID().equals(uuid))return user;
        }
        throw new Exception("No user found with this UUID");
    }
}
