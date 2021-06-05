package server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class represents a database connection.
 * <p>
 * Adapted from https://github.com/qut-cab302/prac07/blob/master/solution/dataExercise/DBConnection.java
 */
final class DBConnection {

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
            URI jarFileURI = DBConnection.class.getProtectionDomain().getCodeSource().getLocation().toURI();

            File jarFile = Paths.get(jarFileURI).toFile();
            File propertiesFile = new File(jarFile.getParentFile(), "db.properties");

            fileInputStream = new FileInputStream(propertiesFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            String url = properties.getProperty("jdbc.url");
            String schema = properties.getProperty("jdbc.schema");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (FileNotFoundException exception) {
            System.err.println("db.properties is not where it is expected to be. Rebuild the program and ensure db.properties is in /src/main/config.");
        } catch (IOException exception) {
            System.err.println("db.properties has become corrupted. Rebuild the program.");
        } catch (SQLException exception) {
            System.err.println("Could not connect to SQL database. Ensure MySQL server is running and database instructions in README.md have been followed.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the database connection's connection field.
     *
     * @return The database connection.
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static Connection getConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }
}
