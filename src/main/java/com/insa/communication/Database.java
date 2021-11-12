package com.insa.communication;

import java.sql.Connection;
import java.sql.DriverManager;

import com.insa.utils.ObjectMessage;

public class Database {
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        try (Connection con = DriverManager.getConnection(Data.getDBUrl(), Data.getDBUsername(), Data.getDBPassword())) {
            // use con here
        }
    }

    public void addMessage(ObjectMessage message) throws Exception {
        // TODO...
    }
}
