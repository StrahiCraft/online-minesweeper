package server;

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
