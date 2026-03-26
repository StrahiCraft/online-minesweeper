package client;

import client.scene.SceneManager;
import client.scene.SceneType;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public void attemptConnectionToServer(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Trying to connect client");

            System.out.println(input.readLine());

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
    }

    @Override
    public void run() {
        try {
            attemptConnectionToServer();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getConnectionSuccessful(){
        return connectionSuccessful;
    }
}
