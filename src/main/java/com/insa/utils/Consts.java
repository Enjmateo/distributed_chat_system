package com.insa.utils;

public class Consts {
    public static final int MAX_UDP_PACKET_SIZE = 5000;

    public static String configFile = "./.data.json";

    public static final String DB_MAIN_TABLE = "messages";
    public static final String DB_TEXT_TABLE = "text_message";
    public static final String DB_FILE_TABLE = "file_message";
    public static final int MAX_TEXT_LENGTH = 500;

    public static final Integer udpPort = 4444;

    public static final int discoveryTimeoutMs = 2000;
    public static final int NOTIFY_ALIVE_PERIOD = 1000;

}
