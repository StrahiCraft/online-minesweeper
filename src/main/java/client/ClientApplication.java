package client;

import client.rendering.MinefieldRenderer;
import game.minefield.MineFieldGenerator;
import game.minefield.Minefield;
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
     * Default start function for the client program
     * @param stage
     * The client's window.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();

        root.getChildren().add(MinefieldRenderer.renderMinefield(MineFieldGenerator.generateMinefield(new Vector2Int(32, 32), 64)));

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Online Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}