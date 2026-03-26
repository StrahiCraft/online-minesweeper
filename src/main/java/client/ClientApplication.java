package client;

import client.scene.SceneFactory;
import client.scene.SceneManager;
import client.scene.SceneType;
import javafx.application.Application;
import javafx.stage.Stage;
import server.database.DatabaseManager;

import java.io.IOException;

/**
 * Class for the client application. Each player runs this as their client.
 */
public class ClientApplication extends Application {
    /**
     * Instance of the client class, this is used to communicate with the server
     */
    private static Client client;

    /**
     * Default start function for the client program.
     * @param stage
     * The client's window.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        client = new Client();
        client.start();

        stage.setTitle("Online Minesweeper");
        stage.setOnCloseRequest((gameCloseEvent) -> client.onGameClosed());
        SceneManager.setStage(stage);

        stage.setScene(SceneFactory.getConnectionFailedScene());
        stage.show();

    }

    /**
     * Gets the instance of the client
     * @return The instance of the client
     */
    public static Client getClientInstance(){
        return client;
    }

    /**
     * The main method from which the game starts.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}