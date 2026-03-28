package client;

import client.scene.SceneManager;
import client.scene.SceneType;
import client_server_comunication.ServerMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
     * Current name of the lobby this client is hosting
     */
    private String currentLobbyName;

    private String potentialLobbyName;

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
            objectOutputStream.writeObject(new ServerMessage(clientId, messageType, data));
            objectOutputStream.reset();
            objectOutputStream.flush();
        }
        catch (Exception e){
            System.out.println("Connection to server failed, no message was sent.");
        }
    }

    /**
     * Logs the player out of their account
     */
    public void logOut(){
        sendMessage(ServerMessageType.DELETE_LOBBY, currentLobbyName);
        sendMessage(ServerMessageType.LOG_OUT, playerName);
        SceneManager.changeScene(SceneType.LOGIN);
        playerName = null;
    }

    /**
     * This function is called when the game is closed
     */
    public void onGameClosed(){
        if(playerName != null){
            logOut();
        }

        sendMessage(ServerMessageType.QUIT);
        Thread.currentThread().interrupt();
        System.exit(0);
    }

    public void alert(String headerText, String containerText, Alert.AlertType alertType){
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(headerText);
            alert.setContentText(containerText);
            alert.showAndWait();
        });
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

                    ServerMessage receivedMessage = (ServerMessage) objectInputStream.readObject();

                    if(receivedMessage == null){
                        continue;
                    }

                    switch (receivedMessage.getMessageType()){
                        case REGISTER_SUCCESS:
                        case LOGIN_SUCCESS:
                            playerName = (String) receivedMessage.getMessageData();
                            currentLobbyName = playerName + "'s lobby";
                            Platform.runLater(() -> SceneManager.changeScene(SceneType.MAIN_MENU));
                            System.out.println("Player name: " + playerName);
                            break;
                        case ALREADY_LOGGED_IN:
                            alert("Login error", "Already logged in!", Alert.AlertType.ERROR);
                            break;
                        case REGISTER_FAIL:
                            alert("Registration error", "User already exists!", Alert.AlertType.ERROR);
                            break;
                        case LOGIN_FAIL:
                            alert("Login error", "Invalid username or password!", Alert.AlertType.ERROR);
                            break;
                        case CREATE_LOBBY_SUCCESS:
                            Platform.runLater(() -> SceneManager.changeScene(SceneType.HOST));
                            String[] messageData = { playerName, currentLobbyName };
                            sendMessage(ServerMessageType.SET_PLAYER_TO_LOBBY, messageData);
                            break;
                        case CREATE_LOBBY_FAIL:
                            alert("Lobby creation error", "Lobby creation failed!", Alert.AlertType.ERROR);
                            break;
                        case LOBBY_RENAME_SUCCESS:
                            currentLobbyName = potentialLobbyName;
                            alert("Lobby rename", "Lobby renamed successfully!", Alert.AlertType.INFORMATION);
                            break;
                        case LOBBY_RENAME_FAIL:
                            alert("Lobby rename", "Lobby with that name already exists!", Alert.AlertType.ERROR);
                            break;
                        case JOIN_LOBBY_SUCCESS:
                            // TODO set to lobby scene
                            break;
                        case JOIN_LOBBY_FAIL:
                            alert("Lobby not found", "No lobby with that name has been found!", Alert.AlertType.ERROR);
                            break;
                        default:
                            break;
                    }

                    System.out.println(receivedMessage.getMessageType());
                }
            }
            if(objectOutputStream != null){
                objectOutputStream.close();
            }
            if(objectInputStream != null){
                objectInputStream.close();
            }
        }
        catch (EOFException e){
            System.out.println("End of socket data, disconnected.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCurrentLobbyName() {
        return currentLobbyName;
    }

    public void setCurrentLobbyName(String currentLobbyName) {
        this.currentLobbyName = currentLobbyName;
    }

    public String getPotentialLobbyName() {
        return potentialLobbyName;
    }

    public void setPotentialLobbyName(String potentialLobbyName) {
        this.potentialLobbyName = potentialLobbyName;
    }
}
