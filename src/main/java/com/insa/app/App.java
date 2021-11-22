package com.insa.app;

import com.insa.communication.*;
import com.insa.gui.*;

public class App 
{
    public Database DB;

    /**
     * Entry point
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );
   
        System.out.println( "[+] Printing welcome windows" );
        WelcomeWindows ww = new WelcomeWindows();

        // Debug
        ww.skipWindows();
    }

    /**
     * Main thread launch after welcome windows
     * @param Pseudo
     */
    public static void mainThread ( String pseudo )
    {
        System.out.println( "[+] Pseudo: " + pseudo );
        
        MainWindows mw = new MainWindows();

        mw.setStatus("Idle (Debug)");
        mw.setPseudo(pseudo);
        mw.addUser(pseudo);

        // Test
        mw.addUser("Alias");
        mw.addUser("Lambda");
    }
}
