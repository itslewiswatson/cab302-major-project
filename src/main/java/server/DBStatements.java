package server;

import common.Credentials;
import common.ExistingUser;
import common.NewUser;
import java.sql.*;

public class DBStatements {
    private static final String GET_USER = "SELECT * FROM user WHERE username = ?";

    private static final String INSERT_USER = "INSERT INTO user (username, password, admin) VALUES (?, ?, ?);";

    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE username = ?";

    private final Connection connection;

    private PreparedStatement insertUser;

    private PreparedStatement getUser;

    private PreparedStatement updatePassword;

    public DBStatements() {
        connection = DBConnection.getConnection();

        try {
            insertUser = connection.prepareStatement(INSERT_USER);
            getUser = connection.prepareStatement(GET_USER);
            updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addNewUser(NewUser newUser) {
        try {
            insertUser.setString(1, newUser.getUsername());
            insertUser.setString(2, newUser.getPassword());
            insertUser.setString(3, newUser.getAdmin());

            insertUser.execute();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ExistingUser getExistingUser(Credentials credentials) {
        ExistingUser existingUser = new ExistingUser();
        ResultSet resultSet;

        try {
            getUser.setString(1, credentials.getUsername());

            resultSet = getUser.executeQuery();
            resultSet.next();

            existingUser.setUsername(resultSet.getString("username"));
            existingUser.setPassword(resultSet.getString("password"));
            existingUser.setAdmin(resultSet.getBoolean("admin"));
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return existingUser;
    }

    public void changePassword(ExistingUser existingUser) {
        try {
            updatePassword.setString(1, existingUser.getPassword());
            updatePassword.setString(2, existingUser.getUsername());
            updatePassword.execute();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
