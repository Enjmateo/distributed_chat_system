package com.insa.app;

import com.insa.gui.*;
import com.insa.communication.*;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app..." );
        
        /* DB connection */ 
        System.out.print( "[+] Connecting to database..." );
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println( "Ok." );

        /* Launching main windows */
        MainWindows mw = new MainWindows();
        mw.launchMainFrame();
    }
}
