package com.insa.communication;

import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;

import java.io.*;
import org.json.*;
import java.util.UUID;

public class Data {
    private static String dbURL = null;

    private static String dbUsername = null;
    private static String dbPassword = null;

    private static UUID uuid = null;


    public static void reloadData() {
        String text = "";

        try {
            InputStream fileInput = new FileInputStream(Consts.CONFIG_FILE);
            text = new String(fileInput.readAllBytes());
            fileInput.close();
        } catch (IOException e) {
            LogHandler.display(5,"[!] Config File not found");
            ExitHandler.error(new Exception("Config file not found"));
        }

        JSONObject json = new JSONObject(text);

        dbURL = json.getString("dbAddr");
        dbUsername = json.getString("dbUser");
        dbPassword = json.getString("dbPassword");

        printConfig();
    }

    public static void printConfig() {
        LogHandler.display(4,"[+] Loaded config: ");
        LogHandler.display(4,"   [>] DB Addr: " + dbURL);
        LogHandler.display(4,"   [>] DB User: " + dbUsername);
        LogHandler.display(4,"   [>] DB passwd: " + dbPassword);
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

    public static UUID getUUID() {
        if (uuid == null) {
            reloadUUID();
            return getUUID();
        } else {
            return uuid;
        }
    }

    private static void createUUID(){
        File newConfig = new File(Consts.UUID_FILE);
        try {
            newConfig.createNewFile();
        } catch (IOException e) {
            ExitHandler.error(e);
        }
        uuid = UUID.randomUUID();
        FileWriter UUIDWriter;
        try {
            UUIDWriter = new FileWriter(Consts.UUID_FILE);
            UUIDWriter.write("{\"uuid\":\"" + uuid.toString()+"\"}");
            UUIDWriter.close();
        } catch (IOException e) {
            ExitHandler.error(e);
        }
        LogHandler.display(4,"      [+] New UUID: " + uuid.toString());
    }

    private static void reloadUUID(){ 
        String text = "";

        try {
            InputStream fileInput = new FileInputStream(Consts.UUID_FILE);
            text = new String(fileInput.readAllBytes());
            fileInput.close();
        } catch (IOException e) {
            LogHandler.display(4,"      [!] ID file not found, creating new one...");
            createUUID();
            return;
        }

        JSONObject json = new JSONObject(text);
        uuid = UUID.fromString(json.getString("uuid"));
        LogHandler.display(1,"      [+] UUID parsed : "+uuid.toString());

    }
}
