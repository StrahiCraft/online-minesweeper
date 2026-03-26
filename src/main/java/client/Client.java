package client;

import client.scene.SceneManager;
import client.scene.SceneType;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class Client extends Thread {
    /**
     * Socket used to connect to the server
     */
    private Socket socket;

    /**
     * IP address of the client (currently set to localhost)
     */
    private final String IP = "127.0.0.1";
    /**
     * Port of the server the client will connect to
     */
    private final int PORT = 25655;

    private UUID playerID;

    /**
     * Name of the player connecting to the server through this client
     */
    private String playerName;

    /**
     * False if the server is offline or connection failed
     */
    private boolean connectionSuccessful = false;

    public Client() {
        try{
            this.socket = new Socket(IP, PORT);
        }
        catch (Exception e){
            System.out.println("Server not found...");
        }
    }

    /**
     * This is called when first trying to connect to the server, if the connection is successful, the game scene
     * will be switched to the log in scene, if the connection fails, the scene remains as the connection failed scene
     */
    public boolean attemptConnectionToServer(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Trying to connect client");

            playerID = UUID.fromString(input.readLine());
            System.out.println(playerID);

            connectionSuccessful = true;
        }
        catch (Exception e){
            System.out.println("Connection failed...");
        }
        finally {
            if(connectionSuccessful){
                Platform.runLater(() -> SceneManager.changeScene(SceneType.LOGIN));
            }
        }
        return connectionSuccessful;
    }

    public void sendMessage(String message){
        try{
            ObjectOutputStream messageObject = new ObjectOutputStream(socket.getOutputStream());

            messageObject.writeObject(new ClientMessage(playerID, message));
            messageObject.flush();
        }
        catch (Exception e){
            System.out.println("Connection to server failed, no message was sent.");
        }
    }

    public void onGameClosed(){
        sendMessage("quit");
        Thread.currentThread().interrupt();
        System.exit(0);
    }

    /**
     * Runs all client side client-server logic after successful connection to the server
     */
    @Override
    public void run() {
        try {
            if(attemptConnectionToServer()){
                while (!Thread.currentThread().isInterrupted()){
                    ObjectInputStream inputObject = new ObjectInputStream(socket.getInputStream());
                    ClientMessage receivedMessage = (ClientMessage) inputObject.readObject();

                    if(receivedMessage == null){
                        continue;
                    }
                    System.out.println(receivedMessage.getMessage());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
