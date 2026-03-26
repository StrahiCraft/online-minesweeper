package server;

import server.database.DatabaseManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Application for the server, this needs to be running for clients to connect and play the game
 */
public class ServerApplication {
    public static final int PORT = 25655;

    public static void main(String[] args) {
        DatabaseManager.connect();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Server started on port " + PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientConnectMessage = input.readLine();

                if(clientConnectMessage == null){
                    System.out.println("Client failed to connect.");
                }
                else{
                    System.out.println(clientConnectMessage);
                    output.println("Connection successful!");
                    System.out.println("Client connected!");
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
        }
    }
}
