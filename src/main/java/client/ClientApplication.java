package client;

import client.rendering.MinefieldRenderer;
import game.Minefield;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.customTypes.Vector2Int;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();

        root.getChildren().add(MinefieldRenderer.renderMinefield(new Minefield(new Vector2Int(32, 32), 64)));

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Online Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}