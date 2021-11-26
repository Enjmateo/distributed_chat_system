package com.insa.communication;

import java.sql.*;

import com.insa.utils.Consts;
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

    public void connect() throws Exception {
        System.out.print( "   [>] Connection..." );
        conn = DriverManager.getConnection("jdbc:mysql://" + Data.getDBUrl() + ":3306", Data.getDBUsername(), Data.getDBPassword());
        System.out.println( "ok." );
    }

    public void printInfo() {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM " + Consts.dBTableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.println( "   [>] " + rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println( "[!] DB failed" );
            ExitHandler.error(e);
        }
    }

    public void addMessage(ObjectMessage message) throws Exception {
        // TODO...
    }
}
