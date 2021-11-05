package com.insa.communication;

import java.util.UUID;

public class Data {
    private static String DB_URL = null;

    private static String DB_USERNAME = null;
    private static String DB_PASSWORD = null;

    private static UUID uuid = null;

    public Data () {}

    protected static String getDBUrl() throws Exception {
        if (DB_URL == null) {
            throw new Exception("[!] Data not found (DB url)");
        } else {
            return DB_URL;
        }
    }

    protected static String getDBUsername() throws Exception {
        if (DB_USERNAME == null) {
            throw new Exception("[!] Data not found (DB username)");
        } else {
            return DB_USERNAME;
        }
    }

    protected static String getDBPassword() throws Exception {
        if (DB_PASSWORD == null) {
            throw new Exception("[!] Data not found (DB password)");
        } else {
            return DB_PASSWORD;
        }
    }

    protected static UUID getUUID() throws Exception {
        if (uuid == null) {
            throw new Exception("[!] Data not found (UUID)");
        } else {
            return uuid;
        }
    }
}
