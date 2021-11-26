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

    public int connect() {
        System.out.print( "   [>] Connection..." );
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + Data.getDBUrl() + ":3306", Data.getDBUsername(), Data.getDBPassword());
        } catch (Exception e) {
            System.out.println( "failed!" );
            return 1;
        }
        System.out.println( "ok." );
        return 0;
    }

    public void printInfo() {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Consts.dBTableName);
            System.out.println(rs.getAsciiStream(1));
        } catch (SQLException e) {
            System.out.println( "[!] DB failed" );
            ExitHandler.error(e);
        }
    }

    public void addMessage(ObjectMessage message) throws Exception {
        // TODO...
    }
}
