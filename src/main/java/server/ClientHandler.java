package server;

import client_server_comunication.ServerMessage;
import server.database.DatabaseManager;
import utility.customTypes.ServerMessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Server side class for handling incoming client messages and sending back server responses
 */
public class ClientHandler extends Thread {
    /**
     * Clients connection socket that connects the client to the server
     */
    private final Socket clientSocket;
    /**
     * Unique id given to the client upon connection with the server, the id is assigned by the server
     */
    private final UUID clientId;

    /**
     * Output stream for sending objects to the client from the server
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * Input stream for receiving objects from the client
     */
    private ObjectInputStream objectInputStream;

    public ClientHandler(Socket clientSocket, UUID clientId, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream){
        this.clientSocket = clientSocket;
        this.clientId = clientId;

        try {
            this.objectOutputStream = objectOutputStream;
            this.objectInputStream = objectInputStream;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client with no additional data
     * @param messageType Type of message being sent to the client
     */
    private void sendMessage(ServerMessageType messageType){
        sendMessage(messageType, null);
    }

    /**
     * Sends a message to the client with additional data in the form of an Object
     * @param messageType Type of message being sent to the client
     * @param data Data sent to the client
     */
    private void sendMessage(ServerMessageType messageType, Object data){
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
     * Runs all server side client-server logic after successful connection from the client until the client disconnects
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if(objectInputStream == null){
                    continue;
                }

                ServerMessage messageFromClient = (ServerMessage) objectInputStream.readObject();

                if(messageFromClient == null) {
                    continue;
                }
                ServerMessageType messageType = messageFromClient.getMessageType();

                switch (messageType){
                    case QUIT -> ServerApplication.onPlayerDisconnected(messageFromClient.getClientId());
                    case REGISTER -> registerPlayer(messageFromClient);
                    case LOGIN -> loginPlayer(messageFromClient);
                    case CREATE_LOBBY -> createLobby(messageFromClient);
                    case DELETE_LOBBY -> LobbyManager.deleteLobby((String) messageFromClient.getMessageData());
                    default -> System.out.println("Unknown message type " + messageType);
                }
            }
            objectOutputStream.close();
            objectInputStream.close();
            clientSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Tries to register the client, sends a REGISTER_SUCCESS message if there is no user with the name yet, and
     * REGISTER_FAIL if there is
     * @param messageFromClient Message from the client, should contain username and password for the account trying
     *                          to register
     */
    private void registerPlayer(ServerMessage messageFromClient){
        String[] registerData = (String[]) messageFromClient.getMessageData();

        if(AccountValidation.validRegistration(registerData[0], registerData[1])){
            DatabaseManager.executeUpdate("INSERT INTO Player(`username`, `password`) VALUES (?, ?)", registerData);
            sendMessage(ServerMessageType.REGISTER_SUCCESS, registerData[0]);
        }
        else {
            sendMessage(ServerMessageType.REGISTER_FAIL);
        }
    }

    /**
     * Tries to log in the client, sends a LOGIN_SUCCESS message if there exists a user with the given name and the
     * password is correct, and LOGIN_FAILED if there isn't
     * @param messageFromClient Message from the client, should contain username and password for the account trying
     *                          to log in
     */
    private void loginPlayer(ServerMessage messageFromClient){
        String[] loginData = (String[]) messageFromClient.getMessageData();

        if(AccountValidation.validLogin(loginData[0], loginData[1])){
            sendMessage(ServerMessageType.LOGIN_SUCCESS, loginData[0]);
        }
        else {
            sendMessage(ServerMessageType.LOGIN_FAIL);
        }
    }

    /**
     * Tries to create a lobby
     * @param messageFromClient
     */
    private void createLobby(ServerMessage messageFromClient){
        String lobbyName = (String) messageFromClient.getMessageData();

        if(LobbyManager.createLobby(lobbyName)){
            sendMessage(ServerMessageType.CREATE_LOBBY_SUCCESS);
        }
        else {
            sendMessage(ServerMessageType.CREATE_LOBBY_FAIL);
        }
    }
}
