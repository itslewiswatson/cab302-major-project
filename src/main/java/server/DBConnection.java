package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private static Connection connection;

    /**
     * Creates a DBConnection object.
     */
    private DBConnection() {

        Properties properties = new Properties();
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream("./src/main/resources/db.props");
            properties.load(fileInputStream);
            fileInputStream.close();

            String url = properties.getProperty("jdbc.url");
            String schema = properties.getProperty("jdbc.schema");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url + "/" + schema, username, password);
        }
        catch (FileNotFoundException exception) {
            System.err.println("db.props is not where it is expected to be. Rebuild the program and ensure db.props is in /src/main/resources.");
        }
        catch (IOException exception) {
            System.err.println("db.props has become corrupted. Rebuild the program.");
        }
        catch (SQLException exception) {
            System.err.println("Could not connect to SQL database. Ensure MySQL server is running and database instructions in README.md have been followed.");
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
