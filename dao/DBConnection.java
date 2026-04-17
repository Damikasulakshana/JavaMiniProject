package dao;

import java.sql.*;


public class DBConnection {

    private static final String URL  = "jdbc:mysql://localhost:3309/faculty_db";
    private static final String USER = "root";
    private static final String PASS = "1234";

    private static Connection con;

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(URL, USER, PASS);
        }
        return con;
    }
}
