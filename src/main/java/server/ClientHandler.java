package server;

import client_server_comunication.ServerMessage;
import server.database.DatabaseManager;
import client_server_comunication.LobbyData;
import client_server_comunication.ServerMessageType;

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
     * Username of the currently logged in account on this client
     */
    private String clientAccountUsername;

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
                    case LOG_OUT -> logOutPlayer(messageFromClient);
                    case CREATE_LOBBY -> createLobby(messageFromClient);
                    case DELETE_LOBBY -> deleteLobby(messageFromClient);
                    case LEAVE_LOBBY -> leaveLobby(messageFromClient);
                    case JOIN_LOBBY -> joinLobby(messageFromClient);
                    case SET_PLAYER_TO_LOBBY -> setPlayerToLobby(messageFromClient);
                    case RENAME_LOBBY -> renameLobby(messageFromClient);
                    case START_GAME -> startGame(messageFromClient);
                    case REFRESH_GAME -> refreshLobby((LobbyData) messageFromClient.getMessageData());
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
            clientAccountUsername = registerData[0];
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
            if(AccountValidation.notAlreadyLoggedIn(loginData[0])){
                AccountValidation.setLoggedIn(loginData[0], true);
                clientAccountUsername = loginData[0];
                sendMessage(ServerMessageType.LOGIN_SUCCESS, loginData[0]);
                return;
            }
            sendMessage(ServerMessageType.ALREADY_LOGGED_IN);
        }
        else {
            sendMessage(ServerMessageType.LOGIN_FAIL);
        }
    }

    /**
     * Logs the player out of the account
     * @param messageFromClient Message from the client, should be the username of the player to log out
     */
    private void logOutPlayer(ServerMessage messageFromClient){
        String playerName = (String) messageFromClient.getMessageData();

        clientAccountUsername = "";
        AccountValidation.setLoggedIn(playerName, false);
    }

    /**
     * Tries to create a lobby
     * @param messageFromClient Message from the client, should contain the name of the lobby to be created
     */
    private void createLobby(ServerMessage messageFromClient){
        String lobbyName = (String) messageFromClient.getMessageData();

        if(LobbyManager.createLobby(lobbyName)){
            ServerApplication.createLobbyData(clientId, clientAccountUsername, lobbyName);
            sendMessage(ServerMessageType.CREATE_LOBBY_SUCCESS, ServerApplication.getLobbyWithClient(clientId));
        }
        else {
            sendMessage(ServerMessageType.CREATE_LOBBY_FAIL);
        }
    }

    /**
     * Deletes a lobby from the database and disconnects all clients that were connected to it from the lobby (it sends them to the main menu)
     * @param messageFromClient Message from the client, should be the name of the lobby being deleted
     */
    public void deleteLobby(ServerMessage messageFromClient){
        String lobbyName = (String) messageFromClient.getMessageData();
        LobbyData lobbyData = ServerApplication.getLobbyWithName(lobbyName);

        if(lobbyData != null){
            for(UUID client : lobbyData.getClients()){
                ServerApplication.getClientHandler(client).sendMessage(ServerMessageType.LOBBY_DISBANDED);
            }
        }

        LobbyManager.deleteLobby(lobbyName);
        ServerApplication.deleteLobbyData(lobbyName);
    }

    /**
     * Sets the player's lobby id to the given lobby based on the player name
     * @param messageFromClient Message from the client, should contain the player's name and the name of the lobby the
     *                          given player is being set to
     */
    private void setPlayerToLobby(ServerMessage messageFromClient){
        String[] decodedMessageData = (String[]) messageFromClient.getMessageData();
        LobbyManager.setPlayerToLobby(decodedMessageData[0], decodedMessageData[1]);
    }

    /**
     * Tries to chang the lobby's name to a new name
     * @param messageFromClient Message from the client, should contain the old name of the lobby and it's new one
     */
    private void renameLobby(ServerMessage messageFromClient){
        String[] decodedMessageData = (String[]) messageFromClient.getMessageData();
        if(LobbyManager.renameLobby(decodedMessageData[0], decodedMessageData[1])){
            LobbyData currentLobby = ServerApplication.getLobbyWithName(decodedMessageData[0]);
            currentLobby.setLobbyName(decodedMessageData[1]);
            sendMessage(ServerMessageType.LOBBY_RENAME_SUCCESS, currentLobby);
        }
        else {
            sendMessage(ServerMessageType.LOBBY_RENAME_FAIL);
        }
    }

    /**
     * Joins the given client to the given lobby
     * @param messageFromClient Message from the client, should be the name of the client followed by the name of
     *                          the lobby the client is joining
     */
    private void joinLobby(ServerMessage messageFromClient){
        String[] decodedMessageData = (String[]) messageFromClient.getMessageData();

        if(LobbyManager.getLobbyId(decodedMessageData[1]) == null) {
            sendMessage(ServerMessageType.JOIN_LOBBY_FAIL);
        }
        else {
            setPlayerToLobby(messageFromClient);
            LobbyData currentLobby = ServerApplication.getLobbyWithName(decodedMessageData[1]);
            currentLobby.addPlayer(clientId, clientAccountUsername);
            sendMessage(ServerMessageType.JOIN_LOBBY_SUCCESS);

            refreshLobby(currentLobby);
        }
    }

    /**
     * Makes the given player leave any lobby
     * @param messageFromClient Message from the client, should be the player's username
     */
    private void leaveLobby(ServerMessage messageFromClient){
        String playerName = (String) messageFromClient.getMessageData();

        LobbyData currentLobby = ServerApplication.getLobbyWithClient(clientId);
        currentLobby.removePlayer(clientId);
        LobbyManager.removePlayerFromLobby(playerName);

        refreshLobby(currentLobby);
    }

    /**
     * Refreshes the lobby screen of all clients connected to the given lobby
     * @param lobby Lobby from which the clients are given
     */
    private void refreshLobby(LobbyData lobby){
        for(UUID clientId : lobby.getClients()){
            ServerApplication.getClientHandler(clientId).sendMessage(ServerMessageType.REFRESH_LOBBY, lobby);
        }
    }

    /**
     * Starts the game to all clients in the given lobby
     * @param messageFromClient Message from the client, should be the data of the lobby containing the minefieeld the game
     *                          will be played on
     */
    private void startGame(ServerMessage messageFromClient){
        LobbyData newLobbyData = (LobbyData) messageFromClient.getMessageData();

        for(UUID clientId : newLobbyData.getClients()){
            ServerApplication.getClientHandler(clientId).sendMessage(ServerMessageType.ON_GAME_STARTED, newLobbyData);
        }
    }
}
