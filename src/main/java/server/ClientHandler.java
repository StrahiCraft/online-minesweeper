package server;

import client.ClientMessage;
import server.database.AccountValidation;
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
            objectOutputStream.writeObject(new ClientMessage(clientId, messageType, data));
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

                ClientMessage clientMessage = (ClientMessage) objectInputStream.readObject();

                if(clientMessage == null) {
                    continue;
                }
                ServerMessageType messageType = clientMessage.getMessageType();

                switch (messageType){
                    case QUIT:
                        ServerApplication.onPlayerDisconnected(clientMessage.getPlayerId());
                        break;
                    case REGISTER:
                        String[] registerData = (String[]) clientMessage.getMessageData();

                        if(AccountValidation.validRegistration(registerData[0], registerData[1])){
                            DatabaseManager.executeUpdate("INSERT INTO Player(`username`, `password`) VALUES (?, ?)", registerData);
                            sendMessage(ServerMessageType.REGISTER_SUCCESS, registerData[0]);
                        }
                        else {
                            sendMessage(ServerMessageType.REGISTER_FAIL);
                        }

                        break;
                    case LOGIN:
                        String[] loginData = (String[]) clientMessage.getMessageData();

                        if(AccountValidation.validLogin(loginData[0], loginData[1])){
                            sendMessage(ServerMessageType.LOGIN_SUCCESS, loginData[0]);
                        }
                        else {
                            sendMessage(ServerMessageType.LOGIN_FAIL);
                        }
                        break;
                    default:
                        break;
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
}
