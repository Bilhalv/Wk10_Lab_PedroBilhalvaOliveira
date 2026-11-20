package ca.centennial.comp228;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_applications_db"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Toronto";
    private static final String DB_USER = "job_app";
    private static final String DB_PASSWORD = "JobApp#2026";

    private Database() {
    }

    public static Connection getDBConnection() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException exception) {
            throw new SQLException("MySQL JDBC driver not found", exception);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
