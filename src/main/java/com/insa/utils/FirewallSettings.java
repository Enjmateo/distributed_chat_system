package com.insa.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.ShellAPI;
import com.sun.jna.platform.win32.WinDef;

public class FirewallSettings {
    public enum OSType {
        Windows, MacOS, Linux, Other
    };
    public interface Shell32 extends ShellAPI {
      Shell32 INSTANCE = (Shell32)Native.loadLibrary("shell32", Shell32.class);

      WinDef.HINSTANCE ShellExecuteA(WinDef.HWND hwnd,
                                    String lpOperation,
                                    String lpFile,
                                    String lpParameters,
                                    String lpDirectory,
                                    int nShowCmd);
    }
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

    public static void setFirewall(){ 
        if(detectedOS == null) init();
        switch (detectedOS) {
            case Windows: handleWindows(); break;
            case MacOS: handleMacOS(); break;
            case Linux: handleLinux(); break;
            case Other: break;
        }
    }

    private static void  handleWindows(){
        //Check if rule already exist :
        /*
        Process process;
        try {
          process = Runtime.getRuntime().exec("netsh advfirewall firewall show rule ODD");
          //process = Runtime.getRuntime().exec("netsh firewall show state | find \""+Consts.udpPort+"\"");
          //process = Runtime.getRuntime().exec("netsh firewall show state");

          BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String line = stdInput.readLine();
          if (line!="") {
            System.out.println("Line :"+line);
            System.out.println("[-] Firewall rule already set");
            return;
          }else{
            
            System.out.println("Line :"+line);
          }
        } catch (IOException e) {
          ExitHandler.error(e);
        }
        */

        System.out.println("[+] Setting firewall rule");
        final String CMDin =  "/S /C netsh advfirewall firewall add rule name=\"ODD\" dir=in action=allow protocol=udp localport=" + Consts.UDP_PORT;
        final String CMDout =  "/S /C netsh advfirewall firewall add rule name=\"ODD\" dir=out action=allow protocol=udp localport=" + Consts.UDP_PORT;
        WinDef.HWND h = null;
        Shell32.INSTANCE.ShellExecuteA(h, "runas", "cmd.exe", CMDin, null, 1);
        Shell32.INSTANCE.ShellExecuteA(h, "runas", "cmd.exe", CMDout, null, 1);
    }

    public static void handleLinux(){
      //TODO: mettre la commande + élévation?
      final String CMD ="iptables";
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
          ExitHandler.error(e);
      }
    }

    private static void handleMacOS(){ 
      //TODO à compléter
      // https://www.freebsd.org/cgi/man.cgi?ipfw(8)
      final String CMD = "ipfw";
      try {
        Process process = Runtime.getRuntime().exec(CMD);
      } catch (IOException e) {ExitHandler.error(e);} 
    }        
}
