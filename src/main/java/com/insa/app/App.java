package com.insa.app;

import com.insa.gui.*;

public class App 
{
    /**
     * Entry point
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );
   
        System.out.println( "[+] Printing welcome windows" );
        WelcomeWindows ww = new WelcomeWindows();
        ww.start();
        ww.dispose();
    }

    /**
     * Main thread launch after welcome windows
     * @param Pseudo
     */
    public static void mainThread ( String Pseudo )
    {
        System.out.println( "[+] Pseudo: " + Pseudo );
        MainWindows mw = new MainWindows();
        mw.start();

    }
}
