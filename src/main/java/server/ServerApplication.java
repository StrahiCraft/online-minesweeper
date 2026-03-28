package server;

import server.database.DatabaseManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

    public static void onPlayerDisconnected(UUID playerId){
        System.out.println("Player " + playerId + " has disconnected!");
        connectedClients.get(playerId).interrupt();
        connectedClients.remove(playerId);
    }
}
