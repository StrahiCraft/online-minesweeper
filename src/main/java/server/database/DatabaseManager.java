package server.database;

import java.sql.*;

/**
 * Class for managing the database
 */
public class DatabaseManager {
    /**
     * Username of the account for the database
     */
    private static final String username = "master";
    /**
     * Password of the account for the database
     */
    private static final String password = "password";
    /**
     * URL of the database
     */
    private static final String url = "jdbc:mysql://localhost:3306/online_minesweeper";

    /**
     * The established connection to the database
     */
    private static Connection connection;

    /**
     * Tries to connect to the database using the given username, password and url.
     * Should be called from the server on startup.
     */
    public static void connect(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the database from the program.
     * Call when closing down the server
     */
    public static void disconnect(){
        try{
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query){
        return executeQuery(query, new String[0]);
    }

    public static ResultSet executeQuery(String query, String[] parameters){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < parameters.length; i++){
                statement.setString(i + 1, parameters[i]);
            }
            resultSet = statement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void executeUpdate(String query){
        executeUpdate(query, new String[0]);
    }

    public static void executeUpdate(String query, String[] parameters){
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++){
                statement.setString(i + 1, parameters[i]);
            }
            statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
