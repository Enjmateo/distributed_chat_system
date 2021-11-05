package com.insa.communication;
import java.util.UUID;
import org.json.*;

public class Data {
    private static String dbURL = null;

    private static String dbUsername = null;
    private static String dbPassword = null;

    private static UUID uuid = null;

    public Data () {
        JSONParser parser = new JSONParser();
        //SI PAS D'UUID :
        uuid = UUID.randomUUID();
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
