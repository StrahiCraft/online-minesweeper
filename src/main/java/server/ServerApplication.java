package server;

import server.database.DatabaseManager;
import client_server_comunication.LobbyData;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Application for the server, this needs to be running for clients to connect and play the game
 */
public class ServerApplication {
    /**
     * Port that the server is hosted on
     */
    public static final int PORT = 25655;

    /**
     * Hashmap of all currently connected clients, each client receives a unique ID and the client handler thread is stored
     * using it as the key
     */
    private static HashMap<UUID, ClientHandler> connectedClients = new HashMap<>();

    /**
     * List of all currently active lobbies
     */
    private static ArrayList<LobbyData> lobbies = new ArrayList<>();

    /**
     * Main function of the server, all client connection logic from the server side is done here
     * @param args
     */
    public static void main(String[] args) {
        DatabaseManager.connect();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Server started on port " + PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.flush();
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                String clientConnectMessage = objectInputStream.readObject().toString();

                if(clientConnectMessage != null){
                    System.out.println(clientConnectMessage);
                    UUID clientId = UUID.randomUUID();
                    objectOutputStream.writeObject(clientId);
                    objectOutputStream.flush();

                    ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, objectInputStream, objectOutputStream);
                    clientHandler.start();

                    connectedClients.put(clientId, clientHandler);

                    System.out.println("Client connected! with ID: " + clientId);
                }
                else{
                    System.out.println("Client failed to connect.");
                }
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if(serverSocket != null){
                    serverSocket.close();
                }
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }

            DatabaseManager.disconnect();
        }
    }

    /**
     * Creates data for the lobby
     * @param hostId UUID of the host
     * @param hostName Name of the host
     * @param lobbyName Name of the created lobby
     */
    public static void createLobbyData(UUID hostId, String hostName, String lobbyName){
        lobbies.add(new LobbyData(hostId, hostName, lobbyName, 16, 16, 64));
    }

    /**
     * Deletes lobby data from the list of active lobbies
     * @param lobbyName Lobby that is being deleted
     */
    public static void deleteLobbyData(String lobbyName){
        lobbies.remove(getLobbyWithName(lobbyName));
    }

    /**
     * Returns the lobby with the given name from the list of active lobbies
     * @param lobbyName Name of the lobby we ara trying to get
     * @return The lobby data of the found lobby or null if no lobby is found
     */
    public static LobbyData getLobbyWithName(String lobbyName){
        for(LobbyData lobby : lobbies){
            if(lobby.getLobbyName().equals(lobbyName)){
                return lobby;
            }
        }
        return null;
    }

    /**
     * Returns the lobby with the given client id from the list of active lobbies
     * @param clientId The id of the client we are trying to find in the lobbies
     * @return The lobby data of the found lobby or null if no lobby is found
     */
    public static LobbyData getLobbyWithClient(UUID clientId){
        for (LobbyData lobby : lobbies){
            if(lobby.containsPlayer(clientId)){
                return lobby;
            }
        }

        return null;
    }

    /**
     * Removes client data from the list of connected clients after the client disconnects
     * @param clientId Id of the client that has just disconnected
     */
    public static void onClientDisconnected(UUID clientId){
        System.out.println("Client " + clientId + " has disconnected!");
        connectedClients.get(clientId).interrupt();
        connectedClients.remove(clientId);
    }

    /**
     * Gets the handler of the client based on the clients UUID
     * @param clientId The client id of the client we are getting the handler from
     * @return The client handler with the given client id
     */
    public static ClientHandler getClientHandler(UUID clientId){
        return connectedClients.get(clientId);
    }
}
