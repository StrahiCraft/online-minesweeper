package client_server_comunication;

import game.minefield.Minefield;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class LobbyData implements Serializable {
    private ArrayList<UUID> clients;
    private ArrayList<String> clientAccountNames;
    private String lobbyName;
    private int minefieldWidth;
    private int minefieldHeight;
    private int mineCount;

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

    public boolean containsPlayer(UUID clientId){
        return clients.contains(clientId);
    }

    public void addPlayer(UUID clientId, String clientName){
        clients.add(clientId);
        clientAccountNames.add(clientName);
    }

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
