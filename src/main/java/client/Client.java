package client;

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

    public Client() {
        try{
            this.socket = new Socket(IP, PORT);
        }
        catch (Exception e){
            System.out.println("Server not found...");
        }
    }

    public void connectionSuccessful(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Trying to connect client");

            System.out.println(input.readLine());
        }
        catch (Exception e){
            System.out.println("Connection failed...");
        }
    }

    @Override
    public void run() {
        try {
            connectionSuccessful();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
