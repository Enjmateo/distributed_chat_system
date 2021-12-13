package com.insa.app;

import com.insa.communication.*;
import com.insa.gui.*;
import com.insa.utils.*;
import com.insa.utils.udp.*;

import java.util.*;


public class App 
{
    public static Database DB;
    //private UDPObjectReceiver udpReceiver;
    private static WelcomeWindows ww;


    /**
     * Entry point
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );
        System.out.println( "[+] Reading config file");

        Data.reloadData();
        UsersHandler.init();
        //FirewallSettings.setFirewall();

        UDPObjectReceiver udpReceiver = null;
        try {
            System.out.println( "[+] Discovery existing clients" );
            UDPObjectSender.init();
            udpReceiver = new UDPObjectReceiver();
        } catch (Exception e) {ExitHandler.error(e);}

        try {
            UDPObjectSender.broadcastMessage(new ConfigMessage(), Consts.udpPort);

            System.out.println( "[+] Wainting responses..." );
            Thread.sleep(Consts.discoveryTimeoutMs);
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

        try {
            UDPObjectSender.broadcastMessage(new ConfigMessage(UsersHandler.getLocalUser().getPseudo(), ConfigMessage.MessageType.KEEP_ALIVE), Consts.udpPort);
        } catch (Exception e) {ExitHandler.error(e);}

        UsersHandler.listUsers();
        
        //UsersHandler.getPseudos().stream().
        
        System.out.println( "[+] Connecting to DB");
        DB =  new Database();
        try {
            DB.connect();

            // DEBUG - TO REMOVE
            System.out.println( "[!] Testing DB.");
            TextMessage msg = new TextMessage(UUID.randomUUID(), "J'adore debug le SQL !");
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
        

        // Test
        mw.addUser("Alias");
        mw.addUser("Lambda");

        // LISTE DES OBJETS A INITIALISER
        /* 
        -UDP Object Sender 
        */
        
        //ExitHandler.exit();
    }
}
