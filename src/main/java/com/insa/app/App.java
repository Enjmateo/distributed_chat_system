package com.insa.app;

import com.insa.gui.*;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app..." );
        
        /* DB connection */ 
        System.out.print( "[+] Connecting to database..." );
        
        System.out.println( "Ok." );

        /* Launching main windows */
        MainWindows mw = new MainWindows();
        mw.start();
    }
}
