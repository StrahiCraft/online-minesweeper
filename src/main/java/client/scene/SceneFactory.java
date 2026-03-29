package client.scene;

import client.ClientApplication;
import client.rendering.MinefieldRenderer;
import client_server_comunication.LobbyData;
import game.minefield.MineFieldGenerator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import client_server_comunication.ServerMessageType;
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
        Button quitGameButton = new Button("Quit Game");

        hostGameButton.setOnMouseClicked(event -> {
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.CREATE_LOBBY, ClientApplication.getClientInstance().getCurrentLobbyName());
        });
        joinGameButton.setOnMouseClicked(event -> {
            SceneManager.changeScene(SceneType.JOIN);
        });
        quitGameButton.setOnMouseClicked(event -> {
            SceneManager.close();
        });

        root.getChildren().addAll(title, hostGameButton, joinGameButton, quitGameButton);
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
        LobbyData currentLobbyData = ClientApplication.getClientInstance().getCurrentLobbyData();

        Label title = new Label("Host Game");

        HBox hostnameHBox = new HBox();

        TextField roomNameTextField = new TextField(currentLobbyData.getLobbyName());
        roomNameTextField.setPromptText("Enter room name...");
        Button setRoomNameButton = new Button("Set room name");

        hostnameHBox.getChildren().addAll(roomNameTextField, setRoomNameButton);

        HBox gameSettingsHBox = new HBox();

        VBox widthSettingsVBox = new VBox();

        Label boardWidthLabel = new Label("Board width");
        TextField widthTextField = new TextField(Integer.toString(currentLobbyData.getMinefieldWidth()));
        widthTextField.setPromptText("Enter width (max 32)");

        widthSettingsVBox.getChildren().addAll(boardWidthLabel, widthTextField);

        VBox heightSettingsVBox = new VBox();

        Label boardHeightLabel = new Label("Board height");
        TextField heightTextField = new TextField(Integer.toString(currentLobbyData.getMinefieldHeight()));
        heightTextField.setPromptText("Enter height (max 32)");

        heightSettingsVBox.getChildren().addAll(boardHeightLabel, heightTextField);

        VBox mineCountVBox = new VBox();

        Label mineCountLabel = new Label("Mine count");
        TextField mineCountTextField = new TextField(Integer.toString(currentLobbyData.getMineCount()));
        mineCountTextField.setPromptText("Enter mine count (max 25% board coverage");

        mineCountVBox.getChildren().addAll(mineCountLabel, mineCountTextField);
        Button updateSettingsButton = new Button("Update settings");

        updateSettingsButton.setOnMouseClicked(event -> {
            int boardWidth = Integer.parseInt(widthTextField.textProperty().getValue());
            int boardHeight = Integer.parseInt(heightTextField.textProperty().getValue());
            int mineCount = Integer.parseInt(mineCountTextField.textProperty().getValue());

            if(boardWidth > 32 || boardWidth < 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Minefield width must be between 4 and 32!", Alert.AlertType.WARNING);
                return;
            }

            if(boardHeight > 32 || boardHeight < 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Minefield height must be between 4 and 32!", Alert.AlertType.WARNING);
                return;
            }

            if(mineCount < 1 || boardHeight > (boardWidth * boardHeight) / 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Mine count must be between 1 and "
                        + (boardWidth * boardHeight) / 4 + "!", Alert.AlertType.WARNING);
                return;
            }

            currentLobbyData.setMinefieldWidth(boardWidth);
            currentLobbyData.setMinefieldHeight(boardHeight);
            currentLobbyData.setMineCount(mineCount);

            currentLobbyData.setMinefield(MineFieldGenerator.generateMinefield(new Vector2Int(boardWidth, boardHeight), mineCount));
            ClientApplication.getClientInstance().alert("Minefield settings", "Minefield settings updated", Alert.AlertType.INFORMATION);
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.REFRESH_GAME, currentLobbyData);
        });

        gameSettingsHBox.getChildren().addAll(widthSettingsVBox, heightSettingsVBox, mineCountVBox, updateSettingsButton);

        ListView<String> playerList = new ListView<>();
        for(String clientName : currentLobbyData.getClientAccountNames()){
            playerList.getItems().add(clientName);
        }

        Button startGameButton = new Button("Start Game");
        Button backButton = new Button("Disband lobby");

        backButton.setOnMouseClicked(event -> {
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.DELETE_LOBBY, ClientApplication.getClientInstance().getCurrentLobbyName());
            SceneManager.changeScene(SceneType.LOBBY);
        });

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

            if(boardWidth > 32 || boardWidth < 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Minefield width must be between 4 and 32!", Alert.AlertType.WARNING);
                return;
            }

            if(boardHeight > 32 || boardHeight < 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Minefield height must be between 4 and 32!", Alert.AlertType.WARNING);
                return;
            }

            if(mineCount < 1 || boardHeight > (boardWidth * boardHeight) / 4){
                ClientApplication.getClientInstance().alert("Minefield settings", "Mine count must be between 1 and "
                        + (boardWidth * boardHeight) / 4 + "!", Alert.AlertType.WARNING);
                return;
            }

            currentLobbyData.setMinefield(MineFieldGenerator.generateMinefield(new Vector2Int(boardWidth, boardHeight), mineCount));
            System.out.println(currentLobbyData.getMinefield());

            ClientApplication.getClientInstance().sendMessage(ServerMessageType.START_GAME, ClientApplication.getClientInstance().getCurrentLobbyData());
        });
        backButton.setOnMouseClicked(event -> {
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.DELETE_LOBBY, ClientApplication.getClientInstance().getCurrentLobbyName());
            ClientApplication.getClientInstance().resetLobbyName();
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, hostnameHBox, gameSettingsHBox, playerList, startGameButton, backButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        roomNameTextField.deselect();

        Scene scene = new Scene(root, resolution.getX(), resolution.getY());
        scene.getStylesheets().add(SceneFactory.class.getResource("/style/style.css").toExternalForm());

        return scene;
    }

    public static Scene getLobbyScene(){
        VBox root = new VBox();
        LobbyData currentLobbyData = ClientApplication.getClientInstance().getCurrentLobbyData();

        if(currentLobbyData == null){
            return new Scene(root, resolution.getX(), resolution.getY());
        }

        Label title = new Label("Lobby: " + currentLobbyData.getLobbyName());

        HBox gameSettingsHBox = new HBox();

        Label boardWidthLabel = new Label("Board width: " + currentLobbyData.getMinefieldWidth());
        Label boardHeightLabel = new Label("  Board height: " + currentLobbyData.getMinefieldHeight());

        Label mineCountLabel = new Label("  Mine count: " + currentLobbyData.getMineCount());

        gameSettingsHBox.getChildren().addAll(boardWidthLabel, boardHeightLabel, mineCountLabel);

        ListView<String> playerList = new ListView<>();
        for(String clientName : currentLobbyData.getClientAccountNames()){
            playerList.getItems().add(clientName);
        }

        Button backButton = new Button("Leave lobby");

        backButton.setOnMouseClicked(event -> {
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.LEAVE_LOBBY, ClientApplication.getClientInstance().getPlayerName());
            SceneManager.changeScene(SceneType.MAIN_MENU);
        });

        root.getChildren().addAll(title, gameSettingsHBox, playerList, backButton);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

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

        root.getChildren().add(MinefieldRenderer.renderMinefield(ClientApplication.getClientInstance().getCurrentLobbyData().getMinefield()));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene game = new Scene(root, resolution.getX(), resolution.getY());
        game.getStylesheets().add(
                SceneFactory.class.getResource("/style/minefield.css").toExternalForm()
        );

        return game;
    }
}
