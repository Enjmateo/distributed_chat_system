package com.insa.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;


public class FirewallSettings {
    public enum OSType {
        Windows, MacOS, Linux, Other
    };
    protected static OSType detectedOS;
    public static void init() {
        if (detectedOS == null) {
          String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
          if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
            detectedOS = OSType.MacOS;
          } else if (OS.indexOf("win") >= 0) {
            detectedOS = OSType.Windows;
          } else if (OS.indexOf("nux") >= 0) {
            detectedOS = OSType.Linux;
          } else {
            detectedOS = OSType.Other;
          }
        }
      }

    public void setFirewall(){ 
        if(detectedOS == null) init();
        switch (detectedOS) {
            case Windows: handleWindows(); break;
            case MacOS: break;
            case Linux: break;
            case Other: break;
        }
    }
    private void handleWindows(){
        final String CMD =  "netsh advfirewall firewall add rule name=\"ODD\" dir=in action=allow protocol=udp localport="+Consts.udpPort;

        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read command errors
            System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
}
