package com.insa.communication;
import java.util.UUID;

import java.io.*;
import org.json.*;

public class Data {
    private static String dbURL = null;

    private static String dbUsername = null;
    private static String dbPassword = null;

    private static UUID uuid = null;

    public Data () {
        String text = "";

        try {
            InputStream fileInput = new FileInputStream("./data.json");
            text = new String(fileInput.readAllBytes());
            fileInput.close();
        }catch(IOException e) {
            //TODO Add error window or create a new file data.json
        }
        JSONObject json = new JSONObject(text);


        if(json.isNull("uuid")) {
            uuid = UUID.randomUUID();
            json.put("uuid", uuid.toString());
            try {
                OutputStream fileOutput = new FileOutputStream("./data.json");
                fileOutput.write(json.toString().getBytes());
                fileOutput.close();
            } catch (Exception e) {
                //TODO: handle exception
            }
        } else {
            uuid = UUID.fromString(json.getString("uuid"));
        }
        dbURL = json.getString("dbAddr");
        dbUsername = json.getString("dbUser");
        dbPassword = json.getString("dbPassword");        
    }


    protected static String getDBUrl() throws Exception {
        if (dbURL == null) {
            throw new Exception("[!] Data not found (DB url)");
        } else {
            return dbURL;
        }
    }

    protected static String getDBUsername() throws Exception {
        if (dbUsername == null) {
            throw new Exception("[!] Data not found (DB username)");
        } else {
            return dbUsername;
        }
    }

    protected static String getDBPassword() throws Exception {
        if (dbPassword == null) {
            throw new Exception("[!] Data not found (DB password)");
        } else {
            return dbPassword;
        }
    }

    protected static String getUUID() throws Exception {
        if (uuid == null) {
            throw new Exception("[!] Data not found (UUID)");
        } else {
            return uuid.toString();
        }
    }
}
