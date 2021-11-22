package com.insa.utils;

public class ExitHandler {
    public static void exit() {
        System.out.println( "[-] Exiting..." );
        System.exit(0);
    }

    public static void error(Exception e) {
        System.out.println( "[-] The program has encountered an error and crashes." );
        e.printStackTrace();
        System.exit(1);
    }
}
