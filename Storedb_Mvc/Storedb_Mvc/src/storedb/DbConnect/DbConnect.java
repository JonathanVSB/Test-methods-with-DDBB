/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package storedb.DbConnect;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author dax
 */
public class DbConnect {
     
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "127.0.0.1";
    static final String BD_NAME = "storedb";
    static final String USER = "storeusr";
    static final String PASSWORD = "storepsw";
 
    public static void loadDriver() throws ClassNotFoundException {
        //getConnectionProperties(); better if connection properties are read from a configuration file
        Class.forName(DRIVER);
    }
 
    /**
     * gets and returns a connection to database
     *
     * @return connection
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {
        final String BD_URL = String.format("%s//%s/%s", PROTOCOL, HOST, BD_NAME);
        Connection conn;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
    
}
