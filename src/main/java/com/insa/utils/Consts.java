package com.insa.utils;

public class Consts {
    public static final int MAX_UDP_PACKET_SIZE = 5000;

    public static String CONFIG_FILE = "./.data.json";

    public static final String DB_MAIN_TABLE = "messages";
    public static final String DB_TEXT_TABLE = "text_message";
    public static final String DB_FILE_TABLE = "file_message";
    public static final int MAX_TEXT_LENGTH = 500;

    public static final Integer UDP_PORT = 4444;
    public static final Integer TCP_PORT_A = 4445;
    public static final Integer TCP_PORT_B = 4446;

    public static final int DISCOVERY_TIMEOUT_MS = 1000;
    public static final int NOTIFY_ALIVE_PERIOD = 5000;

    public static final int TCP_TIMEOUT = 1000;

    final static public double SCENE_PADDING = 20;
	final static public double FIELDS_GAP = 10;
	final static public double ELEMENTS_GAP = 20;
	final static public double IP_ADRESS_FIELD_SIZE = 350;
	final static public double PORT_FIELD_SIZE = 75;
}
