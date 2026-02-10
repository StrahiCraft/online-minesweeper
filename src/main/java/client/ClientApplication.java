package client;

import client.scene.SceneFactory;
import client.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class for the client application. Each player runs this as their client.
 */
public class ClientApplication extends Application {

    /**
     * Default start function for the client program.
     * @param stage
     * The client's window.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.setStage(stage);

        stage.setTitle("Online Minesweeper");
        stage.setScene(SceneFactory.getLoginScene());
        stage.show();
    }

    /**
     * The main method from which the game starts.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}