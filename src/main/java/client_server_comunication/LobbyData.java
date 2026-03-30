package client_server_comunication;

import client.minefield.Minefield;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Data for lobbies, each lobby contains a list of the connected clients, the account names of the players logging in
 * from those clients, the name of the lobby, minefield settings and the current minefield being played on in that lobby
 */
public class LobbyData implements Serializable {
    /**
     * List of clients currently connected to this lobby
     */
    private ArrayList<UUID> clients;
    /**
     * List of account names that are logged in to the clients connected to this lobby
     */
    private ArrayList<String> clientAccountNames;
    /**
     * The current name of this lobby
     */
    private String lobbyName;
    /**
     * The width of the minefield for this lobby
     */
    private int minefieldWidth;
    /**
     * The height of the minefield for this lobby
     */
    private int minefieldHeight;
    /**
     * The number of mines of the minefield for this lobby
     */
    private int mineCount;

    /**
     * The minefield this lobby is being played on
     */
    private Minefield minefield;

    public LobbyData(UUID host, String hostName, String lobbyName, int minefieldWidth, int minefieldHeight, int mineCount) {
        clients = new ArrayList<>();
        clients.add(host);
        clientAccountNames = new ArrayList<>();
        clientAccountNames.add(hostName);

        this.lobbyName = lobbyName;
        this.minefieldWidth = minefieldWidth;
        this.minefieldHeight = minefieldHeight;
        this.mineCount = mineCount;
    }

    public LobbyData(ArrayList<UUID> clients, String lobbyName, int minefieldWidth, int minefieldHeight, int mineCount) {
        this.clients = clients;
        this.lobbyName = lobbyName;
        this.minefieldWidth = minefieldWidth;
        this.minefieldHeight = minefieldHeight;
        this.mineCount = mineCount;
    }

    /**
     * Checks if this lobby contains a player with the given client id
     * @param clientId The client id of the player for which we are checking if they are in the lobby
     * @return True if the lobby contains the player with the given client id, false otherwise
     */
    public boolean containsPlayer(UUID clientId){
        return clients.contains(clientId);
    }

    /**
     * Adds the given player to the lobby
     * @param clientId The client id of the player being added to the lobby
     * @param clientName The name of the player being added to the lobby
     */
    public void addPlayer(UUID clientId, String clientName){
        clients.add(clientId);
        clientAccountNames.add(clientName);
    }

    /**
     * Removes the given player from the lobby
     * @param clientId The client id of the player that is being removed from the lobby
     */
    public void removePlayer(UUID clientId){
        clientAccountNames.remove(clients.indexOf(clientId));
        clients.remove(clientId);
    }

    public ArrayList<UUID> getClients() {
        return clients;
    }

    public void setClients(ArrayList<UUID> clients) {
        this.clients = clients;
    }

    public ArrayList<String> getClientAccountNames() {
        return clientAccountNames;
    }

    public void setClientAccountNames(ArrayList<String> clientAccountNames) {
        this.clientAccountNames = clientAccountNames;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public int getMinefieldWidth() {
        return minefieldWidth;
    }

    public void setMinefieldWidth(int minefieldWidth) {
        this.minefieldWidth = minefieldWidth;
    }

    public int getMinefieldHeight() {
        return minefieldHeight;
    }

    public void setMinefieldHeight(int minefieldHeight) {
        this.minefieldHeight = minefieldHeight;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public Minefield getMinefield() {
        return minefield;
    }

    public void setMinefield(Minefield minefield) {
        this.minefield = minefield;
    }

    @Override
    public String toString() {
        return "LobbyData{" +
                "clients=" + clients +
                ", clientAccountNames=" + clientAccountNames +
                ", lobbyName='" + lobbyName + '\'' +
                ", minefieldWidth=" + minefieldWidth +
                ", minefieldHeight=" + minefieldHeight +
                ", mineCount=" + mineCount +
                '}';
    }
}
