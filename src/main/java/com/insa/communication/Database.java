package com.insa.communication;

import java.sql.*;

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

    public void connect() throws Exception {
        conn = DriverManager.getConnection(Data.getDBUrl(), Data.getDBUsername(), Data.getDBPassword());
    }

    public void addMessage(ObjectMessage message) throws Exception {
        // TODO...
    }
}
