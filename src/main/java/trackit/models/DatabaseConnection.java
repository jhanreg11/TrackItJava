package trackit.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    /**
     * Singleton class for managing connection to database.
     */

    private static Connection connection = null;
    private static final String username = "goofy";
    private static final String password = "gooberson";
    private static final String dbUrl = "jdbc:mysql://localhost/trackit?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    /**
     * Get and create connection if necessary.
     * @return connection to database
     */
    public static Connection getConnection() {
        if (connection == null)
            connect();
        return connection;
    }

    /**
     * Establish connection to database.
     */
    public static void connect() {
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
        }
    }

}
