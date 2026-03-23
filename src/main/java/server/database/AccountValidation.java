package server.database;

import java.sql.ResultSet;

public class AccountValidation {
    public static boolean validLogin(String username, String password){
        ResultSet resultSet = DatabaseManager.getDataFromQuery("SELECT * FROM Player WHERE username = '" + username + "' AND " +
                "password = '"  + password + "'");
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
        return true;
    }
}
