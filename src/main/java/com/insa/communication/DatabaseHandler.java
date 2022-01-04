package com.insa.communication;

import java.sql.*;
import java.util.*;

import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.ObjectMessage;

public class DatabaseHandler {
    private static Connection conn;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect to DB using Data's info
     */
    public void connect() {
        System.out.print("   [>] Connection...");
        try {
            conn = DriverManager.getConnection("jdbc:" + Data.getDBUrl(), Data.getDBUsername(), Data.getDBPassword());
        } catch (Exception e) {
            System.out.println("failed!");
            ExitHandler.error(e);
        }
        System.out.println("ok.");
    }

    protected static ResultSet executeQuery(String q) {
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(q);
            System.out.println("[+] Query executed: " + q);
        } catch (SQLException e) {
            System.out.println("[!] DB query failed: " + q);
            ExitHandler.error(e);
        }
        return rs;
    }

    protected static void executeUpdate(String q, boolean ignore) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(q);
            System.out.println("[+] Query executed: " + q);
        } catch (SQLException e) {
            System.out.println("[-] DB query failed: " + q);
            if (ignore)
                return;
            ExitHandler.error(e);
        }
    }

    private static ArrayList<Integer> getRowInt(String table, String column) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        ResultSet rs = executeQuery("SELECT " + column + " from " + table);
        try {
            while (rs.next())
                list.add(Integer.parseInt(rs.getString(1)));
        } catch (Exception e) {
            ExitHandler.error(e);
        }

        return list;
    }

    protected static int newContentId() {
        ArrayList<Integer> idList = getRowInt(Consts.DB_MAIN_TABLE, "contentID");
        Random seed = new Random();

        Integer id = seed.nextInt(Integer.MAX_VALUE);
        while (idList.contains(id)) {
            id = seed.nextInt(Integer.MAX_VALUE);
        }

        return id;
    }

    private static String getTextContent(int id) {
        String textContent = "";
        ResultSet ts = executeQuery(
                "SELECT * FROM " + Consts.DB_TEXT_TABLE + " WHERE messageID=" + id + " ORDER BY messagePart");

        try {
            while (ts.next()) {
                textContent += ts.getString(3);
            }
        } catch (SQLException e) {
            ExitHandler.error(e);
        }
        return textContent;
    }

    /**
     * Reset remote database. Can be used as init. function.
     */
    public void resetDB() {
        executeUpdate("DROP TABLE " + Consts.DB_MAIN_TABLE, true);
        executeUpdate("DROP TABLE " + Consts.DB_TEXT_TABLE, true);
        executeUpdate("DROP TABLE " + Consts.DB_FILE_TABLE, true);

        executeUpdate("create table " + Consts.DB_MAIN_TABLE + "(messageID integer not null auto_increment, sender char(36) not null, receiver char(36) not null, sendDate bigint not null, contentID integer not null, messageType integer, PRIMARY KEY (messageID));",false);
        executeUpdate("create table " + Consts.DB_TEXT_TABLE + "(messageID integer not null, messagePart integer, content varchar(512));", false);
        executeUpdate("create table " + Consts.DB_FILE_TABLE + "(messageID integer not null, fileName varchar(128) not null, fileID char(36), size integer);",false);
    }

    /**
     * Get message in DB from a given UUID
     * 
     * @param uuid
     * @return ArrayList<ObjectMessage>
     */
    public static ArrayList<Message> getMessages(UUID uuid) {
        ResultSet rs = executeQuery("SELECT * from " + Consts.DB_MAIN_TABLE);
        ArrayList<Message> messageList = new ArrayList<Message>();

        int id, contentId;
        UUID sender, receiver;
        long timestamp;
        int messageType;
        String textContent = "";

        try {
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
                sender = UUID.fromString(rs.getString(2));
                receiver = UUID.fromString(rs.getString(3));
                timestamp = Long.parseLong(rs.getString(4));
                contentId = Integer.parseInt(rs.getString(5));
                messageType = Integer.parseInt(rs.getString(6));

                switch (messageType) {
                    case 0:
                        textContent = "Auto-generated debug message";
                    case 1:
                        textContent = ((textContent = getTextContent(contentId)) != null) ? textContent
                                : getTextContent(contentId);

                        TextMessage msg = new TextMessage(sender, receiver, textContent);
                        messageList.add(msg);

                        System.out.println("   [>] Text message found from "
                                + sender + " to "
                                + receiver + ": "
                                + textContent
                                + " (id=" + id
                                + ", date: " + timestamp + ").");

                        break;
                    case 2:
                    case 3:
                    default:
                        // TODO lorsqu'on aura implémenté FileMessage.java
                        System.out.println("   [>] Not implemented message found (id: " + id + ").");
                        break;
                }
            }
        } catch (SQLException e) {
            ExitHandler.error(e);
        }
        return messageList;
    }
}
