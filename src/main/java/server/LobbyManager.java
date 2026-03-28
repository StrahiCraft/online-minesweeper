package server;

import server.database.DatabaseManager;

import java.sql.ResultSet;

/**
 * Class for managing the lobby
 */
public class LobbyManager {
    /**
     * Creates a lobby with the given name and adds it to the database
     * @param lobbyName The name of the lobby being created
     * @return True if the lobby name is unique and was successfully created, false otherwise
     */
    public static boolean createLobby(String lobbyName) {
        String[] parameters = { lobbyName };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM lobby WHERE name = ?", parameters);

        boolean lobbyValid = true;

        try{
            if (resultSet.next()){
                lobbyValid = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        if(lobbyValid){
            return DatabaseManager.executeUpdate("INSERT INTO lobby (name) VALUES (?)", parameters);
        }

        return false;
    }

    public static void deleteLobby(String lobbyName){
        String[] parameters = { lobbyName };
        DatabaseManager.executeUpdate("DELETE FROM lobby WHERE name = ?", parameters);
    }

    public static void setPlayerToLobby(String playerName, String lobbyName) {
        String[] parameters = { lobbyName };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM lobby WHERE name = ?", parameters);

        try{
            if (resultSet.next()){
                int lobbyId = resultSet.getInt("lobby_id");
                String[] newParameters = { playerName };
                DatabaseManager.executeUpdate("UPDATE player SET lobby_id = " + lobbyId +  " WHERE username = ?", newParameters);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
