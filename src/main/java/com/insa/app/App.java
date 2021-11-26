package com.insa.app;

import com.insa.communication.*;
import com.insa.gui.*;
import com.insa.utils.ExitHandler;

public class App 
{
    public static Database DB;
    public static User localUser;

    /**
     * Entry point
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "[+] Launching app" );
   
        System.out.println( "[+] Printing welcome windows" );
        WelcomeWindows ww = new WelcomeWindows();        

        System.out.println( "[+] Creating base object" );

        localUser = new User(null, null, true);

        System.out.println( "[+] Reading config file");
        Data.reloadData();

        try {
            localUser.setUUID(Data.getUUID());
        } catch (Exception e) {ExitHandler.error(e);}
        
        // Debug
        //ww.skipWindows();
    }

    /**
     * Main thread launch after welcome windows
     * @param Pseudo
     */
    public static void mainThread ( String pseudo )
    {
        System.out.println( "[+] Pseudo: " + pseudo );
        localUser.setPseudo(pseudo);

        System.out.println( "[+] Connecting to DB");
        DB =  new Database();
        try {
            DB.connect();
            DB.printInfo();
        } catch (Exception e) {ExitHandler.error(e);}
        
        MainWindows mw = new MainWindows();

        mw.setStatus("Idle (Debug)");
        mw.setPseudo(pseudo);
        mw.addUser(pseudo);

        // Test
        mw.addUser("Alias");
        mw.addUser("Lambda");
    }
}
