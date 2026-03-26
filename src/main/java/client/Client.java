package client;

import client.scene.SceneManager;
import client.scene.SceneType;
import javafx.application.Platform;
import utility.customTypes.ServerMessageType;

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

    private UUID clientId;

    /**
     * Name of the player connecting to the server through this client
     */
    private String playerName;

    /**
     * Output stream for sending objects to the server
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * Input stream for receiving objects from the server
     */
    private ObjectInputStream objectInputStream;

    /**
     * False if the server is offline or connection failed
     */
    private boolean connectionSuccessful = false;

    public Client() {
        try{
            socket = new Socket(IP, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
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
            objectOutputStream.writeObject("Trying to connect client");

            clientId = (UUID) objectInputStream.readObject();
            System.out.println(clientId);

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

    /**
     * Sends a message to the server with no additional data
     * @param messageType Type of message being sent to the server
     */
    public void sendMessage(ServerMessageType messageType){
        sendMessage(messageType, null);
    }

    /**
     * Sends a message to the server with additional data in the form of an Object
     * @param messageType Type of message being sent to the server
     * @param data Data sent to the server
     */
    public void sendMessage(ServerMessageType messageType, Object data){
        try{
            objectOutputStream.writeObject(new ClientMessage(clientId, messageType, data));
            objectOutputStream.reset();
            objectOutputStream.flush();
        }
        catch (Exception e){
            System.out.println("Connection to server failed, no message was sent.");
        }
    }

    /**
     * This function is called when the game is closed
     */
    public void onGameClosed(){
        sendMessage(ServerMessageType.QUIT);
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
                    if(objectInputStream == null){
                        continue;
                    }

                    ClientMessage receivedMessage = (ClientMessage) objectInputStream.readObject();

                    if(receivedMessage == null){
                        continue;
                    }

                    switch (receivedMessage.getMessageType()){
                        case REGISTER_SUCCESS:
                        case LOGIN_SUCCESS:
                            Platform.runLater(() -> SceneManager.changeScene(SceneType.MAIN_MENU));
                            break;
                        case REGISTER_FAIL:
                            // TODO set register failed message
                            break;
                        case LOGIN_FAIL:
                            // TODO set login failed message
                            break;
                    }

                    System.out.println(receivedMessage.getMessageType());
                }
            }
            objectOutputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
