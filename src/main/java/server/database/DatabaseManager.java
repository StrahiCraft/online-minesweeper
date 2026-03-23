package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {

    private static final String username = "master";
    private static final String password = "password";
    private static final String databaseName = "online_minesweeper";

    private static Connection connection;

    public static void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName,
                    username, password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        try{
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){
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
