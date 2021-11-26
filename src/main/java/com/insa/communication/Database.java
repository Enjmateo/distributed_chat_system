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
            conn = DriverManager.getConnection("jdbc:" + Data.getDBUrl(), Data.getDBUsername(), Data.getDBPassword());
        } catch (Exception e) {
            System.out.println( "failed!" );
            return 1;
        }
        System.out.println( "ok." );
        return 0;
    }

    private void executeQuery(String q){
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(q);          
        } catch (SQLException e) {
            System.out.println( "[!] DB failed" );
            ExitHandler.error(e);
        }
    }

    public void addMessage(ObjectMessage message) throws Exception {
        
    }
}
