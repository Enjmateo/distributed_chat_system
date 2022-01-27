package com.insa.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.scene.paint.Color;

public class Consts {
    public static final int MAX_UDP_PACKET_SIZE = 5000;

    public static String CONFIG_FILE = "./.data";
    public static String UUID_FILE = "./.ID";

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

    //Graphics 
    final static public double PSEUDO_FIELD_SIZE = 100;
    final static public double IMAGE_CONNECTION_WINDOW_SIZE = 300;

    //Main window
    final static public double MW_MIN_HEIGHT = 300;
    final static public double MW_MIN_WIDTH = 500;

    final static public double IM_AND_PSEU_MIN_HEIGHT = 140;
    final static public double IM_AND_PSEU_MIN_WIDTH = 200;

    final static public double MW_IMAGE_SIZE = 220;
    final static public double SCENE_PADDING = 20;
	final static public double FIELDS_GAP = 10;
	final static public double ELEMENTS_GAP = 20;

    //Messages view
    final static public Color BACKGROUND_COLOR=Color.BLACK;
    final static public Color SENT_MESSAGE_BACKGROUND_COLOR= Color.GREY;
    final static public Color RECEIVED_MESSAGE_BACKGROUND_COLOR= Color.BLUE;

    final static public double MESSAGES_ROUNDING=13;
    final static public double MESSAGES_WIDTH=300;
    
    final static public double MESSAGE_PADDING = 7;

    final static public SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm - dd/MM");
}
