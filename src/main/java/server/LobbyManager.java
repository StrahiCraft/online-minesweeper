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

    /**
     * Deletes the lobby from the database
     * @param lobbyName Name of the lobby to delete
     */
    public static void deleteLobby(String lobbyName){
        String[] parameters = { lobbyName };
        DatabaseManager.executeUpdate("DELETE FROM lobby WHERE name = ?", parameters);
    }

    /**
     * Removes a player from the lobby, when removing the player the lobby the player is in is set to NULL
     * @param playerName Player to remove from the lobby
     */
    public static void removePlayerFromLobby(String playerName){
        setPlayerToLobby(playerName, "");
    }

    /**
     * Sets the given player to the given lobby
     * @param playerName Name of the player to set into the given lobby
     * @param lobbyName Name of the lobby the player is being set to, an empty string sets the player's lobby
     *                  to NULL
     */
    public static void setPlayerToLobby(String playerName, String lobbyName) {
        Integer lobbyId = getLobbyId(lobbyName);
        String[] newParameters = { playerName };
        DatabaseManager.executeUpdate("UPDATE player SET lobby_id = " + lobbyId +  " WHERE username = ?", newParameters);
    }

    /**
     * Gets the lobby id from the lobby's name
     * @param lobbyName Name of the lobby we are getting the id for
     * @return The lobby's id or -1 if there is no lobby with the given name
     */
    public static Integer getLobbyId(String lobbyName){
        String[] parameters = { lobbyName };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM lobby WHERE name = ?", parameters);
        Integer lobbyId = null;
        try{
            if (resultSet.next()){
                lobbyId = resultSet.getInt("lobby_id");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return lobbyId;
    }

    /**
     * Changes the name of the lobby to a new one if a lobby with the new name doesn't already exist
     * @param oldName Old name of the lobby, used to find the lobby database entry
     * @param newName New name for the lobby
     * @return True if the lobby name has been changed, false if not
     */
    public static boolean renameLobby(String oldName, String newName){
        String[] oldNameParameter = { oldName };
        String[] newNameParameter = { newName };
        ResultSet oldLobby = DatabaseManager.executeQuery("SELECT * FROM lobby WHERE name = ?", oldNameParameter);
        ResultSet lobbyWithNewName = DatabaseManager.executeQuery("SELECT * FROM lobby WHERE name = ?", newNameParameter);

        try{
            if (oldLobby.next()){
                if(lobbyWithNewName.next()){
                    return false;
                }
                int lobbyId = oldLobby.getInt("lobby_id");
                String[] newParameters = { newName };
                return DatabaseManager.executeUpdate("UPDATE lobby SET name = ? WHERE lobby_id = " + lobbyId, newParameters);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
