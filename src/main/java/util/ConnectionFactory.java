package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Utility class for creating database connections.
 */
public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/OrderManagementDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    /**
     * Gets a connection to the database.
     * @return The database connection.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
