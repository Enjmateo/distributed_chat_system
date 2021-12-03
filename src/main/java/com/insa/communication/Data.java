package com.insa.communication;

import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;

import java.io.*;
import org.json.*;
import java.util.UUID;

public class Data {
    private static String dbURL = null;

    private static String dbUsername = null;
    private static String dbPassword = null;

    private static UUID uuid = null;


    private static void init(){
        File newConfig = new File(Consts.configFile);
        try {
            newConfig.createNewFile();
        } catch (IOException e) {ExitHandler.error(e);}

        FileWriter configWriter;
        try {
            configWriter = new FileWriter(Consts.configFile);
            configWriter.write("{\"uuid\":\"" + UUID.randomUUID().toString() + "\",\"dbAddr\":\"localhost\",\"dbUser\":\"admin\",\"dbPassword\":\"admin\"}");
            configWriter.close();
        } catch (IOException e) {ExitHandler.error(e);}
    }

    public static void reloadData(){
        String text = "";

        try {
            InputStream fileInput = new FileInputStream(Consts.configFile);
            text = new String(fileInput.readAllBytes());
            fileInput.close();
        }catch(IOException e) {
            System.out.println("[!] Config file not found, creating new one...");
            init();
            reloadData();
            return;
        }

        JSONObject json = new JSONObject(text);

        uuid = UUID.fromString(json.getString("uuid"));
        dbURL = json.getString("dbAddr");
        dbUsername = json.getString("dbUser");
        dbPassword = json.getString("dbPassword");

        printConfig();
    }

    public Data () {
        reloadData();  
    }


    public static void printConfig(){
        System.out.println("[+] Loaded config: ");
        System.out.println("   [>] UUID: " + uuid.toString());
        System.out.println("   [>] DB Addr: " + dbURL);
        System.out.println("   [>] DB User: " + dbUsername);
        System.out.println("   [>] DB passwd: " + dbPassword);
    }

    protected static String getDBUrl() {
        if (dbURL == null) {
            reloadData();
            return getDBUrl();
        } else {
            return dbURL;
        }
    }

    protected static String getDBUsername() {
        if (dbUsername == null) {
            reloadData();
            return getDBUsername();
        } else {
            return dbUsername;
        }
    }

    protected static String getDBPassword() {
        if (dbPassword == null) {
            reloadData();
            return getDBPassword();
        } else {
            return dbPassword;
        }
    }

    public static UUID getUUID(){
        if (uuid == null) {
            reloadData();
            return getUUID();
        } else {
            return uuid;
        }
    }
}
