package com.insa.app;

import java.util.ArrayList;
import java.util.Arrays;

import com.insa.communication.*;
import com.insa.gui.*;
import com.insa.utils.*;
import com.insa.utils.udp.*;


public class App 
{
    public static Database DB;
    private UDPObjectReceiver udpReceiver;
    private static WelcomeWindows ww;

    /**
     * Entry point
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );

        Data.init();
        UsersHandler.init();

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
        
        //ArrayList<String> pseudoList = UsersHandler.getPseudos();

        System.out.println( "[+] Printing welcome windows" );
        ww = new WelcomeWindows();

        System.out.println( "[+] Reading config file");
        Data.reloadData();
    }

    /**
     * Main thread launch after welcome windows
     * @param Pseudo
     */
    public static void mainThread ()
    {
        System.out.println( "[+] Pseudo: " + UsersHandler.getLocalUser().getPseudo() );

        try {
            UDPObjectSender.broadcastMessage(new ConfigMessage(UsersHandler.getLocalUser().getPseudo(), ConfigMessage.MessageType.PSEUDO_SET), Consts.udpPort);
        } catch (Exception e) {ExitHandler.error(e);}

        for (String i : UsersHandler.getPseudos()) {
            System.out.println( "[>] " + i );
        }
        
        //UsersHandler.getPseudos().stream().
        /*
        System.out.println( "[+] Connecting to DB");
        DB =  new Database();
        try {
            DB.connect();
        } catch (Exception e) {ExitHandler.error(e);}
        
        ArrayList<ObjectMessage> messagesList = DB.getMessages(localUser.getUUID());

        MainWindows mw = new MainWindows();

        mw.setStatus("Idle (Debug)");
        mw.setPseudo(pseudo);
        mw.addUser(pseudo);

        for (ObjectMessage message : messagesList) {
            if (message.getClass() == TextMessage.class) {
                mw.addMessage(message.getSender().toString(), ((TextMessage)message).getContent());
            }
        }
        

        // Test
        mw.addUser("Alias");
        mw.addUser("Lambda");

        // LISTE DES OBJETS A INITIALISER

        */

        /* 
        -UDP Object Sender 
        */

        ExitHandler.exit();
    }
}
