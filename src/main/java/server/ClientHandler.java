package server;

import client.ClientMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        try{
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
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

                if(clientMessage.getMessage().equals("quit")){
                    ServerApplication.onPlayerDisconnected(clientMessage.getPlayerId());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
