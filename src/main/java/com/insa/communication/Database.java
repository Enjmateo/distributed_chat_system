package com.insa.communication;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.insa.utils.ExitHandler;
import com.insa.utils.ObjectMessage;

public class Database {
    private Connection conn;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int connect() {
        System.out.print("   [>] Connection...");
        try {
            conn = DriverManager.getConnection("jdbc:" + Data.getDBUrl(), Data.getDBUsername(), Data.getDBPassword());
        } catch (Exception e) {
            System.out.println("failed!");
            return 1;
        }
        System.out.println("ok.");
        return 0;
    }

    public ResultSet executeQuery(String q) {
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

    public void executeUpdate(String q) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(q);
            System.out.println("[+] Query executed: " + q);
        } catch (SQLException e) {
            System.out.println("[!] DB query failed: " + q);
            ExitHandler.error(e);
        }
    }

    public ArrayList<ObjectMessage> getMessages(UUID uuid) {
        ResultSet rs = executeQuery("SELECT * from messages");
        ArrayList<ObjectMessage> messageList = new ArrayList<ObjectMessage>();

        int id;
        UUID sender, receiver;
        int timestamp;
        int messageType;
        String textContent = "";

        try {
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
                sender = UUID.fromString(rs.getString(2));
                receiver = UUID.fromString(rs.getString(3));
                timestamp = Integer.parseInt(rs.getString(4));
                messageType = Integer.parseInt(rs.getString(5));

                switch (messageType) {
                    case 0:
                    case 1:
                        ResultSet ts = executeQuery("SELECT * FROM text_message WHERE messageID=" + id);
                        if (ts.next()) {
                            textContent = ts.getString(3);
                        }

                        TextMessage msg = new TextMessage(sender, receiver, textContent);
                        messageList.add(msg);

                        System.out.println("   [>] Text message found from " + sender + " to " + receiver + ": " + textContent + " (id=" + id + ", date: " + timestamp + ").");
                        break;
                    case 2:
                    case 3:
                        // TODO lorsqu'on aura implémenté FileMessage.java
                        System.out.println("   [>] Not implemented message found (id: " + id + ").");
                        break;
                }
            }
        } catch (SQLException e) {ExitHandler.error(e);}
        return messageList;
    }
}
