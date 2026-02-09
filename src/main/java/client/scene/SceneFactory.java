package client.scene;

import client.rendering.MinefieldRenderer;
import game.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.customTypes.Vector2Int;

/**
 * Factory class made for storing scene templates and making instances using them
 */
public class SceneFactory {
    /**
     * The resolution of the window
     */
    private static final Vector2Int resolution = new Vector2Int(1280, 720);

    /**
     * Creates an instance of the main menu scene. This scene can either go to the host game scene or the join
     * game scene. You can also close the game from this scene.
     * @return
     * An instance of the created scene
     */
    public static Scene getMainMenuScene() {
        VBox root = new VBox();

        Label title = new Label("Online Minesweeper");

        Button hostGameButton = new Button("Host Game");
        Button joinGameButton = new Button("Join Game");
        Button statisticsButton = new Button("Statistics");
        Button quitGameButton = new Button("Quit Game");

        hostGameButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.HOST);
        });
        joinGameButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.JOIN);
        });
        statisticsButton.setOnMouseClicked(event -> {

        });
        quitGameButton.setOnMouseClicked(event -> {
            System.exit(0);
        });

        root.getChildren().addAll(title, hostGameButton, joinGameButton, statisticsButton, quitGameButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, resolution.getX(), resolution.getY());
    }

    /**
     * Creates an instance of the join scene. The join scene is used to join other player's games.
     * @return
     * An instance of the created scene
     */
    public static Scene getJoinScene() {
        VBox root = new VBox();

        Label title = new Label("Join Game");

        TextField roomNameTextField = new TextField();
        roomNameTextField.setPromptText("Enter room name...");
        Button joinGameButton = new Button("Join");
        Button backButton = new Button("Back");

        joinGameButton.setOnMouseClicked(event -> {
            // TODO join game if found
        });
        backButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, roomNameTextField, joinGameButton, backButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, resolution.getX(), resolution.getY());
    }

    /**
     * Creates an instance of the host scene. The host scene is used to host games so other players can
     * join and play with the host.
     * @return
     * An instance of the created scene
     */
    public static Scene getHostScene() {
        VBox root = new VBox();

        Label title = new Label("Host Game");

        HBox hostnameHBox = new HBox();

        TextField roomNameTextField = new TextField();
        roomNameTextField.setPromptText("Enter room name...");
        Button setRoomNameButton = new Button("Set room name");

        hostnameHBox.getChildren().addAll(roomNameTextField, setRoomNameButton);

        HBox gameSettingsHBox = new HBox();

        Label errorLabel = new Label();

        VBox widthSettingsVBox = new VBox();

        Label boardWidthLabel = new Label("Board width");
        TextField widthTextField = new TextField();
        widthTextField.setPromptText("Enter width (max 32)");

        widthSettingsVBox.getChildren().addAll(boardWidthLabel, widthTextField);

        VBox heightSettingsVBox = new VBox();

        Label boardHeightLabel = new Label("Board height");
        TextField heightTextField = new TextField();
        heightTextField.setPromptText("Enter height (max 32)");

        heightSettingsVBox.getChildren().addAll(boardHeightLabel, heightTextField);

        VBox mineCountVBox = new VBox();

        Label mineCountLabel = new Label("Mine count");
        TextField mineCountTextField = new TextField();
        mineCountTextField.setPromptText("Enter mine count (max 25% board coverage");

        mineCountVBox.getChildren().addAll(mineCountLabel, mineCountTextField);

        gameSettingsHBox.getChildren().addAll(widthSettingsVBox, heightSettingsVBox, mineCountVBox);

        ListView<String> playerList = new ListView<>();
        Button startGameButton = new Button("Start Game");

        Button backButton = new Button("Back");

        setRoomNameButton.setOnMouseClicked(event -> {
            // TODO set room name
        });
        startGameButton.setOnMouseClicked(event -> {
            int boardWidth = Integer.parseInt(widthTextField.textProperty().getValue());
            int boardHeight = Integer.parseInt(heightTextField.textProperty().getValue());
            int mineCount = Integer.parseInt(mineCountTextField.textProperty().getValue());

            // TODO catch illegal value exception

            GameManager.startGame(new Vector2Int(boardWidth, boardHeight), mineCount);
        });
        backButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, hostnameHBox, errorLabel, gameSettingsHBox, playerList, startGameButton, backButton);

        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, resolution.getX(), resolution.getY());
    }

    /**
     * Creates an instance of the game scene, this is where the minesweeper gameplay happens.
     * @return
     * An instance of the created scene
     */
    public static Scene getGameScene() {
        VBox root = new VBox();

        root.getChildren().add(MinefieldRenderer.renderMinefield(GameManager.getMinefield()));

        Scene game = new Scene(root, resolution.getX(), resolution.getY());
        game.getStylesheets().add(
                SceneFactory.class.getResource("/style/minefield.css").toExternalForm()
        );

        return game;
    }
}
