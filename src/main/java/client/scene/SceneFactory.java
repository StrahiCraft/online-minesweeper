package client.scene;

import client.ClientApplication;
import client.rendering.MinefieldRenderer;
import game.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.customTypes.ServerMessageType;
import utility.customTypes.Vector2Int;

/**
 * Factory class made for storing scene templates and making instances using them
 */
public class SceneFactory {
    /**
     * The resolution of the window
     */
    private static final Vector2Int resolution = new Vector2Int(1280, 720);

    public static Scene getConnectionFailedScene(){
        VBox root = new VBox();

        Label title = new Label("Connection to server failed");

        Button quitGameButton = new Button("Quit Game");
        quitGameButton.setOnMouseClicked(event -> {
            SceneManager.close();
        });

        root.getChildren().addAll(title, quitGameButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
    }

    /**
     * Creates an instance of the login screen. This is the first scene loaded, the user can also choose to register if
     * they don't have an account from here.
     * @return
     * An instance of the created scene
     */
    public static Scene getLoginScene() {
        VBox root = new VBox();

        Label title = new Label("Login");

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button login = new Button("Login");
        Button register = new Button("Don't have an account? Register here!");

        Button quitGameButton = new Button("Quit Game");
        quitGameButton.setOnMouseClicked(event -> {
            SceneManager.close();
        });

        login.setOnMouseClicked(event -> {
            String[] loginData = { username.getText(), password.getText() };
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.LOGIN, loginData);
        });
        register.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.REGISTER);
        });

        root.getChildren().addAll(title, username, password, login, register, quitGameButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
    }

    /**
     * Creates an instance of the registration screen. New users can create accounts from here, or log in to an existing
     * one
     * @return
     * An instance of the created scene
     */
    public static Scene getRegisterScene() {
        VBox root = new VBox();

        Label title = new Label("Register");

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");

        Button register = new Button("Register");
        Button backToLogin = new Button("Back to login screen");

        Button quitGameButton = new Button("Quit Game");
        quitGameButton.setOnMouseClicked(event -> {
            SceneManager.close();
        });

        register.setOnMouseClicked(event -> {
            if(username.getText().length() > 20){
                ClientApplication.getClientInstance().alert("Registration error",
                        "Username too long! Must be 20 or less characters!", Alert.AlertType.ERROR);
                return;
            }
            if(password.getText().length() > 45){
                ClientApplication.getClientInstance().alert("Registration error",
                        "Password too long! Must be 45 or less characters!", Alert.AlertType.ERROR);
                return;
            }

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                ClientApplication.getClientInstance().alert("Registration error",
                        "Username and password can not be empty!", Alert.AlertType.ERROR);
                return;
            }
            if(username.getText().contains(" ") || password.getText().contains(" ")){
                ClientApplication.getClientInstance().alert("Registration error",
                        "Spaces are not allowed in username and password!", Alert.AlertType.ERROR);
                return;
            }
            if(password.getText().equals(confirmPassword.getText())){
                String[] registerData = { username.getText(), password.getText() };
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.REGISTER, registerData);
                return;
            }
            ClientApplication.getClientInstance().alert("Registration error",
                    "Password and confirm password are not the same!", Alert.AlertType.ERROR);
        });
        backToLogin.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.LOGIN);
        });

        root.getChildren().addAll(title, username, password, confirmPassword, register, backToLogin, quitGameButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
    }


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
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.CREATE_LOBBY, ClientApplication.getClientInstance().getCurrentLobbyName());
        });
        joinGameButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.JOIN);
        });
        statisticsButton.setOnMouseClicked(event -> {

        });
        quitGameButton.setOnMouseClicked(event -> {
            SceneManager.close();
        });

        root.getChildren().addAll(title, hostGameButton, joinGameButton, statisticsButton, quitGameButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
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
            String[] messageData = { ClientApplication.getClientInstance().getPlayerName(), roomNameTextField.getText() };
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.JOIN_LOBBY, messageData);
        });
        backButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, roomNameTextField, joinGameButton, backButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
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

        TextField roomNameTextField = new TextField(ClientApplication.getClientInstance().getCurrentLobbyName());
        roomNameTextField.setPromptText("Enter room name...");
        Button setRoomNameButton = new Button("Set room name");

        hostnameHBox.getChildren().addAll(roomNameTextField, setRoomNameButton);

        HBox gameSettingsHBox = new HBox();

        Label errorLabel = new Label();

        VBox widthSettingsVBox = new VBox();

        Label boardWidthLabel = new Label("Board width");
        TextField widthTextField = new TextField("16");
        widthTextField.setPromptText("Enter width (max 32)");

        widthSettingsVBox.getChildren().addAll(boardWidthLabel, widthTextField);

        VBox heightSettingsVBox = new VBox();

        Label boardHeightLabel = new Label("Board height");
        TextField heightTextField = new TextField("16");
        heightTextField.setPromptText("Enter height (max 32)");

        heightSettingsVBox.getChildren().addAll(boardHeightLabel, heightTextField);

        VBox mineCountVBox = new VBox();

        Label mineCountLabel = new Label("Mine count");
        TextField mineCountTextField = new TextField("64");
        mineCountTextField.setPromptText("Enter mine count (max 25% board coverage");

        mineCountVBox.getChildren().addAll(mineCountLabel, mineCountTextField);

        gameSettingsHBox.getChildren().addAll(widthSettingsVBox, heightSettingsVBox, mineCountVBox);

        ListView<String> playerList = new ListView<>();
        Button startGameButton = new Button("Start Game");

        Button backButton = new Button("Back");

        setRoomNameButton.setOnMouseClicked(event -> {
            if(roomNameTextField.getText().isEmpty()){
                ClientApplication.getClientInstance().alert("Room rename", "Room name can not be empty!", Alert.AlertType.ERROR);
                return;
            }

            String[] messageData = { ClientApplication.getClientInstance().getCurrentLobbyName(), roomNameTextField.getText() };

            if(messageData[0].equals(messageData[1])){
                ClientApplication.getClientInstance().alert("Lobby rename", "Lobby name is already that!", Alert.AlertType.INFORMATION);
                return;
            }

            ClientApplication.getClientInstance().setPotentialLobbyName(roomNameTextField.getText());
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.RENAME_LOBBY, messageData);
        });
        startGameButton.setOnMouseClicked(event -> {
            int boardWidth = Integer.parseInt(widthTextField.textProperty().getValue());
            int boardHeight = Integer.parseInt(heightTextField.textProperty().getValue());
            int mineCount = Integer.parseInt(mineCountTextField.textProperty().getValue());

            // TODO catch illegal value exception

            GameManager.startGame(new Vector2Int(boardWidth, boardHeight), mineCount);
        });
        backButton.setOnMouseClicked(event -> {
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.DELETE_LOBBY, ClientApplication.getClientInstance().getCurrentLobbyName());
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, hostnameHBox, errorLabel, gameSettingsHBox, playerList, startGameButton, backButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        roomNameTextField.deselect();

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
    }

    /**
     * Creates an instance of the game scene, this is where the minesweeper gameplay happens.
     * @return
     * An instance of the created scene
     */
    public static Scene getGameScene() {
        VBox root = new VBox();

        root.getChildren().add(MinefieldRenderer.renderMinefield(GameManager.getMinefield()));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene game = new Scene(root, resolution.getX(), resolution.getY());
        game.getStylesheets().add(
                SceneFactory.class.getResource("/style/minefield.css").toExternalForm()
        );

        return game;
    }
}
