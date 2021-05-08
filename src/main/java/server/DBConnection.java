package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class represents a database connection.
 *
 * Adapted from https://github.com/qut-cab302/prac07/blob/master/solution/dataExercise/DBConnection.java
 */
public final class DBConnection {

    /**
     * The database connection.
     */
    private static Connection connection = null;

    /**
     * Creates a DBConnection object.
     */
    private DBConnection() {

        Properties properties = new Properties();
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream("./db.props");
            properties.load(fileInputStream);
            fileInputStream.close();

            String url = properties.getProperty("jdbc.url");
            String schema = properties.getProperty("jdbc.schema");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url + "/" + schema, username, password);
        }
        catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets the database connection's connection field.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }
}
