package server;

import client.ClientMessage;
import server.database.AccountValidation;
import server.database.DatabaseManager;
import utility.customTypes.ServerMessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final UUID playerID;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket clientSocket, UUID playerID){
        this.clientSocket = clientSocket;
        this.playerID = playerID;

        try{
            output = new ObjectOutputStream(this.clientSocket.getOutputStream());
            input = new ObjectInputStream(this.clientSocket.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendMessage(ServerMessageType messageType){
        sendMessage(messageType, null);
    }

    private void sendMessage(ServerMessageType messageType, Object data){
        try{
            output.writeObject(new ClientMessage(playerID, messageType, data));
            output.flush();
        }
        catch (Exception e){
            System.out.println("Connection to server failed, no message was sent.");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ClientMessage clientMessage = (ClientMessage) input.readObject();

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
                            sendMessage(ServerMessageType.REGISTER_SUCCESS);
                        }
                        else {
                            sendMessage(ServerMessageType.REGISTER_FAIL);
                        }

                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
