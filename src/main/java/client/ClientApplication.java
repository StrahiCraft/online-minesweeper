package client;

import client.rendering.MinefieldRenderer;
import client.scene.SceneFactory;
import client.scene.SceneManager;
import game.minefield.MineFieldGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.customTypes.Vector2Int;

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
        stage.setScene(SceneFactory.getMainMenuScene());
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