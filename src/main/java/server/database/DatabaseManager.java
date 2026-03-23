package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
            Class.forName("com.mysql.jdbc.Driver");
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

    public static ResultSet getDataFromQuery(String query){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
