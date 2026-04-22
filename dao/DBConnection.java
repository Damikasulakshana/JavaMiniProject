package dao;

import java.sql.*;

public class DBConnection {

    // Database URL 
    private static final String URL  = "jdbc:mysql://localhost:3309/faculty_db";
    private static final String USER = "root";
    private static final String PASS = "1234";

    // Connection object 
    private static Connection con;

    // Method to get database connection
    public static Connection getConnection() throws SQLException {

        // Check if connection is null OR already closed
        if (con == null || con.isClosed()) {

            // Create new connection using DriverManager
            con = DriverManager.getConnection(URL, USER, PASS);
        }
        return con;
    }
}