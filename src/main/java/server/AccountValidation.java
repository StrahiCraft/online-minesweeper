package server;

import javafx.scene.chart.PieChart;
import server.database.DatabaseManager;

import java.sql.ResultSet;

public class AccountValidation {
    public static boolean validLogin(String username, String password){
        String[] parameters = { username, password };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ? AND password = ?", parameters);
        boolean loginValid = false;

        try{
            if (resultSet.next()){
                loginValid = true;
            }
        }
        catch (Exception e){
            System.out.println("Error with logging in!");
        }

        return loginValid;
    }

    public static void setLoggedIn(String username, boolean value){
        String[] parameters = { username };
        int loggedInStatus = value? 1 : 0;
        DatabaseManager.executeUpdate("UPDATE player SET logged_in = " + loggedInStatus + " WHERE username = ?", parameters);
    }

    public static boolean notAlreadyLoggedIn(String username){
        String[] parameters = { username };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ?", parameters);

        boolean alreadyLoggedIn = false;

        try{
            if (resultSet.next()){
                alreadyLoggedIn = resultSet.getInt("logged_in") == 1;
            }
        }
        catch (Exception e){
            System.out.println("Error with logging in!");
        }

        return !alreadyLoggedIn;
    }

    public static boolean validRegistration(String username, String password){
        String[] parameters = { username };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ?", parameters);
        boolean registrationValid = true;

        try{
            if (resultSet.next()){
                registrationValid = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(registrationValid){
            String[] newAccountParameters = { username, password };
            DatabaseManager.executeUpdate("INSERT INTO player (username, password) VALUES(?, ?)", newAccountParameters);
        }

        return registrationValid;
    }
}
