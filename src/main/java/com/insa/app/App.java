package com.insa.app;

import java.util.*;

import com.insa.communication.*;
import com.insa.gui.*;
import com.insa.utils.*;
import com.insa.utils.udp.*;


public class App 
{
    public static Database DB;
    private static WelcomeWindows ww;
    private  static UsersHandler uh;
    private static UDPObjectSender udpos;
    private static UDPObjectReceiver udpor;
    
    
    /**
    * Entry point
    * @param args
    */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );
        System.out.println( "[+] Reading config file");
        
        Data.reloadData();
        uh = new UsersHandler(); 
        udpos = new UDPObjectSender();
        //FirewallSettings.setFirewall();
        
        
        try {
            System.out.println( "[+] Discovery existing clients" );
            udpor = new UDPObjectReceiver();
        } catch (Exception e) {ExitHandler.error(e);}
        
        try {
            UDPObjectSender.broadcastMessage(new ConfigMessage(), Consts.UDP_PORT);
            
            System.out.println( "[+] Wainting responses..." );
            Thread.sleep(Consts.DISCOVERY_TIMEOUT_MS);
        } catch (Exception e) {ExitHandler.error(e);}
        
        
        UsersHandler.listUsers();        
        
        //ArrayList<String> pseudoList = UsersHandler.getPseudos();
        
        System.out.println( "[+] Printing welcome windows" );
        ww = new WelcomeWindows();
        
    }
    
    /**
    * Main thread launch after welcome windows
    * @param Pseudo
    */
    public static void mainThread ()
    {
        String pseudo = UsersHandler.getLocalUser().getPseudo();
        System.out.println( "[+] Pseudo: " + pseudo );
        
        
        //On envoie un notify avec le pseude
        UDPObjectSender.invokeLater(
        new Runnable() {
            public void run() {
                try {
                    UDPObjectSender.broadcastMessage(new ConfigMessage(UsersHandler.getLocalUser().getPseudo(), ConfigMessage.MessageType.KEEP_ALIVE), Consts.UDP_PORT);
                } catch (Exception e) {
                    ExitHandler.error(e);
                }
            }
        });
        
        
        UsersHandler.listUsers();
        
        //UsersHandler.getPseudos().stream().
        
        System.out.println( "[+] Connecting to DB");
        DB =  new Database();
        try {
            DB.connect();
            
            // DEBUG - TO REMOVE
            System.out.println( "[!] Testing DB.");
            //DB.resetDB();
            TextMessage msg = new TextMessage(UUID.randomUUID(), "Bon injection maintenant");
            msg.sendToDatabase(DB);
            
        } catch (Exception e) {ExitHandler.error(e);}
        
        ArrayList<ObjectMessage> messagesList = DB.getMessages(UsersHandler.getLocalUser().getUUID());
        
        MainWindows mw = new MainWindows();
        
        mw.setStatus("Idle (Debug)");
        mw.setPseudo(pseudo);
        
        for (ObjectMessage message : messagesList) {
            if (message.getClass() == TextMessage.class) {
                mw.addMessage(message.getSender().toString(), ((TextMessage)message).getContent());
            }
        }
        
        // LISTE DES OBJETS A INITIALISER
        /* 
        -UDP Object Sender 
        */
        
        //ExitHandler.exit();
    }
}
