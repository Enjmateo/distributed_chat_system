package com.insa.communication;

import java.sql.*;
import java.util.*;

import com.insa.app.UsersHandler;
import com.insa.gui.ErrorWindow;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;

public class DatabaseHandler {
    private static Connection conn;
    static boolean useDatabase;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void errorDB() {
        useDatabase = false;
        new ErrorWindow(
                "Failed to connect to database. Your messages will not be recorded and history will not be retrieved. Please restart app to retry connection.");
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
            errorDB();
        }
        System.out.println("ok.");
    }

    protected static ResultSet executeQuery(String q) throws SQLException {
        Statement stmt;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(q);
        System.out.println("[+] Query executed: " + q);
        return rs;
    }

    public static void setDatabaseUse(boolean use) {
        useDatabase = use;
    }

    public static boolean getUseDatabase() {
        return useDatabase;
    }

    protected static void executeUpdate(String q) throws SQLException {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(q);
            System.out.println("[+] Query executed: " + q);
        } catch (SQLException e) {
            System.out.println("[-] DB query failed: " + q);
            throw e;
        }
    }

    private static ArrayList<Integer> getRowInt(String table, String column) throws SQLException {
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

    protected static int newContentId() throws Exception {
        ArrayList<Integer> idList = getRowInt(Consts.DB_MAIN_TABLE, "contentID");
        Random seed = new Random();

        Integer id = seed.nextInt(Integer.MAX_VALUE);
        while (idList.contains(id)) {
            id = seed.nextInt(Integer.MAX_VALUE);
        }

        return id;
    }

    private static String getTextContent(int id) throws Exception {
        String textContent = "";
        ResultSet ts = executeQuery(
                "SELECT * FROM " + Consts.DB_TEXT_TABLE + " WHERE messageID=" + id + " ORDER BY messagePart");

        while (ts.next()) {
            textContent += ts.getString(3);
        }
        return textContent;
    }

    /**
     * Get message in DB from a given UUID
     * @param uuid
     * @return ArrayList<ObjectMessage>
     */
    public static ArrayList<Message> getMessages(UUID uuidOther) throws Exception {
        UUID uuidSelf = UsersHandler.getLocalUser().getUUID();
        ResultSet rs = executeQuery("SELECT * from " + Consts.DB_MAIN_TABLE + " WHERE (sender='" + uuidSelf.toString()
                + "' AND receiver='" + uuidOther.toString() + "') OR (sender='" + uuidOther.toString()
                + "' AND receiver='" + uuidSelf.toString() + "') ORDER BY sendDate ASC");
        ArrayList<Message> messageList = new ArrayList<Message>();

        int id, contentId;
        UUID sender, receiver;
        long timestamp;
        int messageType;
        String textContent = "";

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

                    LogHandler.display(7, "   [>] Text message found from "
                            + sender + " to "
                            + receiver + ": "
                            + textContent
                            + " (id=" + id
                            + ", date: " + timestamp + ").");

                    LogHandler.display(7, "   [>] getSender give: " + msg.getSender().toString());

                    break;
                case 2:
                case 3:
                default:
                    // TODO lorsqu'on aura implémenté FileMessage.java
                    System.out.println("   [>] Not implemented message found (id: " + id + ").");
                    break;
            }
        }
        return messageList;
    }
}
