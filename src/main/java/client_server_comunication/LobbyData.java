package client_server_comunication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class LobbyData implements Serializable {
    private ArrayList<UUID> clients;
    private String lobbyName;
    private int minefieldWidth;
    private int minefieldHeight;
    private int mineCount;

    public LobbyData(UUID host, String lobbyName, int minefieldWidth, int minefieldHeight, int mineCount) {
        clients = new ArrayList<>();
        clients.add(host);

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

    public boolean containsPlayer(UUID clientId){
        return clients.contains(clientId);
    }

    public void addPlayer(UUID clientId){
        clients.add(clientId);
    }

    public void removePlayer(UUID clientId){
        clients.remove(clientId);
    }

    public ArrayList<UUID> getClients() {
        return clients;
    }

    public void setClients(ArrayList<UUID> clients) {
        this.clients = clients;
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
}
